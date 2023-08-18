package id.taufikibrahim.entity.movie

import com.squareup.moshi.Json

data class ResponseCast(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "cast")
    val cast: List<Cast>
)