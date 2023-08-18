package id.taufikibrahim.data.repository.toprated

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.domain.popular.PopularRepository
import id.taufikibrahim.domain.toprated.TopRatedRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TopRatedRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    TopRatedRepository {
    override fun getTopRatedMovie(): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getTopRatedMovies()
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getTopRated(page: Int): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getTopRatedMovies(page = page)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTopRatedPaging(): Flow<PagingData<Discover>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { TopRatedPagingSource(this) }
    ).flow

}