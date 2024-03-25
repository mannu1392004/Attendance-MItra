package com.example.savera.Screens.account

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Screens.account.Viewmodel.AccountScreenViewmodel
import com.example.savera.Screens.account.attendanceCountScreen.attendanceCount
import com.example.savera.Screens.account.editScreen.editsScreen
import com.example.savera.Screens.account.feedbackScreen.feedback
import com.example.savera.Screens.account.mainScreen.accountScreen
import com.example.savera.Screens.account.screens.accountscreens
import com.example.savera.Screens.account.seeDevelopers.seeDevops
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun attendanceScreenNav(selectindex: MutableIntState, navController: NavHostController) {

    val nav = rememberNavController()
    val email = FirebaseAuth.getInstance().currentUser?.email


    NavHost(navController = nav, startDestination = accountscreens.MainScreen.name){

        composable(route = accountscreens.MainScreen.name){
            val  accountScreenViewmodel: AccountScreenViewmodel = viewModel()
            accountScreen(selectindex = selectindex,
                accountScreenViewmodel,
                nav,navController)
        }

       composable(route = accountscreens.EditProfile.name){
           val  accountScreenViewmodel: AccountScreenViewmodel = viewModel()
           editsScreen(email,accountScreenViewmodel,nav)

       }



        composable(route = accountscreens.AttendanceCount.name){
            val  accountScreenViewmodel: AccountScreenViewmodel = viewModel()
            attendanceCount(accountScreenViewmodel,nav,email)
        }


        composable(route = accountscreens.Feedback.name){
            val  accountScreenViewmodel: AccountScreenViewmodel = viewModel()
            feedback(accountScreenViewmodel,nav,email)
        }


        composable(route  = accountscreens.Developers.name){
            seeDevops(nav)
        }
        
        
        
    }



}