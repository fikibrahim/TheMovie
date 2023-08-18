package id.taufikibrahim.data.repository.upcoming

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.domain.popular.PopularRepository
import id.taufikibrahim.domain.toprated.TopRatedRepository
import id.taufikibrahim.domain.upcoming.UpcomingRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpcomingRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    UpcomingRepository {
    override fun getUpcomingMovie(): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getUpcomingMovies()
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)
    override fun getUpcoming(page: Int): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getUpcomingMovies(page = page)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getUpcomingPaging(): Flow<PagingData<Discover>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { UpcomingPagingSource(this) }
    ).flow

}