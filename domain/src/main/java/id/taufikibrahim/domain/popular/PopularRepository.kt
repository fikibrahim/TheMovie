package id.taufikibrahim.domain.popular

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import kotlinx.coroutines.flow.Flow

interface PopularRepository {
    fun getPopularMovie(): Flow<ResultState<List<Discover>>>
    fun getPopular(page: Int): Flow<ResultState<List<Discover>>>
    suspend fun getPopularPaging(): Flow<PagingData<Discover>>
}