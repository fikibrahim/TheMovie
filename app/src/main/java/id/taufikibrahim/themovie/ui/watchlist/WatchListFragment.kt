package id.taufikibrahim.themoviedb_visiprimanusantara.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import id.taufikibrahim.themoviedb_visiprimanusantara.databinding.FragmentWatchListBinding
import id.taufikibrahim.themoviedb_visiprimanusantara.ui.MovieViewModel

@AndroidEntryPoint
class WatchListFragment : Fragment() {

    private var _binding: FragmentWatchListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}