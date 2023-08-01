package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.PicturesDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.network.NetworkAsteroidsContainer
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject

class AsteroidsRepository (private val database: PicturesDatabase){

    val asteroids: LiveData<List<Asteroid>> = database.asteroidsDao.getAsteroids().map() {
        it.asDomainModel()
    }

    suspend fun refresh(startDate:String, endDate:String?) {
        withContext(Dispatchers.IO) {
            val asteroids = parseAsteroidsJsonResult(JSONObject(Api.retrofitServiceScalar.getAsteroids(startDate, endDate)))
            val asteroidsContainer = NetworkAsteroidsContainer(asteroids.toList())
            database.asteroidsDao.insertAll(*asteroidsContainer.asDatabaseModel())
        }
    }
}