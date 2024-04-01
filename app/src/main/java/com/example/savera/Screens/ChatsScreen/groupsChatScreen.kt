package com.example.savera.Screens.ChatsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.savera.R
import com.example.savera.ui.theme.ralewayfamilt

@Composable
fun groupChatsScreen(messagebar: NavHostController, year: String?) {
    var showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
                end = 20.dp, top = 20.dp, bottom = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {


        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {
                messagebar.navigate(route = "chatscreen/official")
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column (modifier = Modifier.padding(start = 11.dp)){
                    Text(text = "Savera Official",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall,)
                }
            }


        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {
                if (year == "3" || year == "4") {
                    messagebar.navigate(route = "chatscreen/thirdandfour")
                } else {
                    showDialog.value = true;
                }

            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column(modifier = Modifier.padding(start = 11.dp)) {
                    Text(text = "Savera 3rd and 4th year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)
                }
            }


        }

        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {
                if (year == "2" || year == "3") {
                    messagebar.navigate(route = "chatscreen/secondandthird")
                } else {
                    showDialog.value = true;
                }


            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column(modifier = Modifier.padding(start = 11.dp)) {
                    Text(text = "Savera 2nd and 3rd year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)

                }
            }


        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {
                if (year == "1" || year == "2") {
                    messagebar.navigate(route = "chatscreen/firstandsecond")

                } else {
                    showDialog.value = true;

                }
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column (modifier = Modifier.padding(start = 11.dp)){
                    Text(text = "Savera 1st and 2nd year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)

                }
            }


        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {

                if (year == "1") {
                    messagebar.navigate(route = "chatscreen/first")
                } else {
                    showDialog.value = true;

                }

            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column (modifier = Modifier.padding(start = 11.dp)){
                    Text(text = "Savera 1st year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)

                }
            }


        }



        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {
                if (year == "2") {
                    messagebar.navigate(route = "chatscreen/second")
                } else {
                    showDialog.value = true;

                }
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column(modifier = Modifier.padding(start = 11.dp)) {
                    Text(text = "Savera 2nd year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)

                }
            }


        }

        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {
                if (year == "3") {
                    messagebar.navigate(route = "chatscreen/third")
                } else {
                    showDialog.value = true;

                }
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column (modifier = Modifier.padding(start = 11.dp)){
                    Text(text = "Savera 3rd year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)

                }
            }


        }


        Card(modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 20.dp)
            .clickable {

                if (year == "4") {
                    messagebar.navigate(route = "chatscreen/fourth")
                } else {
                    showDialog.value = true;

                }

            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(60.dp),
                    color = Color.Transparent){
                    Image(
                        painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                        contentDescription = "",
                    )
                }
                Column (modifier = Modifier.padding(start = 11.dp)){
                    Text(text = "Savera 4th year",
                        fontFamily = ralewayfamilt,
                        style= MaterialTheme.typography.headlineSmall)

                }
            }


        }


    }
    if(showDialog.value){
        notAllowedDialog(showDialog)
    }
}

@Composable
fun notAllowedDialog(showDialog: MutableState<Boolean>) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .size(200.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You are not eligible to enter this group",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    color = Color.Black
                )
            }
        }
    }
}