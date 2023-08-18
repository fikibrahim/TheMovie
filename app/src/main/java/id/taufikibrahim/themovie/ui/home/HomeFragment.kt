package id.taufikibrahim.themoviedb_visiprimanusantara.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import id.taufikibrahim.entity.movie.GenresItem
import id.taufikibrahim.entity.movie.Movie
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.FragmentHomeBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.MovieViewModel
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.MovieGridAdapter
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.NowPlayingAdapter
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.UpcomingAdapter
import id.taufikibrahim.themoviedb_visiprimanusantara.utils.HorizontalMarginItemDecoration
import java.lang.Math.abs
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menu.findItem(R.id.setting).isVisible = true
//                menu.findItem(R.id.watchlist).isVisible = false
//            }
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                if (menuItem.itemId == android.R.id.home) {
//                    requireActivity().onBackPressedDispatcher.onBackPressed()
//                }
//                return false
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // now playing
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is ResultState.Success -> onResultNowPlayingSuccess(movies.data)
                    is ResultState.Error -> onResultError(movies.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
        // genre
        viewModel.movieGenres.observe(viewLifecycleOwner) { genres ->
            if (genres != null) {
                when (genres) {
                    is ResultState.Success -> onResultGenresSuccess(genres.data.genres)
                    is ResultState.Error -> onResultError(genres.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
        // popular
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is ResultState.Success -> onResultPopularSuccess(movies.data)
                    is ResultState.Error -> onResultError(movies.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
        // top rated
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is ResultState.Success -> onResultTopRatedSuccess(movies.data)
                    is ResultState.Error -> onResultError(movies.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
        // upcoming
        viewModel.upcomingMovies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is ResultState.Success -> onResultUpcomingSuccess(movies.data)
                    is ResultState.Error -> onResultError(movies.throwable)
                    is ResultState.Empty -> onResultEmpty()
                }
            }
        }
    }

    // Adapter
    private val adapterNowPlaying: NowPlayingAdapter by lazy {
        NowPlayingAdapter(
            onItemClick = { movie ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToDetail(movie.id, movie.title.toString())
                )
            }
        )
    }
    private val adapterPopular: MovieGridAdapter by lazy {
        MovieGridAdapter(
            onItemClick = { movie ->
                if (movie.id == null) {
                    Toast.makeText(requireContext(), "Movie not found!", Toast.LENGTH_SHORT).show()
                    return@MovieGridAdapter
                } else {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToDetail(
                            movie.id!!.toInt(),
                            movie.title.toString()
                        )
                    )
                }
            }
        )
    }
    private val adapterTopRated: MovieGridAdapter by lazy {
        MovieGridAdapter(
            onItemClick = { movie ->
                if (movie.id == null) {
                    Toast.makeText(requireContext(), "Movie not found!", Toast.LENGTH_SHORT).show()
                    return@MovieGridAdapter
                }
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToDetail(
                        movie.id!!.toInt(),
                        movie.title.toString()
                    )
                )
            }
        )
    }
    private val adapterUpcoming: UpcomingAdapter by lazy {
        UpcomingAdapter(
            onItemClick = { movie ->
                if (movie?.id == null) {
                    Toast.makeText(requireContext(), "Movie not found!", Toast.LENGTH_SHORT).show()
                    return@UpcomingAdapter
                }
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToDetail(
                        movie.id!!.toInt(),
                        movie.title.toString()
                    )
                )
            }
        )
    }

    // state result
    private fun onResultEmpty() {
        Log.w("MovieActivity", "Empty")
        Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
    }

    private fun onResultError(throwable: Throwable) {
        Toast.makeText(requireContext(), "Error: $throwable", Toast.LENGTH_SHORT).show()
        Log.e("MovieActivity", "Error: $throwable")
    }

    private fun onResultNowPlayingSuccess(movies: List<Movie>) {
        movies.let {
            setupCarousel(it)
        }
    }

    private fun onResultGenresSuccess(genresItem: List<GenresItem?>?) {
        genresItem.let {
            genresItem?.forEachIndexed { index, item ->
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
                        HomeFragmentDirections.actionHomeToList(
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

    private fun onResultPopularSuccess(movies: List<Discover>) {
        movies.let {
            binding.apply {
                recyclerViewPopular.addItemDecoration(HorizontalMarginItemDecoration(26))
                recyclerViewPopular.adapter = adapterPopular
                adapterPopular.submitList(it)
                seeAllPopular.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToList("popular", 0, "Popular Movies")
                    )
                }
            }
        }
    }

    private fun onResultTopRatedSuccess(movies: List<Discover>) {
        movies.let {
            binding.apply {
                recyclerViewTopRated.addItemDecoration(HorizontalMarginItemDecoration(26))
                recyclerViewTopRated.adapter = adapterTopRated
                adapterTopRated.submitList(it)
                seeAllTopRated.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToList("toprated", 0, "Top Rated Movies")
                    )
                }
            }
        }
    }

    private fun onResultUpcomingSuccess(movies: List<Discover>) {
        movies.let {
            binding.apply {
                recyclerViewUpcoming.addItemDecoration(HorizontalMarginItemDecoration(36))
                recyclerViewUpcoming.adapter = adapterUpcoming
                adapterUpcoming.submitList(it)
                seeAllUpcoming.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToList("upcoming", 0, "Upcoming Movies")
                    )
                }
            }
        }
    }

    private fun setupCarousel(movies: List<Movie>) {
        binding.apply {
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
            compositePageTransformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = (0.80f + r * 0.20f)
            }
            carouselNowPlaying.setPageTransformer(compositePageTransformer)
            carouselNowPlaying.offscreenPageLimit = 1
            carouselNowPlaying.adapter = adapterNowPlaying
            adapterNowPlaying.submitList(movies)
            automateViewPagerSwiping()
        }
    }

    private fun automateViewPagerSwiping() {
        val delayMs: Long = 1000
        val periodMs: Long = 8000
        val handler = Handler()
        binding.apply {
            val update = Runnable {
                if (carouselNowPlaying.currentItem == adapterNowPlaying.currentList.size - 1) {
                    carouselNowPlaying.currentItem = 0
                } else {
                    carouselNowPlaying.setCurrentItem(carouselNowPlaying.currentItem + 1, true)
                }
            }
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, delayMs, periodMs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}