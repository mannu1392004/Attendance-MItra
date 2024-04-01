package com.example.savera.Screens.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Model.UserInformation
import com.example.savera.Screens.dashboard.MainSyllabus.syllabusMainScreen
import com.example.savera.Screens.dashboard.VolunteerAttendance.volunteerAttandance
import com.example.savera.Screens.dashboard.addSyllabus.addSyllabusNavigation
import com.example.savera.Screens.dashboard.mainScreen.dashboardMainScreen
import com.example.savera.Screens.dashboard.seeFeedbacks.seeFeedback
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@Composable
fun dashboard(
    selectindex: MutableIntState,
    notShowTop: MutableState<Boolean>,
    userInfo: MutableState<UserInformation?>
) {
    val activity = LocalContext.current as? Activity ?: return
    BackHandler {
        moveAppToBackground(activity)
    }
    val navigation = rememberNavController()

    val dashboardViewmodel: dashboardViewmodal = viewModel()



    NavHost(navController = navigation, startDestination = DashboardScreen.MainScreen.name) {

        composable(route = DashboardScreen.MainScreen.name) {

            dashboardMainScreen(navigation, dashboardViewmodel,userInfo)
        }

        composable(route = DashboardScreen.Syllabus.name) {
            syllabusMainScreen(notShowTop,userInfo)
        }
        composable(route = DashboardScreen.VolunteersAttendance.name) {
            volunteerAttandance(notShowTop, userInfo, dashboardViewmodel)
        }

        composable(route = DashboardScreen.AddSyllabus.name){
           addSyllabusNavigation(userInfo,dashboardViewmodel,notShowTop)
        }

        composable(route = DashboardScreen.SeeFeedback.name){
            seeFeedback(dashboardViewmodel,notShowTop)
        }
    }


}


fun moveAppToBackground(activity: Activity) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
}