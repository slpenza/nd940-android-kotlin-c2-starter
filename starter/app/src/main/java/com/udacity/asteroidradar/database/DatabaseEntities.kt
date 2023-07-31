package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Picture

@Entity
data class DatabasePicture constructor(
    @PrimaryKey
    val url: String,
    val title: String,
    val mediaType: String
)

fun DatabasePicture.asDomainModel(): Picture {
    return Picture(
            url = this.url,
            title = this.title,
            mediaType = this.mediaType
    )
}
