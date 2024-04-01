package com.example.savera.Screens.dashboard.addSyllabus

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.savera.Model.UserInformation
import com.example.savera.Repository.AppRepository
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.animation
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.inputValue
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("SuspiciousIndentation")
@Composable
fun addSyllabus(
    userInfo: MutableState<UserInformation?>,
    dashboardViewmodel: dashboardViewmodal,
    notShowTop: MutableState<Boolean>,
    navigator: NavHostController,
) {
    val classList = remember {
        mutableStateOf<List<String>>(
            listOf(
                "Class 1",
                "Class 2",
                "Class 3",
                "Class 4",
                "Class 5",
                "Class 6",
                "Class 7",
                "Class 8",
                "Class 9",
                "Class 10",
                "Class 11",
                "Class 12"
            )
        )
    }

    val showDialog = remember {
        mutableStateOf(false)
    }

    val dropdown = remember {
        mutableStateOf(false)
    }

    val selectClass = remember {
        mutableStateOf("Select Class")
    }

    val data = remember {
        mutableStateOf<List<String>>(emptyList())
    }

    if (selectClass.value != "Select Class")
        LaunchedEffect(selectClass.value) {
            dashboardViewmodel.fetchSyllabus1(
                className = selectClass.value
            ) {
                data.value = it
            }
        }



    notShowTop.value = true
    val email = FirebaseAuth.getInstance().currentUser?.email

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
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


// dropdown
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Surface(
                    color = Color(0xffF9A825),
                    shape = RoundedCornerShape(8.dp)
                ) {

                    Row(modifier = Modifier.padding(10.dp)) {
                        textout(
                            title = selectClass.value, modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        Column {


                            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.clickable {
                                    dropdown.value = true

                                })

                                DropdownMenu(
                                    expanded = dropdown.value,
                                    onDismissRequest = { dropdown.value = false },
                                    modifier = Modifier.height(250.dp).background(Color.White),

                                ) {
                                    Surface(color = Color.White) {


                                    Column(
                                        modifier = Modifier.background(Color.White)
                                    ) {


                                        classList.value.forEach {

                                            DropdownMenuItem(text = {
                                                textout(
                                                    title = it,
                                                    modifier = Modifier,
                                                    fontStyle = MaterialTheme.typography.bodyMedium,
                                                    fontFamily = lightrale

                                                )
                                            }, onClick = {

                                                selectClass.value = it
                                                dropdown.value = false

                                            })


                                        }
                                    }


                                }


                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.weight(1f))
                if (selectClass.value != "Select Class")
                    Surface(
                        color = Color(0xffF9A825),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.clickable {
                            showDialog.value = true
                        }
                    ) {
                        textout(
                            title = "Add Subjects", modifier = Modifier.padding(10.dp),
                            fontStyle = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )


                    }


            }


            // show to select Class
            if (selectClass.value == "Select Class")
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    textout(
                        title = "Please Select Class",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium,
                        color = Color.Red
                    )
                }
            else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(10.dp)
                ) {

                    LazyColumn(modifier = Modifier.padding(5.dp)) {
                        if (data.value.isNotEmpty())
                            items(data.value) {

                                listmakers(it = it) {
                                    navigator.navigate("${addSyllabusScreens.AddChapters}/$it/${selectClass.value}")
                                }

                            }

                    }
                }
            }


        }

        if (showDialog.value) {
            showDialogu11(showDialog, selectClass)
        }

    }
}

@Composable
fun showDialogu11(showDialog: MutableState<Boolean>, selectClass: MutableState<String>) {
    val subjectname = remember {
        mutableStateOf("")
    }
    val states = remember {
        mutableIntStateOf(0)
    }

    Dialog(onDismissRequest = { /*TODO*/ }) {
        if (states.value == 0) {
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                        Spacer(modifier = Modifier.weight(1f))
                        textout(
                            title = "Add Subjects",
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
                    inputValue(name = subjectname, placeholder = "Subject Name", keyboard = "e")

                    Spacer(modifier = Modifier.height(20.dp))

                    button(text = "Add") {
                        if (subjectname.value != "") {
                            states.intValue = 1
                            AppRepository.addSubjects(
                                className = selectClass.value,
                                subject = subjectname.value,
                            ) {
                                states.intValue = 2
                                subjectname.value = ""
                            }
                        }

                    }

                }
            }
        }

        if (states.intValue == 1) {
            CircularProgressIndicator(color = Color(0xffF9A825))
        }

        if (states.intValue == 2) {

            animation(newUser = showDialog, Boolean = true, state = states)

        }
    }

}

@Composable
fun listmakers(it: String, click: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable {
                click()
            }
            .fillMaxWidth()
            .padding(7.dp),
        color = Color(0xffF9A825),
        shape = RoundedCornerShape(15)
    ) {

        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            textout(
                title = it, modifier = Modifier,
                fontStyle = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }

}


