package id.taufikibrahim.domain.toprated

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopRatedUseCaseImpl @Inject constructor(private val repository: TopRatedRepository) : TopRatedUseCase {
    override fun getTopRatedMovie(): Flow<ResultState<List<Discover>>> = repository.getTopRatedMovie()
    override fun getTopRated(page: Int): Flow<ResultState<List<Discover>>> = repository.getTopRated(page)
    override suspend fun getTopRatedPaging(): Flow<PagingData<Discover>> = repository.getTopRatedPaging()
}