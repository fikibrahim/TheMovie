package id.taufikibrahim.data.repository.popular

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.domain.popular.PopularRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PopularRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    PopularRepository {
    override fun getPopularMovie(): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getPopularMovies()
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override fun getPopular(page: Int): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getPopularMovies(page = page)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPopularPaging(): Flow<PagingData<Discover>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { PopularPagingSource(this) }
    ).flow

}