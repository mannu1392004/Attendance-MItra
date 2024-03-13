package com.example.savera.Screens.events

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavHostController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen

@Composable
fun eventscreen(selectindex: MutableIntState, mainscreennav: NavHostController) {
    BackHandler {
        selectindex.value = 2
        mainscreennav.navigate(route = mainScreen.Dashboard.name)
    }
    Text(text = "eventscreen")
}