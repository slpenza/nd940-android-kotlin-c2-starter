package com.udacity.asteroidradar.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.PicturesDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Picture
import com.udacity.asteroidradar.network.NetworkAsteroidsContainer
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import java.net.UnknownHostException

class PictureRepository (private val database: PicturesDatabase){

    val picture: LiveData<Picture?> = database.pictureDao.getPicture().map {picture->
        picture?.let {
            Log.i("PictureRepository", it.toString())
            it.asDomainModel()
        }
    }
    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            try {
                val picture = Api.retrofitServiceMoshi.getPicture()
                database.pictureDao.insert(picture.asDatabaseModel())
            } catch (exception: UnknownHostException) {
                Log.e("AsteroidsRepository", "Handle no network")
            }
        }
    }
}