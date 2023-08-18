package id.taufikibrahim.entity.movie

import com.squareup.moshi.Json

data class Video(
    @Json(name = "iso_639_1") var iso6391: String? = null,
    @Json(name = "iso_3166_1") var iso31661: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "key") var key: String? = null,
    @Json(name = "site") var site: String? = null,
    @Json(name = "size") var size: Int? = null,
    @Json(name = "type") var type: String? = null,
    @Json(name = "official") var official: Boolean? = null,
    @Json(name = "published_at") var publishedAt: String? = null,
    @Json(name = "id") var id: String? = null
)
