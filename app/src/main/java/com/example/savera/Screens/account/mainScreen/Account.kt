package com.example.savera.Screens.account.mainScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.savera.Model.UserInformation
import com.example.savera.Navigation.Screens
import com.example.savera.Navigation.mainScreenNavigation.mainScreen
import com.example.savera.R
import com.example.savera.Screens.account.Viewmodel.AccountScreenViewmodel
import com.example.savera.Screens.account.screens.accountscreens
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun accountScreen(
    selectindex: MutableIntState,
    accountScreenViewmodel: AccountScreenViewmodel,
    nav: NavHostController,
    navController: NavHostController,

    ) {
    BackHandler {
        selectindex.value = 2
    }
    val showChangePassword = remember {
        mutableStateOf(false)
    }


    val email = FirebaseAuth.getInstance().currentUser?.email

    val data =
        remember {
            mutableStateOf<UserInformation?>(null)
        }

    val error = remember {
        mutableStateOf("")
    }

    val showLogout = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {
        delay(200)
        accountScreenViewmodel.fetchUserDetails(
            info = {
                data.value = it
            },
            failure = {
                error.value = it
            }

        )
    }


    if (data.value != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                topview()
                Column(
                    modifier = Modifier.padding(
                        start = 20.dp, end = 20.dp, bottom = 30.dp,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Spacer(modifier = Modifier.height(90.dp))
                    surfaceCreator("Edit Profile") {
                        nav.navigate(accountscreens.EditProfile.name)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    surfaceCreator(
                        "Change Password",
                        image = R.drawable.lock
                    ) {
                        if (email != null) {

                            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnCompleteListener {
                                    showChangePassword.value = true
                                }
                        }


                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    surfaceCreator(
                        "Attendance Count",
                        image = R.drawable.people
                    ) {
                        nav.navigate(accountscreens.AttendanceCount.name)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    surfaceCreator(
                        "Feedback / Suggestions",
                        image = R.drawable.feed
                    ) {

                        nav.navigate(accountscreens.Feedback.name)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    surfaceCreator(
                        "See Developers",
                        image = R.drawable.fav
                    ) {
                        nav.navigate(accountscreens.Developers.name)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    button(
                        text = "Log Out", modifier = Modifier.background(
                            color = Color(0xffFF0000), shape = RoundedCornerShape(10.dp)
                        )
                    ) {

                        showLogout.value=true

                    }
                }
            }
            Row(
                modifier = Modifier.absoluteOffset(y = 130.dp, x = 40.dp),
                verticalAlignment = Alignment.Bottom
            ) {

                accountpic(data.value!!.profilePic)


                Column {
                    textout(
                        title = data.value!!.name,
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleLarge
                    )

                    if (email != null) {
                        Text(
                            text = email,
                            fontFamily = lightrale,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 7.dp)
                        )
                    }

                }
            }
            if (showChangePassword.value) {
                showDialogue(showChangePassword)

            }

            if (showLogout.value){
                showLogoutDialogue(navController,showLogout)
            }


        }


    } else {
        Surface(modifier = Modifier.fillMaxSize()) {


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
fun showLogoutDialogue(navController: NavHostController, showLogout: MutableState<Boolean>) {
    Dialog(onDismissRequest = { /*TODO*/ }) {

        Surface(modifier = Modifier.fillMaxWidth(),
            color = Color(0xffE7E7E7),
            shape = RoundedCornerShape(20.dp)
        ){
           Column(verticalArrangement = Arrangement.Center,
               modifier = Modifier.padding(20.dp)) {

               textout(title = "Logout Confirmation",
                   modifier =Modifier , fontStyle =MaterialTheme.typography.titleMedium )

               textout(title = "Are you sure you want to log out?",
                   modifier =Modifier , fontStyle =MaterialTheme.typography.titleSmall,
                   fontFamily = lightrale)

               Row(modifier = Modifier
                   .fillMaxWidth()
                   .padding(top = 15.dp), horizontalArrangement = Arrangement.SpaceEvenly

                   ) {

                   button(text = "Yes",
                       ) {
                       showLogout.value = false

                       FirebaseAuth.getInstance().signOut()

                       navController.navigate(Screens.LoginScreen.name)


                   }

                   button(text = "No ") {
                       showLogout.value = false
                   }

               }

           }

        }

    }
    



}

@Composable
fun showDialogue(showChangePassword: MutableState<Boolean>) {
    Dialog(onDismissRequest = { /*TODO*/ }) {


        Surface(modifier = Modifier,
            color = Color(0xffE7E7E7),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    textout(
                        title = "\uD83D\uDD12 Password Change Alert! \uD83D\uDD12\n",
                        modifier = Modifier, fontStyle = MaterialTheme.typography.titleMedium,
                        color = Color(0xffFF0000)
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {

textout(title = "Important: Password Change Link Sent!\n",
    modifier = Modifier, fontStyle =MaterialTheme.typography.bodyMedium,
    fontFamily = ralewaybold)

                }
                    textout(
                        title =
                                "\uD83D\uDCE7 Check your Gmail inbox (don't forget spam!) for the password change link. Secure your account by updating your password regularly.",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.bodyMedium
                    )

                Spacer(modifier = Modifier.height(10.dp))
                
         button(text = "Okay") {
             showChangePassword.value = false
         }
         
            }
        }
    }
}

@Composable
fun accountpic(
    profilePic: String,
    modifier: Modifier = Modifier
        .size(80.dp)
        .padding(2.dp)
        .border(width = 1.dp, color = Color(0xfff707070), shape = CircleShape),
) {
    Surface(
        modifier = modifier,
        shape = CircleShape
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = profilePic),
            contentDescription = "",
            modifier = Modifier.background(color = Color.Gray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )
    }


}


@Composable
fun topview() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp), color = Color(0xffF9A825),
        shape = RoundedCornerShape(
            bottomStartPercent = 80,
            bottomEndPercent = 80
        )

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {


            Image(
                painter = painterResource(id = R.drawable.account), contentDescription = "",
                modifier = Modifier.size(50.dp)
            )

            textout(
                title = "Your Info",
                modifier = Modifier,
                fontStyle = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }


    }


}
@Composable
fun surfaceCreator(
    s: String,
    image: Int = R.drawable.account,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
        color = Color(0xffF9A825),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = image), contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            textout(
                title = s,
                modifier = Modifier.padding(16.dp),
                fontStyle = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
    }
}
