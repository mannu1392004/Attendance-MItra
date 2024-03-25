package com.example.savera.Screens.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen
import com.example.savera.R
import com.example.savera.ui.theme.ralewaybold

@SuppressLint("SuspiciousIndentation")
@Composable
fun dashboard(selectindex: MutableIntState) {
  val activity = LocalContext.current as? Activity  ?: return
    BackHandler {
       moveAppToBackground(activity)
    }


    val navigation = rememberNavController()


    NavHost(navController = navigation, startDestination = DashboardScreen.MainScreen.name){

        composable(route=DashboardScreen.MainScreen.name){
            dashboardMainScreen()
        }

    }















}



fun moveAppToBackground(activity: Activity) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
}