package id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.taufikibrahim.data.BuildConfig
import id.taufikibrahim.entity.movie.Movie
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.ItemCarouselNowPlayingBinding

class NowPlayingAdapter constructor(private val onItemClick: (Movie) -> Unit) :
    ListAdapter<Movie, NowPlayingAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemCarouselNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.itemView.setOnClickListener { onItemClick(currentPosition) }
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemCarouselNowPlayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.apply {
                val smallBackdropImageUrl = "${BuildConfig.IMAGE_URL}w200${item.backdropPath}"
                val backdropImageUrl = "${BuildConfig.IMAGE_URL}original${item.backdropPath}"
                val smallPosterImageUrl = "${BuildConfig.IMAGE_URL}w200${item.posterPath}"
                val posterImageUrl = "${BuildConfig.IMAGE_URL}w500${item.posterPath}"
                Glide.with(root.context)
                    .load(backdropImageUrl)
                    .placeholder(R.drawable.ic_placeholder_movie)
                    .thumbnail(Glide.with(root.context).load(smallBackdropImageUrl))
                    .into(backdropView)
                Glide.with(root.context)
                    .load(posterImageUrl)
                    .thumbnail(Glide.with(root.context).load(smallPosterImageUrl))
                    .placeholder(R.drawable.ic_placeholder_movie)
                    .into(posterView)
                textTitle.text = item.title
                vote.text = item.voteAverage?.toFloat().toString()
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}