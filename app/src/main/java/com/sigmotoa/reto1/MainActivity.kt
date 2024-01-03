package com.sigmotoa.reto1

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sigmotoa.reto1.model.MovieDbResult
import com.sigmotoa.reto1.ui.theme.Reto1Theme

class MainActivity : ComponentActivity() {

    private var lastLocation: String by mutableStateOf("US")
    private lateinit var fusedLocation: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                requestPopularMovies()
            }
        }

    @SuppressLint("MissingPermission")
    private fun requestPopularMovies() {
        fusedLocation.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                lastLocation = getRegionFromLocation(location)
            }
        }
    }


    private fun getRegionFromLocation(location: Location?): String {
        if (location == null) {
            return "US"
        }
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return result?.firstOrNull()?.countryCode ?: "US"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            Reto1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isLocationGranted by remember {
                        mutableStateOf(false)
                    }
                    if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        isLocationGranted = true

                    } else {
                        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    }

                    Log.d("Location", lastLocation)
                    if (isLocationGranted) {
                        GeneralMoviesScreen(region = lastLocation)
                    } else {
                        GeneralMoviesScreen(region = "US")
                    }

                }

            }
        }
    }


}