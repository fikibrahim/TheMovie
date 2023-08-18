package id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.taufikibrahim.themoviedb_visiprimanusantara.R

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    /**
     * view holder class for footer loader and error state handling
     */
    class LoaderViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_load_state_view, parent, false)
                return LoaderViewHolder(view, retry)
            }
        }

        private val loader: RelativeLayout = view.findViewById(R.id.loader)
        private val progress: ProgressBar = view.findViewById(R.id.load_state_progress)
        private val errorText: TextView = view.findViewById(R.id.load_state_errorMessage)
        private val retryBtn: Button = view.findViewById(R.id.load_state_retry)

        init {
            retryBtn.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    loader.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    retryBtn.visibility = View.GONE
                }
                is LoadState.Error -> {
                    errorText.text = loadState.error.toString()
                    loader.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
                else -> {
                    loader.visibility = View.GONE
                    errorText.visibility = View.GONE
                    retryBtn.visibility = View.GONE
                }
            }
        }
    }
}