package id.taufikibrahim.entity.movie

import com.squareup.moshi.Json

data class ResponseReview(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "page")
    val page: Int? = null,

    @Json(name = "total_pages")
    val totalPages: Int? = null,

    @Json(name = "results")
    val results: List<Review>,

    @Json(name = "total_results")
    val totalResults: Int? = null
)