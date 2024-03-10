package com.example.savera.Screens.HomeScreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController



@SuppressLint("SuspiciousIndentation")
@Preview
@Composable
fun HomeScreen() {
 val activity = LocalContext.current as? Activity

    BackHandler {
activity?.finish()
 }

    Text(text = "HomeScreen")

}