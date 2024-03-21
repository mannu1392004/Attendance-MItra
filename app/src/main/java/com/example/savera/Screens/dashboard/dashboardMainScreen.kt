package com.example.savera.Screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.savera.R
import com.example.savera.ui.theme.ralewaybold

@Composable
fun dashboardMainScreen(){

    // color to be change
    Surface(modifier = Modifier.fillMaxSize()
        , color = MaterialTheme.colorScheme.surfaceVariant ) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                BoxesCreated(image = R.drawable.adduser, title =" Add Users ", modifier = Modifier.size(150.dp))
                BoxesCreated(image = R.drawable.addstudents, title ="Add Students", modifier =  Modifier.size(150.dp))

            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                BoxesCreated(image = R.drawable.creategroups, title ="Create Groups", modifier = Modifier.size(150.dp))
                BoxesCreated(image = R.drawable.removeaccess, title ="Remove access", modifier =  Modifier.size(150.dp))
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {


                BoxesCreated(image = R.drawable.volunteersattendance, title ="Volunteers\n" +
                        "Attendance", modifier = Modifier.size(150.dp))

                BoxesCreated(image = R.drawable.makeadmin, title ="Make Admin", modifier = Modifier.size(150.dp))

            }

        }



    }

}


@Composable
fun BoxesCreated(image:Int,title:String,modifier: Modifier){
    Surface(modifier = modifier,
        color = Color(0xffF9A825),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,


            modifier = Modifier.fillMaxSize()) {


            Image(
                painter = painterResource(id = image),
                contentDescription = ""
            )

            Text(text = title, color = Color.White,
                fontFamily = ralewaybold,
            )
        }

    }

}
