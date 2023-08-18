package id.taufikibrahim.domain.moviediscover

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import id.taufikibrahim.entity.movie.Movie
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    fun getDiscoverByGenre(page: Int, genreId: Int): Flow<ResultState<List<Discover>>>
    suspend fun getDiscoverPagingByGenre(genreId: Int): Flow<PagingData<Discover>>
}