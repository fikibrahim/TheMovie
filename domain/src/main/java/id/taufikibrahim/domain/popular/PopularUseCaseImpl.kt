package id.taufikibrahim.domain.popular

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import id.taufikibrahim.entity.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularUseCaseImpl @Inject constructor(private val repository: PopularRepository) : PopularUseCase {
    override fun getPopularMovie(): Flow<ResultState<List<Discover>>> = repository.getPopularMovie()
    override fun getPopular(page: Int): Flow<ResultState<List<Discover>>> = repository.getPopular(page)
    override suspend fun getPopularPaging(): Flow<PagingData<Discover>> = repository.getPopularPaging()
}