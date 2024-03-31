package com.example.savera.Screens.dashboard.addSyllabus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.savera.Model.UserInformation
import com.example.savera.Screens.dashboard.addSyllabus.addChapters.addChapters
import com.example.savera.Screens.dashboard.addSyllabus.addTopics.addTopics
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal

@Composable
fun addSyllabusNavigation(
    userInfo: MutableState<UserInformation?>,
    dashboardViewmodel: dashboardViewmodal,
    notShowTop: MutableState<Boolean>
) {
    val navigator = rememberNavController()

    NavHost(navController = navigator, startDestination = addSyllabusScreens.AddSubjects.name ){
        composable(route = addSyllabusScreens.AddSubjects.name){
            addSyllabus(userInfo,dashboardViewmodel,notShowTop,navigator)
        }


        val route =  addSyllabusScreens.AddChapters.name
        composable(route ="$route/{subject}/{class}",
            arguments = listOf(navArgument("subject"){
                type = NavType.StringType},
                navArgument("class"){
                    type = NavType.StringType}
                )
        ){
            val  classname = it.arguments?.getString("class")
            val subject =  it.arguments?.getString("subject")

            addChapters(classname,subject,userInfo,dashboardViewmodel,navigator)
        }

        val route2= addSyllabusScreens.AddTopics.name
        composable(route ="$route2/{class}/{subject}/{chapter}",
            arguments = listOf(navArgument("class"){
                type = NavType.StringType
            },
                navArgument("subject"){
                    type = NavType.StringType
                },
                navArgument("chapter"){
                    type = NavType.StringType
                }
                )
            ){
            val  classname = it.arguments?.getString("class")
            val subject =  it.arguments?.getString("subject")
            val chapter = it.arguments?.getString("chapter")

            addTopics(classname,subject,chapter,dashboardViewmodel,userInfo)


        }

    }
}





