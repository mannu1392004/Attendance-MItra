package com.example.savera.Screens.dashboard.MainSyllabus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.savera.Model.ChapterList
import com.example.savera.Model.UserInformation
import com.example.savera.Model.topicList
import com.example.savera.Screens.dashboard.MainSyllabus.syllabus.syllabus
import com.example.savera.Screens.dashboard.MainSyllabus.syllabusDetail.syllabusDetail
import com.example.savera.Screens.dashboard.MainSyllabus.topicDetail.topicDetail
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal

@Composable
fun syllabusMainScreen(notShowTop: MutableState<Boolean>, userInfo: MutableState<UserInformation?>) {
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

        LaunchedEffect(selected.value) {
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

    NavHost(navController = navigation, startDestination = syllabusScreens.MainScreen.name) {

        composable(route = syllabusScreens.MainScreen.name){
            notShowTop.value = true

            syllabus(dashboardViewmodel, selectedclass,navigation,selected,userInfo)
        }

        composable(route = syllabusScreens.SyllabusDetail.name) {
            syllabusDetail(selected, dashboardViewmodel,data,chapterSelected,navigation,userInfo)
        }

        composable(route =syllabusScreens.TopicDetails.name){
            topicDetail(topicData,selected,selectedclass,dashboardViewmodel,chapterSelected,userInfo)
        }

    }







}