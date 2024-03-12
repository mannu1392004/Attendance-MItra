package com.example.savera.Experiment

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp1() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("nestedNavigation") { // Add nestedNavigation destination
            NestedNavigation(navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        Text(text = "Home Screen")
        Button(onClick = {
            navController.navigate("nestedNavigation")
        }) {
            Text(text = "Go to Nested Navigation")
        }
    }
}

@Composable
fun NestedNavigation(navController: NavController) {
    val nestedNavController = rememberNavController()

    NavHost(
        navController = nestedNavController,
        startDestination = "nestedHome"
    ) {
        composable("nestedHome") {
            Text(text = "Nested Home Screen")
        }
        composable("nestedDetails/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            Text(text = "Details Screen for item: $itemId")
        }
    }
}
