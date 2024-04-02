package com.example.savera.Screens.events

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.savera.Model.UserInformation
import com.example.savera.Model.events_Data
import com.example.savera.R
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import com.example.savera.ui.theme.ralewayfamilt
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@Composable
fun eventscreen(
    selectindex: MutableIntState,
    userInfo: MutableState<UserInformation?>,
    eventScreenViewmodel: eventScreenViewmodel = viewModel(),

    ) {
    BackHandler {
        selectindex.value = 2
    }
    val showDialogue = remember {
        mutableStateOf(false)
    }

  val events = eventScreenViewmodel.events.collectAsState()
    
  val email = FirebaseAuth.getInstance().currentUser?.email

    val valueToDelete = remember {
        mutableStateOf("")
    }
    val imgUrl = remember {
        mutableStateOf("")
    }


val deleteDialogue =   remember {
    mutableStateOf(false)
}
    Scaffold(
        modifier = Modifier.background(color = Color.White),
        floatingActionButton = {
          if (userInfo.value?.admin=="True")
            FloatingActionButton(
                onClick = { showDialogue.value = !showDialogue.value },
                modifier = Modifier.background(
                    color = Color.White
                ), containerColor = Color(0xffFB5607)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "",
                    tint = Color.White
                )

            }
        }
    ) {

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), color = Color.White
        ) {
if (events.value.isEmpty()){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        textout(title = "No  Event is Scheduled. \n Schedule an event or Wait", modifier =Modifier , fontStyle =MaterialTheme.typography.titleMedium,
            color = Color.Red)


    }




}
            
    else        
            LazyColumn {
                items(events.value){
                    eventsmaker(it,email,deleteDialogue,valueToDelete,imgUrl)
                }



            }



    if (deleteDialogue.value){
        deleteDialogueshow(valueToDelete,imgUrl,deleteDialogue,eventScreenViewmodel)
    }



if (showDialogue.value)
            alertdialogue(showDialogue)
        }
    }


}
@Composable
fun deleteDialogueshow(
    valueToDelete: MutableState<String>,
    imgUrl: MutableState<String>,
    deleteDialogue: MutableState<Boolean>,
    eventScreenViewmodel: eventScreenViewmodel
) {



    val State = remember {
        mutableIntStateOf(0)
    }
Dialog(onDismissRequest = { /*TODO*/ }) {


    if (State.value == 0) {
        Surface(modifier = Modifier.fillMaxWidth(),
            color = Color(0xffE7E7E7),
            shape = RoundedCornerShape(20.dp)
        ){
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)) {

                textout(title = "Delete Confirmation",
                    modifier =Modifier , fontStyle =MaterialTheme.typography.titleMedium )

                textout(title = "Are you sure you want to Delete?",
                    modifier =Modifier , fontStyle =MaterialTheme.typography.titleSmall,
                    fontFamily = lightrale
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp), horizontalArrangement = Arrangement.SpaceEvenly

                ) {

                    button(text = "Yes",
                    ) {
                        State.value = 1

                        eventScreenViewmodel.deleteevent(
                           document = valueToDelete.value,
                            failure = {},
                            success = {
                                      State.value = 2
                            },
                            fileUrl = imgUrl.value

                        )





                    }

                    button(text = "No ") {
                        deleteDialogue.value = false
                    }

                }

            }

        }


    }
    //loading
    if (State.value == 1){
        CircularProgressIndicator(color = Color(0xffF9A825) )
    }

    // success
    if (State.value == 2){
val scale = remember {
    androidx.compose.animation.core.Animatable(0f)
}
        LaunchedEffect(Unit) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )

            deleteDialogue.value = false

        }

        Image(painter = painterResource(id = R.drawable.checklist), contentDescription = "",
            modifier = Modifier.scale(scale.value))



    }



}





}

@Composable
fun eventsmaker(
    it: events_Data,
    email: String?,
    deleteDialogue: MutableState<Boolean>,
    valueToDelete: MutableState<String>,
    imgUrl: MutableState<String>
) {



Column(modifier = Modifier
    .fillMaxWidth()
    .padding(20.dp)) {
   Row(modifier = Modifier.fillMaxWidth()) {
       Text(
           text = it.eventName,color = Color.Black,
           fontFamily = ralewaybold,
           style = MaterialTheme.typography.headlineSmall
       )
       Spacer(modifier = Modifier.weight(1f))

       if (email==it.createdBy){
           Icon(imageVector = Icons.Filled.Delete, contentDescription ="", tint = Color.Black,
               modifier = Modifier.clickable {
deleteDialogue.value = true
                   valueToDelete.value = it.document
                   imgUrl.value = it.imageUrl


               })
       }

   }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = it.imageUrl), contentDescription = "",
            modifier = Modifier,
            contentScale = ContentScale.Crop
        )
    }
Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center) {
  Icon(painter = painterResource(id = R.drawable.calender), contentDescription ="",
      tint = Color(0xffFB5607))
    Text(text = it.date, color = Color.Black,
        fontFamily = ralewaybold,
        modifier = Modifier.padding(6.dp))


}
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Icon(painter = painterResource(id = R.drawable.claenderrrr), contentDescription ="",
                tint = Color(0xffFB5607),
                modifier = Modifier.size(18.dp))
            Text(text = it.time,color = Color.Black,
                fontFamily = ralewaybold,
                modifier = Modifier.padding(6.dp))
        }

    }
    Row(modifier = Modifier.padding(top = 20.dp),
        ) {
        Icon(painter = painterResource(id = R.drawable.docum), contentDescription ="" ,
            tint = Color(0xffFB5607),
            modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = it.description,color = Color.Black,
            fontFamily = ralewayfamilt)


    }
}
    HorizontalDivider()
}


