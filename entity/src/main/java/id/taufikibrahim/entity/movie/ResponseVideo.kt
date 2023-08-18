package id.taufikibrahim.entity.movie

import com.squareup.moshi.Json

data class ResponseVideo(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "results")
    val results: List<Video>
)