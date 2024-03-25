package com.example.savera.Screens.account.attendanceCountScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.savera.Model.UserInformation
import com.example.savera.Screens.account.Viewmodel.AccountScreenViewmodel
import com.example.savera.Screens.account.editScreen.topbar
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold

@Composable
fun attendanceCount(
    accountScreenViewmodel: AccountScreenViewmodel,
    nav: NavHostController,
    email: String?
) {
    val data = remember {
        mutableStateOf<UserInformation?>(null)
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

if (data.value!=null) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            topbar(nav = nav, Heading = "Attendance Records")
Spacer(modifier = Modifier.height(screeHeight/10))
            accountpic(profilePic = data.value!!.profilePic,
                modifier = Modifier
                    .size(130.dp)
                    .padding(2.dp)
                    .border(width = 1.dp, color = Color(0xfff707070), shape = CircleShape))

        textout(title = data.value!!.name, modifier = Modifier, fontStyle = MaterialTheme.typography.titleMedium,
            fontFamily = ralewaybold)

            if (email != null) {
                textout(title = email, modifier = Modifier,
                    fontStyle =MaterialTheme.typography.bodySmall,
                    fontFamily = lightrale
                    )
            }
Spacer(modifier = Modifier.height(30.dp))
    Surface(color = Color(0xffF9A825),
        shape = RoundedCornerShape(25.dp)
    ) {
textout(title = "Attendance Count : ${data.value!!.attendance}"
    , modifier = Modifier.padding(top = 20.dp
        , bottom = 20.dp,
        start = 40.dp,
        end = 40.dp), fontStyle =MaterialTheme.typography.titleMedium,
    color = Color.White)
    }

        }

    }


}
else{
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }

    }
}


}