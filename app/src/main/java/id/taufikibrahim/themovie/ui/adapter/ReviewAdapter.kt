package id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.taufikibrahim.data.BuildConfig
import id.taufikibrahim.entity.movie.Review
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.ItemReviewBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.Utils.dateFormat
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.setResizableText

class ReviewAdapter :
    PagingDataAdapter<Review, ReviewAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review?) {
            binding.apply {
                val smallPosterImageUrl = "${BuildConfig.IMAGE_URL}w200${item?.authorDetails?.avatarPath}"
                val posterImageUrl = "${BuildConfig.IMAGE_URL}w500${item?.authorDetails?.avatarPath}"
                Glide.with(root.context)
                    .load(posterImageUrl)
                    .placeholder(R.drawable.twotone_person_24)
                    .thumbnail(Glide.with(root.context).load(smallPosterImageUrl))
                    .into(avatar)
                textName.text = item?.author
                textDate.text = if (item?.createdAt != null) dateFormat(item.createdAt.toString()) else "-"
                comment.text = item?.content
                comment.setResizableText(item?.content.toString(), 4, true)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(
                oldItem: Review,
                newItem: Review
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Review,
                newItem: Review
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}