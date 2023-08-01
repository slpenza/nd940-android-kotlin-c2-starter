package com.udacity.asteroidradar.network

import android.util.Log
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabasePicture
import com.udacity.asteroidradar.domain.Picture

/**
 * "Data transfer objects are responsible for parsing and representing values on the network while domain objects are the core data representation for our app."
 * https://learn.udacity.com/nanodegrees/nd940/parts/cd0636/lessons/7ee66103-41de-4bb8-9948-d46a209400cd/concepts/66e90a22-53c7-4f5c-911e-1778a8428c8f
 */

/**
 * ASTEROIDS
 * https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
 * {
 *   "links": {
 *     "next": "http://www.neowsapp.com/rest/v1/feed?start_date=2015-09-08&end_date=2015-09-09&detailed=false&api_key=DEMO_KEY",
 *     "prev": "http://www.neowsapp.com/rest/v1/feed?start_date=2015-09-06&end_date=2015-09-07&detailed=false&api_key=DEMO_KEY",
 *     "self": "http://www.neowsapp.com/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&detailed=false&api_key=DEMO_KEY"
 *   },
 *   "element_count": 24,
 *   "near_earth_objects": {
 *     "2015-09-08": [
 *       {
 *         "links": {
 *           "self": "http://www.neowsapp.com/rest/v1/neo/3726710?api_key=DEMO_KEY"
 *         },
 *         "id": "3726710",
 *         "neo_reference_id": "3726710",
 *         "name": "(2015 RC)",
 *         "nasa_jpl_url": "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3726710",
 *         "absolute_magnitude_h": 24.3,
 *         "estimated_diameter": {
 *           "kilometers": {
 *             "estimated_diameter_min": 0.0366906138,
 *             "estimated_diameter_max": 0.0820427065
 *           },
 *           "meters": {
 *             "estimated_diameter_min": 36.6906137531,
 *             "estimated_diameter_max": 82.0427064882
 *           },
 *           "miles": {
 *             "estimated_diameter_min": 0.0227984834,
 *             "estimated_diameter_max": 0.0509789586
 *           },
 *           "feet": {
 *             "estimated_diameter_min": 120.3760332259,
 *             "estimated_diameter_max": 269.1689931548
 *           }
 *         },
 *         "is_potentially_hazardous_asteroid": false,
 *         "close_approach_data": [
 *           {
 *             "close_approach_date": "2015-09-08",
 *             "close_approach_date_full": "2015-Sep-08 09:45",
 *             "epoch_date_close_approach": 1441705500000,
 *             "relative_velocity": {
 *               "kilometers_per_second": "19.4850295284",
 *               "kilometers_per_hour": "70146.106302123",
 *               "miles_per_hour": "43586.0625520053"
 *             },
 *             "miss_distance": {
 *               "astronomical": "0.0269230459",
 *               "lunar": "10.4730648551",
 *               "kilometers": "4027630.320552233",
 *               "miles": "2502653.4316094954"
 *             },
 *             "orbiting_body": "Earth"
 *           }
 *         ],
 *         "is_sentry_object": false
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroidsContainer(val near_earth_objects: List<com.udacity.asteroidradar.Asteroid>)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun NetworkAsteroidsContainer.asDomainModel(): List<Asteroid> {
    //Log.i("DataTransferObjects", title.toString())
    return near_earth_objects.map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun NetworkAsteroidsContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    //Log.i("DataTransferObjects", title.toString())
    return near_earth_objects.map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

/**
 * IMAGE OF THE DAY
 */
@JsonClass(generateAdapter = true)
data class NetworkPictureContainer(val title: String, val media_type: String, val url: String)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkPicture(
    val title: String,
    val mediaType: String,
    val url: String
)

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
