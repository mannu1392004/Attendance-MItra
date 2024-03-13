package com.example.savera.Screens.homeScreen

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.navigation.NavHostController
import com.example.savera.Components.mainContent
import com.example.savera.Navigation.mainScreenNavigation.mainScreen

@Composable
fun homeScreen(selectindex: MutableIntState, mainscreennav: NavHostController) {


    BackHandler {
        selectindex.value = 2
       mainscreennav.navigate(route = mainScreen.Dashboard.name)
    }

    mainContent()

}