package id.taufikibrahim.domain.moviedetail

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailUseCaseImpl @Inject constructor(private val repository: MovieDetailRepository) : MovieDetailUseCase {
    override fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>> = repository.getMovieDetailBy(id)
    override fun getMovieGenres(): Flow<ResultState<ResponseMovieDetail>> = repository.getMovieGenres()
    override fun getMovieVideosBy(id: Int): Flow<ResultState<ResponseVideo>> = repository.getMovieVideosBy(id)
    override fun getMovieCastBy(id: Int): Flow<ResultState<ResponseCast>> = repository.getMovieCastBy(id)
    override fun getReview(id: Int, page: Int): Flow<ResultState<List<Review>>> = repository.getReview(id, page)
    override suspend fun getReviewPaging(id: Int): Flow<PagingData<Review>> = repository.getReviewPaging(id)
}