package id.taufikibrahim.themoviedb_visiprimanusantara.ui.home

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.taufikibrahim.data.BuildConfig
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.*
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.FragmentDetailBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.MovieViewModel
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.Utils


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()

    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requireActivity().addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menu.findItem(R.id.setting).isVisible = false
//                menu.findItem(R.id.watchlist).isVisible = true
//            }
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                if (menuItem.itemId == android.R.id.home) {
//                    requireActivity().onBackPressedDispatcher.onBackPressed()
//                }
//                return true
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val id = safeArgs.id
        viewModel.movieDetailBy(id).observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is ResultState.Success -> onResultSuccess(movie.data)
                    is ResultState.Error -> onResultError(movie.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
        viewModel.movieVideoBy(id).observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is ResultState.Success -> onResultVideoSuccess(movie.data)
                    is ResultState.Error -> onResultError(movie.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
        viewModel.movieCastBy(id).observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is ResultState.Success -> onResultCastSuccess(movie.data)
                    is ResultState.Error -> onResultError(movie.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
    }

    private fun onResultEmpty() {
        Log.i("DetailFragment", "Empty")
        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
    }

    private fun onResultError(throwable: Throwable) {
        Log.e("DetailFragment", "Error: $throwable")
        Toast.makeText(requireContext(), "Error: $throwable", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun onResultSuccess(movie: ResponseMovieDetail) {
        Log.i("DetailFragment", "Success: $movie")
        activity?.title = movie.title
        binding.apply {
            val smallBackdropImageUrl = "${BuildConfig.IMAGE_URL}w200${movie.backdropPath}"
            val backdropImageUrl = "${BuildConfig.IMAGE_URL}original${movie.backdropPath}"
            val smallPosterImageUrl = "${BuildConfig.IMAGE_URL}w200${movie.posterPath}"
            val posterImageUrl = "${BuildConfig.IMAGE_URL}w500${movie.posterPath}"
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
            textTitle.text = movie.title
            vote.text = movie.voteAverage?.toFloat().toString()
            releaseDate.text = "∙ ${
                Utils.dateFormat(
                    movie.releaseDate.toString(),
                    "yyyy"
                )
            } ∙ ${movie.originalLanguage?.uppercase()}"
            setupGenres(movie.genres)
            overview.text = movie.overview
            setupProduction(movie.productionCompanies)
            releaseDateFull.text = Utils.dateFormat(movie.releaseDate.toString(), "dd MMM yyyy")
            popularity.text = movie.popularity.toString()
            reviews.setOnClickListener {
                if (movie.id != null) {
                    findNavController().navigate(
                        DetailFragmentDirections.actionDetailToReview(
                            movie.id!!,
                            "Reviews: ${movie.title}"
                        )
                    )
                }
            }
        }
    }

    private fun onResultVideoSuccess(movie: ResponseVideo) {
        binding.apply {
            setupVideos(movie.results)
        }
    }

    private fun onResultCastSuccess(movie: ResponseCast) {
        binding.apply {
            setupCast(movie.cast)
        }
    }

    private fun setupGenres(genresItem: List<GenresItem?>?) {
        genresItem.let {
            genresItem?.forEach { item ->
                val chip = Chip(requireContext())
                chip.text = item?.name
                chip.setOnClickListener {
                    Log.w("TAG", "onResultGenresSuccess: $item")
                    if (item?.id == null) {
                        Toast.makeText(requireContext(), "Genre not found!", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }
                    findNavController().navigate(
                        DetailFragmentDirections.actionDetailToList(
                            "discover",
                            item.id!!,
                            "${item.name} Movie Discover"
                        )
                    )
                }
                binding.apply {
                    chipGroup.addView(chip)
                }
            }
        }
    }

    private fun setupProduction(productionsItem: List<ProductionCompaniesItem?>?) {
        productionsItem.let {
            productionsItem?.forEach { item ->
                val prod = View.inflate(
                    requireContext(),
                    R.layout.item_production,
                    null
                ) as MaterialCardView
                val imageView = prod.findViewById<ImageView>(R.id.image)
                val name = prod.findViewById<TextView>(R.id.name)
                val params = RelativeLayout.LayoutParams(
                    300,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.marginEnd = 30
                prod.layoutParams = params
                val imageUrl = "${BuildConfig.IMAGE_URL}w500${item?.logoPath}"
                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder_movie)
                    .into(imageView)
                name.text = item?.name
                binding.apply {
                    production.addView(prod)
                }
            }
        }
    }

    private fun setupCast(castItem: List<Cast?>?) {
        castItem.let {
            castItem?.forEach { item ->
                val prod = View.inflate(
                    requireContext(),
                    R.layout.item_cast,
                    null
                ) as MaterialCardView
                val imageView = prod.findViewById<ImageView>(R.id.image)
                val name = prod.findViewById<TextView>(R.id.name)
                val params = RelativeLayout.LayoutParams(
                    300,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.marginEnd = 30
                prod.layoutParams = params
                val smallImageUrl = "${BuildConfig.IMAGE_URL}w200${item?.profilePath}"
                val imageUrl = "${BuildConfig.IMAGE_URL}w500${item?.profilePath}"
                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.twotone_person_24)
                    .thumbnail(Glide.with(requireContext()).load(smallImageUrl))
                    .into(imageView)
                name.text = item?.name
//                imageView.setOnClickListener {
//                    Log.w("TAG", "onResultGenresSuccess: $item")
//                    if (item?.id == null) {
//                        Toast.makeText(requireContext(), "Genre not found!", Toast.LENGTH_SHORT).show()
//                        return@setOnClickListener
//                    }
//                    findNavController().navigate(
//                        DetailFragmentDirections.actionDetailToList("discover", item.id!!, "${item.name} Movie Discover")
//                    )
//                }
                binding.apply {
                    cast.addView(prod)
                }
            }
        }
    }

    private fun setupVideos(videosItem: List<Video?>?) {
        videosItem.let {
            videosItem?.forEach { item ->
                val itemView =
                    View.inflate(requireContext(), R.layout.item_video, null) as MaterialCardView
                val imageView = itemView.findViewById<ImageView>(R.id.image)
                val title = itemView.findViewById<TextView>(R.id.title)
                val params = RelativeLayout.LayoutParams(
                    600,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.marginEnd = 30
                itemView.layoutParams = params
                val smallImageUrl = "https://img.youtube.com/vi/${item?.key}/default.jpg"
                val imageUrl = "https://img.youtube.com/vi/${item?.key}/mqdefault.jpg"
                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_placeholder_movie)
                    .thumbnail(Glide.with(requireContext()).load(smallImageUrl))
                    .into(imageView)
                title.text = item?.name
                itemView.setOnClickListener {
                    if (item?.site == "YouTube" && item.key != null) {
                        watchYoutubeVideo(item.key.toString())
                        return@setOnClickListener
                    }
                }
                if (item?.site == "YouTube") {
                    binding.apply {
                        videos.addView(itemView)
                    }
                }
            }
        }
    }

    private fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}