package com.example.savera.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.savera.R
import com.example.savera.ui.theme.ralewayfamilt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String, showmessagetopbar: MutableState<Boolean>) {
    androidx.compose.material3.TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xffF9A825),
            ),
        title = {
            Image(  // Use Image composable for the logo
                painter = painterResource(id = R.drawable.savera_logo1_removebg_preview),
                contentDescription = "App logo", // Provide content description
                modifier = Modifier.size(width = 90.dp, height = 90.dp)
            )
        },
        actions = {
            IconButton(onClick = { showmessagetopbar.value=!showmessagetopbar.value }) {
                Icon(
                    painter= painterResource(id = R.drawable.chat_icon),
                    contentDescription = "",

                )
            }
        },
    )
}


@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenTopBar(showmessagetopbar: MutableState<Boolean>) {

    androidx.compose.material3.TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xffF9A825),
        ),
        title = {
         Row(verticalAlignment = Alignment.CenterVertically) {


               Image(  // Use Image composable for the logo
                   painter = painterResource(id = R.drawable.back),
                   contentDescription = "App logo", // Provide content description
                   modifier = Modifier.clickable(
                       interactionSource = MutableInteractionSource(),
                     indication = null,
                       onClick = {
                           showmessagetopbar.value=!showmessagetopbar.value
                       }
                   )
               )
Spacer(modifier = Modifier.width(15.dp))
               Text(text = "Groups", fontFamily = ralewayfamilt,
                   color = Color.White)
           }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(
                   imageVector = Icons.Filled.MoreVert,
                    contentDescription = "",
                    tint = Color.White

                    )
            }
        },
    )

}