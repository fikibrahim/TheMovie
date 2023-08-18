package id.taufikibrahim.themoviedb_visiprimanusantara.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.FragmentListBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.MovieViewModel
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.DiscoverAdapter
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.LoaderStateAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private val safeArgs: MovieListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val adapter: DiscoverAdapter by lazy {
        DiscoverAdapter(
            onItemClick = { movie ->
                if (movie?.id == null) {
                    Toast.makeText(requireContext(), "Movie not found!", Toast.LENGTH_SHORT).show()
                    return@DiscoverAdapter
                }
                findNavController().navigate(
                    MovieListFragmentDirections.actionListToDetail(movie.id!!.toInt(), movie.title.toString())
                )
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requireActivity().addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menu.findItem(R.id.setting).isVisible = false
//                menu.findItem(R.id.watchlist).isVisible = false
//            }
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                if (menuItem.itemId == android.R.id.home) {
//                    requireActivity().onBackPressedDispatcher.onBackPressed()
//                }
//                return true
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.apply {
            recyclerView.adapter = adapter.withLoadStateFooter(LoaderStateAdapter { adapter.retry() })
        }
        val id = safeArgs.id
        when (safeArgs.type) {
            "discover" -> {
                viewModel.getDiscoverPaging(id)
            }
            "popular" -> {
                viewModel.getPopularPaging()
            }
            "toprated" -> {
                viewModel.getTopRatedPaging()
            }
            "upcoming" -> {
                viewModel.getUpcomingPaging()
            }
        }
        viewModel.discoverFlow.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}