package id.taufikibrahim.entity.movie

import com.squareup.moshi.Json

data class ResponseDiscover(
    @Json(name = "dates")
    val dates: Dates? = null,

    @Json(name = "page")
    val page: Int? = null,

    @Json(name = "total_pages")
    val totalPages: Int? = null,

    @Json(name = "results")
    val results: List<Discover>,

    @Json(name = "total_results")
    val totalResults: Int? = null
)