package id.taufikibrahim.themoviedb_visiprimanusantara.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.taufikibrahim.domain.moviedetail.MovieDetailUseCase
import id.taufikibrahim.domain.moviediscover.DiscoverUseCase
import id.taufikibrahim.domain.nowplaying.NowPlayingUseCase
import id.taufikibrahim.domain.popular.PopularUseCase
import id.taufikibrahim.domain.toprated.TopRatedUseCase
import id.taufikibrahim.domain.upcoming.UpcomingUseCase
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val nowPlayingUseCase: NowPlayingUseCase,
    private val movieDetailUseCase: MovieDetailUseCase,
    private val discoverUseCase: DiscoverUseCase,
    private val popularUseCase: PopularUseCase,
    private val topRatedUseCase: TopRatedUseCase,
    private val upcomingUseCase: UpcomingUseCase,
) : ViewModel() {
    //now playing
    val nowPlayingMovies = nowPlayingUseCase.getNowPlaying().asLiveData()

    // genres
    val movieGenres: LiveData<ResultState<ResponseMovieDetail>> =
        movieDetailUseCase.getMovieGenres().asLiveData()

    // discover
    private var _discoverFlow = MutableLiveData<PagingData<Discover>>()
    val discoverFlow: LiveData<PagingData<Discover>>
        get() = _discoverFlow

    fun getDiscoverPaging(genreId: Int) {
        viewModelScope.launch {
            discoverUseCase.getDiscoverPagingByGenre(genreId).cachedIn(viewModelScope).collectLatest {
                _discoverFlow.postValue(it)
            }
        }
    }

    //popular
    val popularMovies = popularUseCase.getPopularMovie().asLiveData()
    fun getPopularPaging() {
        viewModelScope.launch {
            popularUseCase.getPopularPaging().cachedIn(viewModelScope).collectLatest {
                _discoverFlow.postValue(it)
            }
        }
    }

    //top rated
    val topRatedMovies = topRatedUseCase.getTopRatedMovie().asLiveData()
    fun getTopRatedPaging() {
        viewModelScope.launch {
            topRatedUseCase.getTopRatedPaging().cachedIn(viewModelScope).collectLatest {
                _discoverFlow.postValue(it)
            }
        }
    }

    //upcoming
    val upcomingMovies = upcomingUseCase.getUpcomingMovie().asLiveData()
    fun getUpcomingPaging() {
        viewModelScope.launch {
            upcomingUseCase.getUpcomingPaging().cachedIn(viewModelScope).collectLatest {
                _discoverFlow.postValue(it)
            }
        }
    }

    //movie detail
    fun movieDetailBy(id: Int): LiveData<ResultState<ResponseMovieDetail>> =
        movieDetailUseCase.getMovieDetailBy(id).asLiveData()
    fun movieVideoBy(id: Int): LiveData<ResultState<ResponseVideo>> =
        movieDetailUseCase.getMovieVideosBy(id).asLiveData()
    fun movieCastBy(id: Int): LiveData<ResultState<ResponseCast>> =
        movieDetailUseCase.getMovieCastBy(id).asLiveData()
    //review
    private var _reviewFlow = MutableLiveData<PagingData<Review>>()
    val reviewFlow: LiveData<PagingData<Review>>
        get() = _reviewFlow

    fun getReviewPaging(movieId: Int) {
        viewModelScope.launch {
            movieDetailUseCase.getReviewPaging(movieId).cachedIn(viewModelScope).collectLatest {
                _reviewFlow.postValue(it)
            }
        }
    }
}