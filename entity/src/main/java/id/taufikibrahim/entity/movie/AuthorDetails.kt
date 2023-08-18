package id.taufikibrahim.entity.movie


import com.squareup.moshi.Json

data class AuthorDetails(
    @Json(name = "avatar_path")
    val avatarPath: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "rating")
    val rating: Any?,
    @Json(name = "username")
    val username: String?
)