package com.example.savera.Screens.dashboard.VolunteerAttendance

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import com.example.savera.Experiment.fusedLocationProviderClient
import com.example.savera.Model.UserInformation
import com.example.savera.Model.adminDetails
import com.example.savera.Repository.AppRepository
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.attendanceScreen.attendanceTaken
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewayfamilt
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun volunteerAttandance(
    notShowTop: MutableState<Boolean>,
    userInfo: MutableState<UserInformation?>,
    dashboardViewmodel: dashboardViewmodal
) {

    notShowTop.value = true
    val email = FirebaseAuth.getInstance().currentUser?.email
    val attendancetaken = remember {
        mutableStateOf<Boolean?>(null)
    }


    LaunchedEffect(Unit) {
        val date1 =  LocalDate.now()
            .format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            )

        if (email != null) {
            AppRepository.checking(
                date = date1,
                email  =  email,
            ){
                attendancetaken.value = it
            }
        }
    }


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




    var currentTime = remember {
        mutableStateOf(System.currentTimeMillis())
    }
    LaunchedEffect(Unit) {
        while (true){
            currentTime.value = System.currentTimeMillis()
            delay(1000)
        }

    }

    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val formattedTime = sdf.format(currentTime.value)

    val distance =  remember {
        mutableStateOf(0.0f)
    }

val courotine = rememberCoroutineScope()







    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    LaunchedEffect(longitude.doubleValue) {
        checkPermsission(context, longitude, latitude)

        distance.value = calculateDistance(
            lat1 = latitude.doubleValue, lon1 = longitude.doubleValue,
            lat2 = 29.028771870267366, lon2 = 77.0602252677162
        )

        if (
            distance.value<400f
        ) {

            result.value = true
        }
        else {
            result.value = false
        }

    }



    Surface(modifier = Modifier.fillMaxSize(),color = Color.White) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                userInfo.value?.let {
                    accountpic(
                        profilePic = it.profilePic,

                        modifier = Modifier
                            .size(60.dp)
                            .padding(2.dp)
                            .border(
                                width = 1.dp, color = Color(0xfff707070),
                                shape = CircleShape
                            )
                    )
                }

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

            Spacer(modifier = Modifier.height(20.dp))
            // date timer
            Surface(
                color = Color(0xffF9A825),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 18.dp, bottom = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    textout(
                        title = "Date:" + LocalDate.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium
                    )

                    textout(
                        title = "Time:$formattedTime",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium
                    )

                }
            }


        }

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(top = 10.dp)

        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {

                val porta = remember {
                    mutableStateOf<Boolean?>(null)
                }

                LaunchedEffect(Unit) {
                    AppRepository.fetchStatusforportal {
                        porta.value = it
                    }
                }





                if (attendancetaken.value != null && porta.value != null)
                  if (porta.value!!){
                    if (!attendancetaken.value!!) {
                        Column(
                            modifier = Modifier.padding(top = 40.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            if (result.value != null) {

                                // if
                                if (result.value!!) {

                                    SwipeableButton(dashboardViewmodel, email)


                                }

                                if (distance.value > 100000f) {
                                    Text(
                                        text = "Please on your location and internet",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = ralewayfamilt,
                                        color = Color.Red,
                                        textAlign = TextAlign.Center
                                    )

                                } else {
                                    Text(
                                        text = "Distance from Savera: " + distance.value.toString() + "m",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = ralewayfamilt
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        var x = 0


                                        courotine.launch {
                                            while (!result.value!!) {

                                                checkPermsission(context, longitude, latitude)
                                                x++
                                                delay(2000)

                                            }
                                        }
                                    },
                                    colors = ButtonColors(
                                        contentColor = Color.White,
                                        containerColor = Color(0xffF9A825),
                                        disabledContainerColor = Color(0xffF9A825),
                                        disabledContentColor = Color.White
                                    )
                                ) {
                                    Text(text = "Refresh")
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Note-\n"
                                            + "You must with in the radius of 30m in order to mark the attendance\n",
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    fontFamily = ralewayfamilt,
                                    style = MaterialTheme.typography.bodyMedium
                                )


                            }

                            if (attendancetaken.value == null) {
                                loadingDialogue()
                            }

                        }
                    } else {
                        attendanceTaken()
                    }
            }
                else{
                    textout(title = "Portal is Closed", modifier = Modifier,
                        fontStyle =MaterialTheme.typography.titleMedium ,
                        color = Color.Red)
                }

            }
if (userInfo.value?.admin=="True") {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {


        val (isComplete, setIsComplete) = remember {
            mutableStateOf(false)
        }
        val onoroff = remember {
            mutableStateOf(false)
        }
        LaunchedEffect(Unit) {
            AppRepository.fetchStatusforportal {
                onoroff.value = it
            }
        }



        if (!onoroff.value) {
            SwipeButton(
                text = "Turn on Portal", isComplete = isComplete,
                backgroundColor = Color(0xFF0E970E)
            ) {


                onoroff.value = !onoroff.value
                AppRepository.updatePortal(onoroff.value)
            }
        } else {

            val (isComplete1, setIsComplete3) = remember {
                mutableStateOf(false)
            }

            SwipeButton(
                text = "Turn off Portal", isComplete = isComplete1,
                backgroundColor = Color.Red
            ) {


                onoroff.value = !onoroff.value
                AppRepository.updatePortal(onoroff.value)
            }
        }


    }

    val showStudents = remember {
        mutableStateOf(false)
    }
    button(text = "See Volunteers List") {
        showStudents.value = true


    }


    if (showStudents.value) {


        showUsersList(showStudents, dashboardViewmodel)

    }

}


        }



    }
    
    
}
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@Composable
fun showUsersList(value: MutableState<Boolean>, dashboardViewmodel: dashboardViewmodal) {
Dialog(onDismissRequest = { value.value = !value.value }) {
    val data = remember {
        mutableStateOf<List<adminDetails?>>(emptyList())
    }
    LaunchedEffect(Unit) {
        dashboardViewmodel.fetchAdmins {
            data.value =  it

        }

    }


    Surface(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
        color = Color.White) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 20.dp)){
            textout(title = "Volunteers", modifier = Modifier, fontStyle =MaterialTheme.typography.titleMedium )






         LazyColumn() {


             items(data.value){

                 val status = remember {
                     mutableStateOf(false) }


LaunchedEffect(Unit) {
    AppRepository.fetchStatusforportal {
        status.value = it
    }
}





                 Row(modifier = Modifier
                     .fillMaxWidth()
                     .padding(5.dp),
                     verticalAlignment = Alignment.CenterVertically
                 ) {

                     if (it != null) {
                         accountpic(profilePic = it.image)
                         textout(title = it.name,
                             modifier = Modifier, fontStyle =MaterialTheme.typography.bodyLarge )
                         Spacer(modifier = Modifier.weight(1f))
                         Checkbox(checked = status.value,
                             onCheckedChange ={bollean->

                               status.value = !status.value


                                 status.value.let { it1 ->

                                     AppRepository.markAttendance(it.email)

                                 }

                             } )
                     }

                 }
             }

         }



        }
    }


}


}

@Composable
fun loadingDialogue() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = Color(0xffF9A825))
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