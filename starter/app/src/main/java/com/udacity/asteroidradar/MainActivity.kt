package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//Main screen with a list of clickable asteroids using a RecyclerView with its adapter
//The NASA image of the day is displayed in the Main Screen
//Details screen that displays the selected asteroid data once it’s clicked in the Main screen as seen in the provided design. The images in the details screen are going to be provided with the starter code: an image for a potentially hazardous asteroid and another one for the non-hazardous ones, you have to display the correct image depending on the isPotentiallyHazardous asteroid parameter. Navigation xml file is already included with starter code.
//Download and parse the data from NASA NeoWS (Near Earth Object Web Service) API. As this response cannot be parsed directly with Moshi, we are providing a method to parse the data “manually” for you, it’s called parseAsteroidsJsonResult inside NetworkUtils class, we recommend trying for yourself before using this method or at least take a close look at it as it is an extremely common problem in real-world apps. For this response we need retrofit-converter-scalars instead of Moshi, you can check this dependency in build.gradle (app) file.
//When asteroids are downloaded, save them in the local database.
//Fetch and display the asteroids from the database and only fetch the asteroids from today onwards, ignoring asteroids before today.
//Display the asteroids sorted by date (Check SQLite documentation to get sorted data using a query).
//TODO - Cache the data of the asteroid by using a worker, so it downloads and saves the next 7 day's asteroids in background once a day when the device is charging and wifi is enabled.
//Download Picture of Day JSON, parse it using Moshi and display it at the top of Main screen using Picasso Library. (You can find Picasso documentation here: https://square.github.io/picasso/) You could use Glide if you are more comfortable with it, although be careful as we found some problems displaying NASA images with Glide.
//TODO - Add content description to the views: Picture of the day (Use the title dynamically for this), details images and dialog button. Check if it works correctly with talk back.
//TODO - Make sure the entire app works without an internet connection. The app can display saved asteroids from the database even if internet connection is not available
//TODO - Add talkback and push-button navigation to make an Android app accessible
//TODO - Provides descriptions for all the texts and images: Asteroid images in details screen and image of the day.
//TODO - Provides description for the details screen help button
//TODO - Modify the app to support multiple languages, device sizes, and orientations.
//TODO - Make the app delete asteroids from the previous day once a day using the same workManager that downloads the asteroids.
//TODO - Match the styles for the details screen subtitles and values to make it consistent, and make it look like what is in the designs.