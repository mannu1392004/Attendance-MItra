package com.example.savera.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Screens.HomeScreen.HomeScreen
import com.example.savera.Screens.LoadingScreen.loadingScreen
import com.example.savera.Screens.LoginScreen.LoginScreen
import com.example.savera.Screens.SplashScreen.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.name) {

        composable(route = Screens.SplashScreen.name) {
            SplashScreen(navController)
        }

        composable(route = Screens.LoginScreen.name) {
            LoginScreen(navController)
        }

        composable(route = Screens.HomeScreen.name) {
            HomeScreen()
        }

        composable(route = Screens.LoadingScreen.name){
            loadingScreen()
        }

    }


}