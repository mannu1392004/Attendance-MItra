package com.example.savera.Screens.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Screens.dashboard.mainScreen.dashboardMainScreen
import com.example.savera.Screens.dashboard.syllabus.syllabus
import com.example.savera.Screens.dashboard.syllabusDetail.syllabusDetail
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal

@SuppressLint("SuspiciousIndentation")
@Composable
fun dashboard(selectindex: MutableIntState, notShowTop: MutableState<Boolean>) {
  val activity = LocalContext.current as? Activity  ?: return
    BackHandler {
       moveAppToBackground(activity)
    }
    val navigation = rememberNavController()

    val dashboardViewmodel: dashboardViewmodal = viewModel()

    // loading resources so that animation can be seen









    val selected = remember {
        mutableStateOf("")
    }

    NavHost(navController = navigation, startDestination = DashboardScreen.MainScreen.name){

        composable(route=DashboardScreen.MainScreen.name){

            dashboardMainScreen(navigation,dashboardViewmodel)
        }

        composable(route = DashboardScreen.Syllabus.name){
          notShowTop.value = true

            syllabus(dashboardViewmodel)
        }

composable(route = DashboardScreen.SyllabusDetail.name){
syllabusDetail(selected,dashboardViewmodel)
}



    }















}



fun moveAppToBackground(activity: Activity) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
}