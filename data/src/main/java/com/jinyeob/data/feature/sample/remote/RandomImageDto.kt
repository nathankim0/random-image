package com.jinyeob.data.feature.sample.remote

import com.jinyeob.doamin.feature.sample.model.UnsplashImageModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashImageElement(
    val id: String? = null,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    val width: Long? = null,
    val height: Long? = null,
    val color: String? = null,

    @Json(name = "blur_hash")
    val blurHash: String? = null,

    val likes: Long? = null,

    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null,

    val description: String? = null,
    val user: User? = null,

    @Json(name = "current_user_collections")
    val currentUserCollections: List<CurrentUserCollection>? = null,

    val urls: Urls? = null,
    val links: UnsplashImageLinks? = null
) {
    companion object {
        fun UnsplashImageElement.toModel() = UnsplashImageModel(url = urls?.regular ?: "")
    }
}

@JsonClass(generateAdapter = true)
data class CurrentUserCollection(
    val id: Long? = null,
    val title: String? = null,

    @Json(name = "published_at")
    val publishedAt: String? = null,

    @Json(name = "last_collected_at")
    val lastCollectedAt: String? = null,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto? = null,

    val user: User? = null
)

@JsonClass(generateAdapter = true)
data class CoverPhoto(
    val id: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val color: String? = null,

    @Json(name = "blur_hash")
    val blurHash: String? = null,

    val likes: Long? = null,

    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null,

    val description: String? = null,
    val user: User? = null,
    val urls: Urls? = null,
    val links: CoverPhotoLinks? = null
)

@JsonClass(generateAdapter = true)
data class CoverPhotoLinks(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,

    @Json(name = "download_location")
    val downloadLocation: String? = null
)

@JsonClass(generateAdapter = true)
data class UnsplashImageLinks(
    val self: String? = null,
    val html: String? = null,
    val download: String? = null,

    @Json(name = "download_location")
    val downloadLocation: String? = null
)

@JsonClass(generateAdapter = true)
data class Urls(
    val raw: String? = null,
    val full: String? = null,
    val regular: String? = null,
    val small: String? = null,
    val thumb: String? = null
)

@JsonClass(generateAdapter = true)
data class User(
    val id: String? = null,
    val username: String? = null,
    val name: String? = null,

    @Json(name = "portfolio_url")
    val portfolioURL: String? = null,

    val bio: String? = null,
    val location: String? = null,

    @Json(name = "total_likes")
    val totalLikes: Long? = null,

    @Json(name = "total_photos")
    val totalPhotos: Long? = null,

    @Json(name = "total_collections")
    val totalCollections: Long? = null,

    @Json(name = "instagram_username")
    val instagramUsername: String? = null,

    @Json(name = "twitter_username")
    val twitterUsername: String? = null,

    @Json(name = "profile_image")
    val profileImage: ProfileImage? = null,

    val links: UserLinks? = null
)

@JsonClass(generateAdapter = true)
data class UserLinks(
    val self: String? = null,
    val html: String? = null,
    val photos: String? = null,
    val likes: String? = null,
    val portfolio: String? = null
)

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null
)