@Composable
fun emptylist() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp)
    ) {

        textoutput(title = "No events scheduled at the", color = Color.Red, size = 4)
        textoutput(title = "the moment.Stay tuned for updates!", color = Color.Red, size = 4)

        Spacer(modifier = Modifier.height(200.dp))
        Image(
            painter = painterResource(id = R.drawable.callllll), contentDescription = "",
            modifier = Modifier.scale(1.3f)
        )


    }

}

@Composable
fun textoutput(
    title: String,
    color: Color,
    size: Int,
    fontFamily: androidx.compose.ui.text.font.FontFamily = ralewayfamilt,
) {
    Text(
        text = title,
        color = color, fontFamily = fontFamily)


}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@Composable
fun alertdialogue(
    showDialogue: MutableState<Boolean>,
    eventScreenViewmodel: eventScreenViewmodel = viewModel (),
) {
    val email = FirebaseAuth.getInstance().currentUser?.email

    var loading = remember {
        mutableStateOf(0)
    }
    
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }



    Dialog(onDismissRequest = { },
        ) {
        var eventname = remember {
            mutableStateOf("")
        }
        var dates = remember {
            mutableStateOf("")
        }

        var time = remember {
            mutableStateOf("")
        }

        var location = remember {
            mutableStateOf("")
        }

        var description = remember {
            mutableStateOf("")
        }




        Surface(modifier = Modifier,
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 10.dp, color = Color.White
        ) {
            
            if (loading.value==0)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                        modifier = Modifier.clickable {
                            showDialogue.value = !showDialogue.value
                        })

                }
                textoutput(title = "Create New Event", color = Color.Black, size = 5)


                
                
                
                input(value = eventname, placeholder = "Event Name", singleLine = true)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                    input(value = dates, singleLine = true, placeholder = "Date", modifier = Modifier.weight(1f))


                    input(value = time, singleLine = true, placeholder = "Time", modifier = Modifier.weight(1f))
                }


                input(value = location, placeholder = "Location", singleLine = true)
                
                input(value = description, singleLine = false, placeholder = "Description",
                    modifier = Modifier.height(200.dp))


                imageUri?.let {
                    val bitmap = loadBitmap(context, it)
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                        )
                    }

                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
buttonCreation(title = "Upload img", modifier = Modifier.clickable {

    launcher.launch("image/*")

})

                    buttonCreation(title = "Create", modifier = Modifier.clickable {

                        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                         val localTime = LocalTime.now().format(DateTimeFormatter.ofPattern( "HH:mm:ss"))
                        if (imageUri!= null) {
                            loading.value = 1
                            imageUri?.let {
                                uploadImageToFirebase(
                                    context = context,
                                    imageUri = it,
                                    eventname = eventname.value,
                                    onSuccess = {


                                        eventScreenViewmodel.addeventsDetail(
                                            collection = "Events",
                                            document = "$date $localTime",
                                            data =
                                            hashMapOf(
                                                "Event Name" to eventname.value,
                                                "Date" to "${dates.value}",
                                                "Time" to "${time.value}",
                                                "Location" to "${location.value}",
                                                "Description" to "${description.value}",
                                                "imageurl" to "$it",
                                                "created by" to "$email"
                                            ),
                                            onsuccess = {
                                                loading.value = 2

                                            },
                                            failure = {
                                                loading.value = 3

                                            }


                                        )


                                    },
                                    exception = {


                                    },
                                    mainfoldername = "events"
                                )
                            }

                        }


                    })

                }


                Spacer(modifier = Modifier.height(10.dp))
                
            }
            if (loading.value== 1){
                CircularProgressIndicator()
            }
            
            if (loading.value == 2){

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp).background(Color.White)) {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                            modifier = Modifier.clickable {
                                showDialogue.value = !showDialogue.value
                            }, tint = Color.Black)
                    }

                    Image(painter = painterResource(id = R.drawable.done), contentDescription = "")


                }
            }
            
            

        }
    }


}

@Composable
fun input(
    value: MutableState<String>,
    singleLine:Boolean,
    placeholder: String,
    modifier: Modifier = Modifier,
    need:Boolean=true
) {

    OutlinedTextField(value = value.value, onValueChange = { value.value = it },

        placeholder = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
           if (need) {
               Icon(
                   painter = painterResource(id = R.drawable.eventname), contentDescription = "",
                   tint = Color.Gray
               )
           }
            Text(text = placeholder, color = Color.Gray)
        }

    },

       
        singleLine = singleLine
, colors = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color.Transparent,
    unfocusedBorderColor = Color.Transparent, focusedTextColor = Color.Black, unfocusedTextColor = Color.Black

),
        modifier = modifier.border(width = 2.dp, color = Color(0xffF57F17), shape = RoundedCornerShape(10.dp))

    )
}




@Composable
fun buttonCreation(title: String, modifier: Modifier){
    Surface(color = Color(0xffFB5607),
        shape = RoundedCornerShape(10.dp)
        , modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically
            , modifier = Modifier.padding(4.dp)   ) {
            Text(text = title, modifier = Modifier.padding(10.dp), color = Color.White)
            Icon(imageVector = Icons.Filled.AddCircle, contentDescription ="",
                tint = Color.White)
        }
    }

}




