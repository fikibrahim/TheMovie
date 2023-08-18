package id.taufikibrahim.domain.upcoming

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpcomingUseCaseImpl @Inject constructor(private val repository: UpcomingRepository) : UpcomingUseCase {
    override fun getUpcomingMovie(): Flow<ResultState<List<Discover>>> = repository.getUpcomingMovie()
    override fun getUpcoming(page: Int): Flow<ResultState<List<Discover>>> = repository.getUpcoming(page)
    override suspend fun getUpcomingPaging(): Flow<PagingData<Discover>> = repository.getUpcomingPaging()
}