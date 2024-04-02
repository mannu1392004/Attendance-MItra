package com.example.savera.Screens.ChatsScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.savera.Model.UserInformation
import com.example.savera.Screens.messageScreen.ChatScreen
import com.example.savera.Screens.messageScreen.messageScreenViewModel


@Composable
fun chatScreen(userInfo: MutableState<UserInformation?>, selectindex: MutableIntState) {


val  messageScreenViewModel:messageScreenViewModel = viewModel ()

    val messagebar = rememberNavController()
    val year = userInfo.value?.year

    NavHost(navController = messagebar, startDestination = "Groups"){


        composable(route = "Groups"){
            groupChatsScreen(messagebar,year,selectindex)

        }
        composable(route = "chatscreen/{year}",
            arguments = listOf(navArgument("year"){
                type  = NavType.StringType
            })
        ){

            val groupSelected = it.arguments?.getString("year")

          ChatScreen(viewModel = messageScreenViewModel, msgNavigation = messagebar, groupSelected = groupSelected)
        }



    }












}
