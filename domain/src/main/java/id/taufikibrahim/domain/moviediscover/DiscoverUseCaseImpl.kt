package id.taufikibrahim.domain.moviediscover

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.Discover
import id.taufikibrahim.entity.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverUseCaseImpl @Inject constructor(private val repository: DiscoverRepository) : DiscoverUseCase {
    override fun getDiscoverByGenre(page: Int, genreId: Int): Flow<ResultState<List<Discover>>> = repository.getDiscoverByGenre(page, genreId)
    override suspend fun getDiscoverPagingByGenre(genreId: Int): Flow<PagingData<Discover>> = repository.getDiscoverPagingByGenre(genreId)
}