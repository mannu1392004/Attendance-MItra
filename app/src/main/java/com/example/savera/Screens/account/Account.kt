package com.example.savera.Screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen

@Composable
fun accountScreen(
    x: MutableState<Int>,
    selectindex: MutableIntState,
    mainscreennav: NavHostController
) {

    Column {
        BackHandler {
            selectindex.value = 2
            mainscreennav.navigate(route = mainScreen.Dashboard.name)
        }

        Text(text = "Account screen")


        
        Text(text = x.value.toString())
        Button(onClick = { x.value++ }) {

            Text(text = "plus")
        }

    }
}