package id.taufikibrahim.domain.moviedetail

import androidx.paging.PagingData
import id.taufikibrahim.entity.ResultState
import id.taufikibrahim.entity.movie.*
import kotlinx.coroutines.flow.Flow

interface MovieDetailUseCase {
    fun getMovieDetailBy(id: Int): Flow<ResultState<ResponseMovieDetail>>
    fun getMovieGenres(): Flow<ResultState<ResponseMovieDetail>>
    fun getMovieVideosBy(id: Int): Flow<ResultState<ResponseVideo>>
    fun getMovieCastBy(id: Int): Flow<ResultState<ResponseCast>>
    fun getReview(id: Int, page: Int): Flow<ResultState<List<Review>>>
    suspend fun getReviewPaging(id:Int): Flow<PagingData<Review>>

}