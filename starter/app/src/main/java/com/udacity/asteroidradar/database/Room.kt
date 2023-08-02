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
interface AsteroidsDao {
    @Query("select * from DatabaseAsteroid ORDER BY closeApproachDate")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)
}

@Dao
interface PictureDao {
    @Query("SELECT * from DatabasePicture ORDER BY date LIMIT 1")
    fun getPicture(): LiveData<DatabasePicture?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picture: DatabasePicture)
}

@Database(
    entities = [DatabaseAsteroid::class, DatabasePicture::class],
    version = 3,
    exportSchema = false
)
abstract class PicturesDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
    abstract val pictureDao: PictureDao
}

private lateinit var INSTANCE: PicturesDatabase

fun getDatabase(context: Context): PicturesDatabase {
    synchronized(PicturesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,

                PicturesDatabase::class.java,
                "picture"
            ).fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}