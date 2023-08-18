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
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.ItemUpcomingBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.Utils

class UpcomingAdapter constructor(private val onItemClick: (Discover?) -> Unit) :
    ListAdapter<Discover, UpcomingAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.bind(currentPosition, onItemClick)
    }

    class ListItemViewHolder(private var binding: ItemUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Discover?, onItemClick: (Discover?) -> Unit) {
            binding.apply {
                val smallBackdropImageUrl = "${BuildConfig.IMAGE_URL}w200${item?.backdropPath}"
                val backdropImageUrl = "${BuildConfig.IMAGE_URL}original${item?.backdropPath}"
                val smallPosterImageUrl = "${BuildConfig.IMAGE_URL}w200${item?.posterPath}"
                val posterImageUrl = "${BuildConfig.IMAGE_URL}w500${item?.posterPath}"
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
                textTitle.text = item?.title
                vote.text = item?.voteAverage?.toFloat().toString()
                releaseDate.text = "∙ ${Utils.dateFormat(item?.releaseDate.toString(), "yyyy")}"
                itemRow.setOnClickListener {
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