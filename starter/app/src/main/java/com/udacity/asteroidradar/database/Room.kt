package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface PictureDao {
    @Query("select * from DatabasePicture LIMIT 1")
    fun getPicture(): LiveData<DatabasePicture?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pictures: DatabasePicture)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picture: DatabasePicture)
}
@Database (entities = [DatabasePicture::class], version = 1, exportSchema = false)
abstract class PicturesDatabase: RoomDatabase() {
    abstract val pictureDao: PictureDao
}

private lateinit var INSTANCE: PicturesDatabase

fun getDatabase(context: Context): PicturesDatabase {
    synchronized(PicturesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PicturesDatabase::class.java,
                "picture").build()
        }
    }
    return INSTANCE
}