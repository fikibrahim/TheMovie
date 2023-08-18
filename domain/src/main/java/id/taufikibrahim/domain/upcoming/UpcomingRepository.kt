package id.taufikibrahim.domain.upcoming

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.flow.Flow

interface UpcomingRepository {
    fun getUpcomingMovie(): Flow<ResultState<List<Discover>>>
    fun getUpcoming(page: Int): Flow<ResultState<List<Discover>>>
    suspend fun getUpcomingPaging(): Flow<PagingData<Discover>>
}