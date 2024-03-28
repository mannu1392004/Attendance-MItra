package com.example.savera.Screens.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Model.ChapterList
import com.example.savera.Model.topicList
import com.example.savera.Screens.dashboard.mainScreen.dashboardMainScreen
import com.example.savera.Screens.dashboard.syllabus.syllabus
import com.example.savera.Screens.dashboard.syllabusDetail.syllabusDetail
import com.example.savera.Screens.dashboard.topicDetail.topicDetail
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal

@SuppressLint("SuspiciousIndentation")
@Composable
fun dashboard(selectindex: MutableIntState, notShowTop: MutableState<Boolean>) {
    val activity = LocalContext.current as? Activity ?: return
    BackHandler {
        moveAppToBackground(activity)
    }
    val navigation = rememberNavController()


    val selectedclass = remember {
        mutableStateOf("Select the Class")
    }

    val dashboardViewmodel: dashboardViewmodal = viewModel()

// which subject is selected
    val selected = remember {
        mutableStateOf("")

    }

// used for chapters
    val data = remember {
        mutableStateOf<List<ChapterList>>(emptyList())
    }


    if (selected.value != "") {

        LaunchedEffect(Unit) {
            dashboardViewmodel.fetchChapters(
                classname = selectedclass.value,
               subject = selected.value,
                successful = {
                    data.value = it
                }


            )


        }

    }


    // selection for chapters
val chapterSelected = remember {
    mutableStateOf("")
}

    val topicData = remember {
        mutableStateOf<List<topicList>>(emptyList())
    }




    if (chapterSelected.value!="")
LaunchedEffect(chapterSelected.value) {

dashboardViewmodel.fetchTopics(
    subject = selected.value,
    className = selectedclass.value,
    chapter = chapterSelected.value,
    success = {
topicData.value  = it
    }

)

}




    NavHost(navController = navigation, startDestination = DashboardScreen.MainScreen.name) {

        composable(route = DashboardScreen.MainScreen.name) {

            dashboardMainScreen(navigation, dashboardViewmodel)
        }

        composable(route = DashboardScreen.Syllabus.name){
            notShowTop.value = true

            syllabus(dashboardViewmodel, selectedclass,navigation,selected)
        }

        composable(route = DashboardScreen.SyllabusDetail.name) {
            syllabusDetail(selected, dashboardViewmodel,data,chapterSelected,navigation)
        }

        composable(route = DashboardScreen.TopicDetails.name){
            topicDetail(topicData,selected,selectedclass,dashboardViewmodel,chapterSelected)
        }

    }


}


fun moveAppToBackground(activity: Activity) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    activity.startActivity(intent)
}