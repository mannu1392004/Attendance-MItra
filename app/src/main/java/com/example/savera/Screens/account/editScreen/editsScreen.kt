package com.example.savera.Screens.account.editScreen

import android.content.Context
import android.net.Uri
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.savera.Model.UserInformation
import com.example.savera.R
import com.example.savera.Screens.account.Viewmodel.AccountScreenViewmodel
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.account.screens.accountscreens
import com.example.savera.Screens.events.loadBitmap
import com.example.savera.Screens.events.uploadImageToFirebase
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale

@Composable
fun editsScreen(
    email: String? = "mannu1392004@gmail.com",
    accountScreenViewmodel: AccountScreenViewmodel,
    nav: NavHostController

) {

    val data =
        remember {
            mutableStateOf<UserInformation?>(null)
        }

    val error = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        accountScreenViewmodel.fetchUserDetails(
            info = {
                data.value = it
            },
            failure = {
                error.value = it
            }

        )
    }


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }

val State = remember {
    mutableIntStateOf(0)
}


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = Color.White
    ) {

        if (data.value != null) {


            val name = remember {
                mutableStateOf(data.value!!.name)
            }

            val gender = remember {
                mutableStateOf(data.value!!.gender)
            }

            val year = remember {
                mutableStateOf(data.value!!.year)
            }

            val phonenu = remember {
                mutableStateOf(data.value!!.phone)
            }


if (State.value==0){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                topbar(nav)
                Spacer(modifier = Modifier.height(40.dp))

                // profile pic changer
                data.value?.let { profilepicchanger(it.profilePic,imageUri,context,launcher) }

                //name
                textout(
                    title = data.value!!.name,
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleMedium
                )
                if (email != null) {
                    textout(
                        title = email,
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.bodyMedium,
                        fontFamily = lightrale
                    )
                }


                HorizontalDivider(
                    thickness = 1.5.dp,
                    color = Color(0xffF9A825),
                    modifier = Modifier.padding(
                        start = 20.dp, end = 20.dp,
                        top = 40.dp, bottom = 40.dp
                    )
                )

                Column(modifier = Modifier.padding(start = 40.dp, end = 40.dp)) {
                    // name show
                    adjustinput(value = name, singleLine = true, placeholder = "Name")
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        adjustinput(
                            value = gender, singleLine = true, placeholder = "Gender",
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xffF57F17),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(4.dp)
                                .weight(1f)
                        )

                        Spacer(modifier = Modifier.weight(0.4f))

                        adjustinput(
                            value = year, singleLine = true, placeholder = "Year",
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xffF57F17),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(4.dp)
                                .weight(1f)
                        )

                    }

                    Spacer(modifier = Modifier.height(20.dp))


                    adjustinput(
                        value = phonenu, singleLine = true, placeholder = "Phone No."
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                // save button
                button(text = "Save") {
                    if (!name.value.isNullOrBlank()&&!gender.value.isNullOrBlank()&&!phonenu.value.isNullOrBlank()&&!year.value.isNullOrBlank()) {


                        if (email != null) {

                            State.value = 1
                            if (imageUri == null)
                                accountScreenViewmodel.editData(
                                    teachergmail = email,
                                    data = hashMapOf(
                                        "Name" to name.value,
                                        "Phone" to phonenu.value,
                                        "Gender" to gender.value,
                                        "Year" to year.value
                                        ),
                                    failure = {
                                              State.value=2
                                    },
                                    info = {
                                        State.value=3
                                    }
                                )
                            else {
                                imageUri?.let {
                                    uploadImageToFirebase(
                                        context = context,
                                        eventname = email,
                                        exception = {

                                        },
                                        onSuccess = { link ->
                                            accountScreenViewmodel.editData(
                                                teachergmail = email,
                                                data = hashMapOf(
                                                    "Name" to name.value,
                                                    "Phone" to phonenu.value,
                                                    "Gender" to gender.value,
                                                    "Year" to year.value,
                                                    "ProfilePic" to link
                                                )
                                            ,
                                            failure = {
                                                State.value=2
                                            },
                                            info = {
                                                State.value=3
                                            }
                                            )
                                        },
                                        mainfoldername = "Profile Pictures",
                                        imageUri = it
                                    )

                                }


                            }


                        }

                    }

                }


            }
}

            // loading
            if (State.value==1){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            // error
            if (State.value==2){
                errorDialogue(nav)
            }

            // Success
            if (State.value==3){
                sucessfullyexecuted(nav)
            }


        }
        else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

    }


}

@Composable
fun sucessfullyexecuted(nav: NavHostController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis =1000
                ,easing = {
                    OvershootInterpolator(2f)
                        .getInterpolation(it)
                }
            ))
        nav.navigate(accountscreens.MainScreen.name)


    }
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.Transparent) {

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {


            Image(
                painter = painterResource(id = R.drawable.checklist),
                contentDescription = "",
                modifier = Modifier.scale(scale.value)
            )
        }
    }



    }

@Composable
fun errorDialogue(nav: NavHostController) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(modifier = Modifier,
            color = Color.White) {
            Column(modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                textout(title = "Something Went Wrong", modifier = Modifier, fontStyle =MaterialTheme.typography.bodyMedium)

                button(text = "Ok") {
                    nav.navigate(route = accountscreens.MainScreen.name)
                }
            }
        }
    }
}

@Composable
fun profilepicchanger(
    profilePic: String,
    imageUri: Uri?,
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Uri?>
) {



    Box(
        modifier = Modifier
            .size(90.dp)
            .clickable {
                launcher.launch("image/*")
            }

    ) {
        if (imageUri == null)

            accountpic(
                profilePic = profilePic, modifier = Modifier
                    .size(90.dp)
                    .padding(2.dp)
                    .border(width = 1.dp, color = Color(0xfff707070), shape = CircleShape)
            )
        else {
            imageUri.let {
                val bitmap = it?.let { it1 -> loadBitmap(context = context, uri = it1) }
                    .let {
                        it?.asImageBitmap()?.let { it1 ->
                            Image(
                                bitmap = it1,
                                contentDescription = "",
                                modifier = Modifier
                                    .background(color = Color.Gray, shape = CircleShape)
                                    .clip(shape = CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

            }
        }



        Image(
            painter = painterResource(id = R.drawable.camera), contentDescription = "",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(30.dp),

            )


    }

}

@Composable
fun topbar(nav: NavHostController,Heading:String="Edit Profile") {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp,top= 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "",
            tint = Color(0xffFB5607),
            modifier = Modifier.clickable {
                nav.navigate(accountscreens.MainScreen.name)
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        textout(
            title = Heading, modifier = Modifier,
            fontStyle = MaterialTheme.typography.titleLarge,
            color = Color(0xffFB5607)
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}


@Composable
fun adjustinput(
    value: MutableState<String>,
    singleLine: Boolean,
    placeholder: String,
    modifier: Modifier = Modifier
        .border(
            width = 1.dp,
            color = Color(0xffF57F17),
            shape = RoundedCornerShape(10.dp)
        )
        .padding(4.dp)
        .fillMaxWidth(),
) {

    OutlinedTextField(
        value = value.value, onValueChange = { value.value = it },

        label = {
            Text(
                text = placeholder,
                fontFamily = lightrale, style = MaterialTheme.typography.bodyMedium
            )

        },


        singleLine = singleLine, colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black

        ),
        modifier = modifier

    )
}
