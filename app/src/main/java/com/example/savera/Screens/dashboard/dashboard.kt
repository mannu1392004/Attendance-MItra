package com.example.savera.Screens.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun dashboard(selectindex: MutableIntState, mainscreennav: NavHostController) {
  val activity = LocalContext.current as? Activity  ?: return
    BackHandler {
       moveAppToBackground(activity)
    }
    Text(text = "dashboard")
}
fun moveAppToBackground(activity: Activity) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
}