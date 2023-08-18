package id.taufikibrahim.themoviedb_visiprimanusantara.ui.home

import android.os.Bundle
import android.view.*
import androidx.annotation.NonNull
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.taufikibrahim.themoviedb_visiprimanusantara.R
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.FragmentListBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.MovieViewModel
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.LoaderStateAdapter
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.adapter.ReviewAdapter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ReviewListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()
    private val safeArgs: ReviewListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val adapter: ReviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requireActivity().addMenuProvider(object : MenuProvider {

//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                if (menuItem.itemId == android.R.id.home) {
//                    requireActivity().onBackPressedDispatcher.onBackPressed()
//                }
//                return false
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            recyclerView.adapter = adapter.withLoadStateFooter(LoaderStateAdapter { adapter.retry() })
        }
        viewModel.getReviewPaging(safeArgs.id)
        viewModel.reviewFlow.observe(viewLifecycleOwner) {
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