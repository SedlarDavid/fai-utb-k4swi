package repository

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import api.Api
import api.LocationApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.runBlocking

class LocationRepository(private val activityContext: Activity) {

    private val api: LocationApi = Api().location

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activityContext)

    init {
        getLastKnownLocation()
    }


    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                activityContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activityContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions



            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var locResponse: String = ""
                    runBlocking {

                        locResponse = api.getLocationByLatLng(location.latitude, location.longitude)
                    }

                    println(locResponse)

                    // use your location object
                    // get latitude , longitude and other info from this
                }

            }

    }


    fun getLocationFromLatLng() {}
    fun getLocationFromCity() {}
}