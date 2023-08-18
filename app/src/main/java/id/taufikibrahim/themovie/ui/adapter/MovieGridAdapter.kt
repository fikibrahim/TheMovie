package id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.taufikibrahim.data.BuildConfig
import id.taufikibrahim.entity.movie.Discover
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.ItemGridBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.Utils

class MovieGridAdapter constructor(private val onItemClick: (Discover) -> Unit) :
    ListAdapter<Discover, MovieGridAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemGridBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.itemView.setOnClickListener { onItemClick(currentPosition) }
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Discover?) {
            binding.apply {
                val smallPosterImageUrl = "${BuildConfig.IMAGE_URL}w200${item?.posterPath}"
                val posterImageUrl = "${BuildConfig.IMAGE_URL}w500${item?.posterPath}"
                Glide.with(root.context)
                    .load(posterImageUrl)
                    .placeholder(R.drawable.ic_placeholder_movie)
                    .thumbnail(Glide.with(root.context).load(smallPosterImageUrl))
                    .into(posterView)
                textTitle.text = item?.title
                vote.text = item?.voteAverage?.toFloat().toString()
                releaseDate.text = "∙ ${Utils.dateFormat(item?.releaseDate.toString(), "yyyy")}"
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Discover>() {
            override fun areItemsTheSame(
                oldItem: Discover,
                newItem: Discover
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Discover,
                newItem: Discover
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}