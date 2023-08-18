package id.taufikibrahim.data.repository.detail

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.data.repository.upcoming.UpcomingPagingSource
import id.taufikibrahim.domain.moviedetail.MovieDetailRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    MovieDetailRepository {
    override fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>> = flow {
        try {
            val response = movieApiService.getMovieDetailBy(id)
            emit(ResultState.Success(response))
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieGenres(): Flow<ResultState<ResponseMovieDetail>> = flow {
        try {
            val response = movieApiService.getMovieGenres()
            emit(ResultState.Success(response))
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieVideosBy(id: Int): Flow<ResultState<ResponseVideo>> = flow {
        try {
            val response = movieApiService.getMovieVideoBy(id)
            emit(ResultState.Success(response))
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieCastBy(id: Int): Flow<ResultState<ResponseCast>> = flow {
        try {
            val response = movieApiService.getMovieCastBy(id)
            emit(ResultState.Success(response))
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getReview(id: Int, page: Int): Flow<ResultState<List<Review>>> = flow {
        try {
            val response = movieApiService.getMovieReviewBy(id = id, page = page)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getReviewPaging(id: Int): Flow<PagingData<Review>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { ReviewPagingSource(id, this) }
    ).flow
}