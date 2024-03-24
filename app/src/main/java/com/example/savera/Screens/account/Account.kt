package com.example.savera.Screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.savera.R
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.savera.ui.theme.lightrale

@Composable
fun accountScreen(
    selectindex: MutableIntState,
) {
    BackHandler {
        selectindex.value = 2
    }

    val screeHeight = LocalConfiguration.current.screenHeightDp.dp



    Box(modifier = Modifier
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
                surfaceCreator("Edit Profile")

                Spacer(modifier = Modifier.height(10.dp))
                surfaceCreator("Change Password",
                    image = R.drawable.lock)

                Spacer(modifier = Modifier.height(10.dp))
                surfaceCreator("Attendance Count",
                    image = R.drawable.people)

                Spacer(modifier = Modifier.height(10.dp))
                surfaceCreator("Feedback / Suggestions",
                    image = R.drawable.feed)

                Spacer(modifier = Modifier.height(10.dp))
                surfaceCreator("See Developers",
                    image = R.drawable.fav)

                Spacer(modifier = Modifier.height(20.dp))
                button(
                    text = "Log Out", modifier = Modifier.background(
                        color = Color(0xffFF0000), shape = RoundedCornerShape(10.dp)
                    )
                ) {

                }
            }
        }
        Row(modifier = Modifier.absoluteOffset(y = 120.dp, x = 20.dp),
            verticalAlignment = Alignment.Bottom) {
            Image(
                painter = painterResource(id = R.drawable.adduser), contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .border(
                        width = 1.dp, shape = CircleShape,
                        color = Color(0xff707070)
                    )
            )

            Column {
                textout(
                    title = "Mannu",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleLarge
                )

                Text(text = "mannu1392004@gmail.com",
                    fontFamily = lightrale,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 7.dp)
                    )

            }
        }
    }



        }
@Composable
fun topview() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
, color = Color(0xffF9A825),
        shape = RoundedCornerShape(
        bottomStartPercent = 100,
            bottomEndPercent = 10
        )

    ) {
       Column(verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier.padding(start = 30.dp)) {

           Image(
               painter = painterResource(id = R.drawable.account), contentDescription = "",
               modifier = Modifier.size(50.dp)
           )
       }


    }


}

@Composable
fun surfaceCreator(s: String,
                   image:Int= R.drawable.account
                   ) {
    Surface(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        color = Color(0xffF9A825),
        shape = CircleShape) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically) {
Image(painter = painterResource(id = image), contentDescription = "",
    modifier = Modifier.size(20.dp))
            textout(title = s,
                modifier = Modifier.padding(16.dp),
                fontStyle =MaterialTheme.typography.titleSmall,
                color = Color.White)

        }
    }
}
