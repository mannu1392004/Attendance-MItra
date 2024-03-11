package com.example.savera.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.savera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(title: String) {
    androidx.compose.material3.TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFD8A09),
            ),
        title = {
            Image(  // Use Image composable for the logo
                painter = painterResource(id = R.drawable.savera_logo_removebg_preview),
                contentDescription = "App logo", // Provide content description
                modifier = Modifier.size(width = 90.dp, height = 90.dp)
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    painter= painterResource(id = R.drawable.chat_icon),
                    contentDescription = "",

                )
            }
        },
    )
}