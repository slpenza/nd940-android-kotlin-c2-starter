package com.udacity.asteroidradar.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.database.PicturesDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Picture
import com.udacity.asteroidradar.network.asDatabaseModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class PictureRepository (private val database: PicturesDatabase){

    val picture: LiveData<Picture?> = database.pictureDao.getPicture().map {picture->
        picture?.let {
            Log.i("PictureRepository", it.toString())
            it.asDomainModel()
        }
    }
    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val picture = Api.retrofitServiceMoshi.getPicture()
            //database.pictureDao.insertAll(*picturesList.asDatabaseModel())
            database.pictureDao.insert(picture.asDatabaseModel())
        }
    }
}