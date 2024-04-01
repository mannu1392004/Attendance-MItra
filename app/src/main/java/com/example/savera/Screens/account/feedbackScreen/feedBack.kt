package com.example.savera.Screens.account.feedbackScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.savera.Model.UserInformation
import com.example.savera.Screens.account.Viewmodel.AccountScreenViewmodel
import com.example.savera.Screens.account.editScreen.topbar
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.account.screens.accountscreens
import com.example.savera.Screens.events.input
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun feedback(accountScreenViewmodel: AccountScreenViewmodel, nav: NavHostController, email: String?) {
    val data = remember {
        mutableStateOf<UserInformation?>(null)
    }

    val error = remember {
        mutableStateOf("")
    }

    val screeHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(Unit) {
        accountScreenViewmodel.fetchUserDetails(
            info = {
                data.value = it
            },
            failure = {}
        )
    }


    val feedback = remember {
        mutableStateOf("")
    }

    val state = remember {
        mutableIntStateOf(0)
    }



        if (data.value != null) {

                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        topbar(nav = nav, Heading = "Feedback / Suggestions")

                        Spacer(modifier = Modifier.height(screeHeight / 10))
                        accountpic(
                            profilePic = data.value!!.profilePic,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(2.dp)
                                .border(width = 1.dp,
                                    color = Color(0xfff707070), shape = CircleShape)
                        )

                        textout(
                            title = data.value!!.name,
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleMedium,
                            fontFamily = ralewaybold
                        )

                        if (email != null) {
                            textout(
                                title = email, modifier = Modifier,
                                fontStyle = MaterialTheme.typography.bodySmall,
                                fontFamily = lightrale
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                        textout(
                            title = "\uD83D\uDCE3 Share Your Thoughts! \uD83D\uDCE3",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleMedium
                        )
                        val text =
                            "We value your input! Please take a moment to share any suggestions or feedback you have regarding our app. Your input helps us improve our services and create a better experience for you."

                        val paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)

                        text.split("\n").forEach {
                            Text(
                                text = AnnotatedString(
                                    text = it,
                                    paragraphStyle = paragraphStyle,
                                ),
                                modifier = Modifier.padding(vertical = 8.dp),
                                style = MaterialTheme.typography.bodyMedium, color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        input(
                            value = feedback,
                            singleLine = false,
                            placeholder = "Type your ideas here.....",
                            need = false,

                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        button(text = "Submit") {
                            if (feedback.value.isNullOrBlank()) {
                                error.value =
                                    "Oops! It seems like you haven't entered any feedback. Please provide your suggestions or thoughts before submitting."
                            } else {
state.intValue = 1
                                val localDate =
                                    LocalDate.now()
                                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                                val localTime =
                                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))


                                if (email != null) {
                                    accountScreenViewmodel.addFeedBack(
                                        emailtrim = email,
                                        success = {
state.intValue = 2
                                        },
                                        failure = {

                                        },
                                        hashMap = hashMapOf(
                                            "$localDate $localTime" to feedback.value
                                        )
                                    )

                                }

                            }

                        }

                        Spacer(modifier = Modifier.height(10.dp))

textout(title = error.value, modifier = Modifier, fontStyle = MaterialTheme.typography.bodyMedium,
    color = Color.Red)

                    }
                }


            if (state.intValue==1){
                loading()
            }
            // success state
            if (state.intValue==2){
feedbackrecorde(nav,state)

            }


            }

            // loading

            else {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color(0xffF9A825))
                }

            }
        }




}
@Composable
fun feedbackrecorde(nav: NavHostController, state: MutableIntState) {
Dialog(onDismissRequest = { /*TODO*/ }) {
    Surface(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
        , color = Color(0xffE7E7E7)
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 17.dp, bottom = 17.dp)) {

            textout(title = "\uD83D\uDC4D Feedback /Suggestion Recorded! \uD83D\uDC4D",
    modifier = Modifier, fontStyle = MaterialTheme.typography.titleSmall,
                fontFamily = ralewaybold
                )
            Spacer(modifier = Modifier.height(0.dp))

            Text(
                text = AnnotatedString(
                    text =
                            "Thank you for sharing your thoughts with us! Your feedback has been successfully recorded." +
                            " We appreciate your valuable input in helping us improve our app.",
                    paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center),
                ),
                modifier = Modifier.padding( 8.dp),
                style = MaterialTheme.typography.bodyMedium, color = Color.Black
            )
            
            button(text = "  Ok  ") {
                state.value = 0
                nav.navigate(accountscreens.MainScreen.name)
            }
            
            


        }
    }
}

}

@Composable
fun loading() {
    Dialog(onDismissRequest = { /*TODO*/ }) {


        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
CircularProgressIndicator(color = Color(0xffF9A825))
        }
    }
}