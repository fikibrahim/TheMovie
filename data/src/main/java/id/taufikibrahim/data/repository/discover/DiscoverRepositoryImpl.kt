package id.taufikibrahim.data.repository.discover

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.domain.moviediscover.DiscoverRepository
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    DiscoverRepository {

    override fun getDiscoverByGenre(page: Int, genreId: Int): Flow<ResultState<List<Discover>>> = flow {
        try {
            val response = movieApiService.getMovieDiscover(page = page, with_genres = genreId)
            if (response.results.isNotEmpty()) {
                emit(ResultState.Success(response.results))
            } else {
                emit(ResultState.Empty)
            }
        } catch (t: Throwable) {
            emit(ResultState.Error(t))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDiscoverPagingByGenre(genreId: Int): Flow<PagingData<Discover>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2),
        pagingSourceFactory = { DiscoverPagingSource(this, genreId) }
    ).flow
}