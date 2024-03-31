package com.example.savera.Screens.dashboard.addSyllabus.addTopics

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.savera.Model.UserInformation
import com.example.savera.Repository.AppRepository
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.dashboard.addSyllabus.listmakers
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.animation
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.inputValue
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.google.firebase.auth.FirebaseAuth

@Composable
fun addTopics(
    classname: String?,
    subject: String?,
    chapter: String?,
    dashboardViewmodel: dashboardViewmodal,
    userInfo: MutableState<UserInformation?>
) {

    val data = remember {
        mutableStateOf<List<String>>(emptyList())
    }

    val showdialogue = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if (classname != null) {
            if (subject != null) {
                if (chapter != null) {
                    dashboardViewmodel.fetchtopicsforedit(
                        subject = subject,
                        className = classname,
                        chapter = chapter
                    ){
                        data.value = it
                    }
                }
            }
        }
    }



    val email = FirebaseAuth.getInstance().currentUser?.email

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                userInfo.value?.let {
                    accountpic(
                        profilePic = it.profilePic,

                        modifier = Modifier
                            .size(60.dp)
                            .padding(2.dp)
                            .border(
                                width = 1.dp, color = Color(0xff707070),
                                shape = CircleShape
                            )
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column {


                    userInfo.value?.let {
                        textout(
                            title = it.name,
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleMedium
                        )
                    }

                    textout(
                        title = email.toString(),
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.bodyMedium,
                        fontFamily = lightrale
                    )

                }


            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Surface(color = Color(0xffF9A825), shape = RoundedCornerShape(5.dp)) {
                    textout(
                        title = "Chapter: $chapter", modifier = Modifier.padding(8.dp),
                        fontStyle = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }

                Surface(color = Color(0xffF9A825), shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.clickable {
                        showdialogue.value = true
                    }
                ) {
                    textout(
                        title = "Add Topics", modifier = Modifier.padding(8.dp),
                        fontStyle = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }




            }

            LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                if (data.value.isNotEmpty())
                    items(data.value){

                        listmakers(it = it){

                        }

                    }
                else{
                    item{
                        CircularProgressIndicator()
                    }
                }

            }

        }

        if (showdialogue.value) {
            if (classname != null) {
                showDialogu14(showdialogue,classname,subject,chapter)
            }
        }


    }



}




@Composable
fun showDialogu14(
    showDialog: MutableState<Boolean>,
    selectClass: String,
    subject: String?,
    chapter: String?
) {
    val subjectname = remember {
        mutableStateOf("")
    }
    val states = remember {
        mutableIntStateOf(0)
    }

    Dialog(onDismissRequest = { /*TODO*/ }) {
        if (states.intValue == 0){
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth()
                        ,
                    ) {

                        Spacer(modifier = Modifier.weight(1f))
                        textout(
                            title = "Topics",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                            modifier = Modifier.clickable {
                                showDialog.value = false
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    inputValue(name = subjectname,
                        placeholder = "Topic Name", keyboard = "e")

                    Spacer(modifier = Modifier.height(20.dp))

                    button(text = "Add") {
                        if (subjectname.value != ""){
                            states.intValue = 1
                            if (subject != null) {
                                if (chapter != null) {
                                    AppRepository.addDataToChapters(
                                        chapter =chapter ,
                                        subject = subject,
                                        className = selectClass,
                                        topic = subjectname.value
                                    ){
                                        states.intValue = 2
                                        subjectname.value = ""
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }

        if (states.intValue ==1){
            CircularProgressIndicator(color = Color(0xffF9A825))
        }

        if (states.intValue==2){

            animation(newUser = showDialog, Boolean = true, state =states )

        }
    }

}
