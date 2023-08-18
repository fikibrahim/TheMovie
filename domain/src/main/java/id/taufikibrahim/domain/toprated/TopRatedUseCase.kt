package id.taufikibrahim.domain.toprated

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.flow.Flow

interface TopRatedUseCase {
    fun getTopRatedMovie(): Flow<ResultState<List<Discover>>>
    fun getTopRated(page: Int): Flow<ResultState<List<Discover>>>
    suspend fun getTopRatedPaging(): Flow<PagingData<Discover>>
}