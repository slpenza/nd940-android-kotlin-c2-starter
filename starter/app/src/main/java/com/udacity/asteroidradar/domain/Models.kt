package com.udacity.asteroidradar.domain
/*
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)
*/
data class Picture(
    val title: String,
    val url: String,
    val mediaType: String
)