package com.example.savera.Screens.dashboard.VolunteerAttendance

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.savera.Experiment.fusedLocationProviderClient
import com.example.savera.Model.UserInformation
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

@Composable
fun volunteerAttandance(notShowTop: MutableState<Boolean>,
                        userInfo: MutableState<UserInformation?>) {

    notShowTop.value = true

    val context  =  LocalContext.current

    val latitude = remember {
        mutableDoubleStateOf(0.0)
    }
    val longitude = remember {
        mutableDoubleStateOf(0.0)
    }

    val result = remember {
        mutableStateOf<Boolean?>(null)
    }

    val email = FirebaseAuth.getInstance().currentUser?.email

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    LaunchedEffect(longitude.doubleValue) {
        checkPermsission(context, longitude, latitude)
        if (
            calculateDistance(
                lat1 = latitude.doubleValue, lon1 = longitude.doubleValue,
                lat2 = 29.028771870267366, lon2 = 77.0602252677162
            )< 3000

        ) {

            result.value = true
        }
        else {
            result.value = false
        }

    }



    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                userInfo.value?.let { accountpic(profilePic = it.profilePic) }
                
                Spacer(modifier = Modifier.width(5.dp))
                
Column {


    userInfo.value?.let {
        textout(
            title = it.name,
            modifier = Modifier,
            fontStyle = MaterialTheme.typography.titleMedium
        )
    }

    textout(
        title = email.toString(),
        modifier = Modifier,
        fontStyle = MaterialTheme.typography.bodyMedium,
        fontFamily = lightrale
    )

}


            }



            if (result.value!=null){
                if (result.value!!){



                }
else{
    Text(text = "Please on your location")
}

            }
            else{
                CircularProgressIndicator()
            }



        }
    }
    
    
}


// locations

fun checkPermsission(
    context: Context,
    longitude: MutableDoubleState,
    latitude: MutableDoubleState,
) {
    val task = fusedLocationProviderClient.lastLocation




    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )

        return
    }
    task.addOnSuccessListener {
        if (it != null) {
            longitude.doubleValue = it.longitude
            latitude.doubleValue = it.latitude
        }
    }

}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
    val results = FloatArray(1)
    Location.distanceBetween(lat1, lon1, lat2, lon2, results)
    return results[0]
}