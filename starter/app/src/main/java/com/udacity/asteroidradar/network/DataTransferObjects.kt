package com.udacity.asteroidradar.network

import android.util.Log
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.domain.Picture
import com.udacity.asteroidradar.database.DatabasePicture

@JsonClass(generateAdapter = true)
data class NetworkPictureContainer(val title:String, val media_type:String, val url:String)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkPicture(
    val title: String,
    val mediaType: String,
    val url: String)

/**
 * Convert Network results to database objects
 */
fun NetworkPictureContainer.asDomainModel(): Picture {
    Log.i("DataTransferObjects", title.toString())
    return Picture(
            title = title,
            mediaType = media_type,
            url = url
    )
}

fun NetworkPictureContainer.asDatabaseModel(): DatabasePicture {
    Log.i("DataTransferObjects", title.toString())
    return DatabasePicture(
            title = title,
            mediaType = media_type,
            url = url
    )
}
