package id.taufikibrahim.data.remote

import id.taufikibrahim.entity.movie.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): ResponseMovies
    
    @GET("movie/{id}")
    suspend fun getMovieDetailBy(
        @Path("id") id: Int,
    ): ResponseMovieDetail

    @GET("movie/{id}/videos")
    suspend fun getMovieVideoBy(
        @Path("id") id: Int,
    ): ResponseVideo

    @GET("movie/{id}/credits")
    suspend fun getMovieCastBy(
        @Path("id") id: Int,
    ): ResponseCast

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviewBy(
        @Path("id") id: Int,
        @Query("page") page: Int = 1,
    ): ResponseReview

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): ResponseMovieDetail

    @GET("discover/movie")
    suspend fun getMovieDiscover(
        @Query("sort_by") shortBy: String = "popularity.desc",
        @Query("with_genres") with_genres: Int,
        @Query("page") page: Int,
    ): ResponseDiscover

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): ResponseDiscover

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
    ): ResponseDiscover

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
    ): ResponseDiscover
}