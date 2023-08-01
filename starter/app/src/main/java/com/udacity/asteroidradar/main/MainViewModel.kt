package com.udacity.asteroidradar.main

//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.domain.Picture
import com.udacity.asteroidradar.repo.AsteroidsRepository
import com.udacity.asteroidradar.repo.PictureRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

enum class ApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [MainFragment].
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val pictureRepository = PictureRepository(database)
    private val asteroidsRepository = AsteroidsRepository(database)

    /**
     * The internal MutableLiveData that stores the status of the most recent request
     */
    private val _status = MutableLiveData<ApiStatus>()

    /**
     * The external immutable LiveData for the request status
     */
    val status: LiveData<ApiStatus>
        get() = _status

    /**
     * Internally, we use a MutableLiveData, because we will be updating the List of [Asteroid]
     * with new values
     */
    private val _asteroids = asteroidsRepository.asteroids

    /**
     * The external LiveData interface to the [Asteroid] is immutable, so only this class can modify
     */
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _picture = pictureRepository.picture
    val picture: LiveData<Picture?>
        get() = _picture


    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        Log.i("MainViewModel", "init called")
        viewModelScope.launch {
            val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
            asteroidsRepository.refresh(formattedDate, null)
            pictureRepository.refresh()

        }
    }


    /*
        /**
         * Gets filtered Mars real estate property information from the Mars API Retrofit service and
         * updates the [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service
         * returns a coroutine Deferred, which we await to get the result of the transaction.
         * @param filter the [MarsApiFilter] that is sent as part of the web server request
         */
        private fun getAsteroids(startDate: String, endDate: String) {
            Log.i("MainViewModel", "getAsteroids called " + startDate + " " + endDate)
            viewModelScope.launch {
                Log.i("MainViewModel", "getAsteroids called - loading")
                _status.value = ApiStatus.LOADING
                try {
                    _asteroids.value = parseAsteroidsJsonResult(JSONObject(Api.retrofitServiceScalar.getAsteroids(startDate, endDate)))
                    Log.i("MainViewModel", "getAsteroids called - " + _asteroids.value.toString())
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    Log.i("MainViewModel", "getAsteroids called - " + e.toString())
                    val errorBody = (e as? HttpException)?.response()?.errorBody()?.string()
                    Log.i("MainViewModel", errorBody ?: "n/a")
                    _status.value = ApiStatus.ERROR
                    _asteroids.value = ArrayList()
                }
            }
        }

        private fun getPicture() {
            Log.i("MainViewModel", "getPicture called")
            viewModelScope.launch {
                Log.i("MainViewModel", "getPicture called - loading")
                _status.value = ApiStatus.LOADING
                try {
                    _picture.value = Api.retrofitServiceMoshi.getImageOfTheDay()
                    Log.i("MainViewModel", "getPicture called - " + _picture.value.toString())
                    _status.value = ApiStatus.DONE
                } catch (e: Exception) {
                    Log.i("MainViewModel", "getPicture called - " + e.toString())
                    val errorBody = (e as? HttpException)?.response()?.errorBody()?.string()
                    Log.i("MainViewModel", errorBody ?: "n/a")
                    _status.value = ApiStatus.ERROR
                    _picture.value = null
                }
            }
        }*/

    /**
     * Internally, we use a MutableLiveData to handle navigation to the selected [Asteroid]
     */
    private val _navigateToDetails = MutableLiveData<Asteroid?>()
    val navigateToDetails: LiveData<Asteroid?>
        get() = _navigateToDetails

    /**
     * When the property is clicked, set the [_navigateToDetails] [MutableLiveData]
     * @param asteroid The [Asteroid] that was clicked on.
     */
    fun displayDetails(asteroid: Asteroid) {
        _navigateToDetails.value = asteroid
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayDetailsComplete() {
        _navigateToDetails.value = null
    }

    fun onRecyclerViewItemClicked(asteroid: Asteroid) {
        _navigateToDetails.value = asteroid
    }

    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * by calling [getAsteroids]
     * @param startDate the start date that is sent as part of the web server request
     * @param endDate the end_date that is sent as part of the web server request
     */
    fun updateFilter(startDate: String, endDate: String) {
        viewModelScope.launch {
            asteroidsRepository.refresh(startDate, endDate)
        }
    }
}
