package id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.taufikibrahim.data.BuildConfig
import id.taufikibrahim.entity.movie.Discover
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.ItemListBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.Utils.dateFormat

class DiscoverAdapter constructor(private val onItemClick: (Discover?) -> Unit) :
    PagingDataAdapter<Discover, DiscoverAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.bind(currentPosition, onItemClick)
    }

    class ListItemViewHolder(private var binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Discover?, onItemClick: (Discover?) -> Unit) {
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
                releaseDate.text = if (item?.releaseDate != null) dateFormat(item.releaseDate.toString(), "yyyy") else "-"
                row.setOnClickListener {
                    onItemClick(item)
                }
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