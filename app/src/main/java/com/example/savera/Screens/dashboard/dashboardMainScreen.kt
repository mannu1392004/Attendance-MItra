package com.example.savera.Screens.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.savera.R
import com.example.savera.Screens.homeScreen.animation
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.inputValue
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import kotlinx.coroutines.delay

@Composable
fun dashboardMainScreen(dashboardViewmodel: dashboardViewmodel= viewModel()) {
    val showAddUserDialogue = remember {
        mutableStateOf(false)
    }

    val showAddStudentDialogue = remember {
        mutableStateOf(false)
    }

    val showRemoveAccess = remember {
        mutableStateOf(false)
    }

    val showMakeAdmin = remember {
        mutableStateOf(false)
    }


    // color to be change
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
           ,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                // Add User
                BoxesCreated(image = R.drawable.adduser,
                    title = " Add Users ",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            showAddUserDialogue.value = true

                        }
                )



                BoxesCreated(
                    image = R.drawable.addstudents,
                    title = "Add Students",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            showAddStudentDialogue.value = true
                        }
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BoxesCreated(
                    image = R.drawable.clipboard,
                    title = "Syllabus",
                    modifier = Modifier.size(150.dp)
                )


                BoxesCreated(
                    image = R.drawable.volunteersattendance, title = "Volunteers\n" +
                            "Attendance", modifier = Modifier.size(150.dp)
                )


            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {



                BoxesCreated(
                    image = R.drawable.makeadmin,
                    title = "Make Admin",
                    modifier = Modifier.size(150.dp)
                )

                BoxesCreated(
                    image = R.drawable.removeaccess,
                    title = "Remove access",
                    modifier = Modifier.size(150.dp)
                )


            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {



                BoxesCreated(
                    image = R.drawable.list,
                    title = "Add Syllabus",
                    modifier = Modifier.size(150.dp)
                )

                BoxesCreated(
                    image = R.drawable.delete,
                    title = "Del. Student",
                    modifier = Modifier.size(150.dp)
                )


            }






        }


        if (showAddUserDialogue.value) {
            AdduserDialogue(showAddUserDialogue)
        }

        if (showAddStudentDialogue.value) {
            AddStudents(showAddStudentDialogue,dashboardViewmodel)
        }

        if (showRemoveAccess.value) {
        }

        if (showMakeAdmin.value) {
        }


    }

}

@SuppressLint("SuspiciousIndentation")
@Composable
fun AddStudents(
    showAddStudentDialogue: MutableState<Boolean>,
    dashboardViewmodel: dashboardViewmodel
) {

val phoneno =  remember {
    mutableStateOf("")
}

val name = remember {
    mutableStateOf("")
}
val dropdownmenu = remember {
    mutableStateOf(false)
}

val classselected  = remember {
    mutableStateOf("")
}

val fatherName = remember {
    mutableStateOf("")
}

val state = remember {
    mutableStateOf(0)
}

val error= remember {
    mutableStateOf("")
}


val list = dashboardViewmodel.classList.collectAsState()

 Dialog(onDismissRequest = { /*TODO*/ }) {
Surface {

    if (state.value==0)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                modifier = Modifier.clickable {
                    showAddStudentDialogue.value = false
                })
        }
       textout(title = "Add Student", modifier = Modifier, fontStyle =MaterialTheme.typography.titleLarge)


        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            textout(title = "Class-", modifier = Modifier, fontStyle = MaterialTheme.typography.bodyLarge)

            textout(title = classselected.value, modifier = Modifier, fontStyle =MaterialTheme.typography.titleMedium )
if (!list.value.isNullOrEmpty())
           Column {


            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "",
              modifier = Modifier.clickable {
                  dropdownmenu.value = !dropdownmenu.value
              }  )
            DropdownMenu(
                expanded = dropdownmenu.value,
                onDismissRequest = { dropdownmenu.value = false },
                modifier = Modifier.height(150.dp),

                ) {
                list.value.forEach {
                    DropdownMenuItem(text = {
                        Text(
                            text = it, fontFamily = lightrale
                        )
                    }, onClick = {
                        dropdownmenu.value = false
                        classselected.value = it
                    }

                    )


                }


            }

        }
        }

        inputValue(name = name, placeholder = "Name", keyboard ="" )

        inputValue(name =fatherName , placeholder = "Father's Name", keyboard ="" )

        inputValue(name = phoneno, placeholder = "Phone No.", keyboard ="number")

        button(text = "Add") {
            if (!name.value.isNullOrEmpty() && !classselected.value.isNullOrEmpty()) {
                state.value = 1
                dashboardViewmodel.addStudent(
                    className = classselected.value,
                    name = name.value,
                    success = {
                        state.value = 3
                        name.value = ""
                        fatherName.value = ""
                        phoneno.value = ""

                    },
                    error = {
error.value = it
                        state.value = 2

                    },
                    data = hashMapOf(
                        "Name" to name.value,
                        "Father's Name" to fatherName.value,
                        "Phone No." to phoneno.value
                    )
                )
            }
        }
    }
}

     if (state.value ==1) {
         val showerror = remember {
             mutableStateOf(false)
         }


             Column(
                 modifier = Modifier.fillMaxSize(),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
                 CircularProgressIndicator(
                     color = Color(0xffF9A825)
                 )
                 LaunchedEffect(Unit) {
                     delay(5000)
                     showerror.value = true

                 }
                 if (showerror.value)
                     textout(
                         title = "Check Your Internet",
                         modifier = Modifier,
                         fontStyle = MaterialTheme.typography.titleLarge
                     )

         }
     }
     if (state.value==2){
     Surface {
         Column(modifier = Modifier
             .fillMaxWidth()
             .padding(10.dp),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally,
             ) {
             textout(title = error.value, modifier = Modifier, fontStyle =MaterialTheme.typography.bodyLarge )
             button(text = "Ok") {
                 showAddStudentDialogue.value = false
             }
         }
     }


     }


     if (state.value==3){
         animation(newUser = showAddStudentDialogue, Boolean = true,state)
     }




 }



}

@Composable
fun AdduserDialogue(showAddUserDialogue: MutableState<Boolean>) {
    val openUrlLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    Dialog(onDismissRequest = { /*TODO*/ }) {
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }


        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)) {

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                        modifier = Modifier.clickable {
                            showAddUserDialogue.value = false
                        })
                }

                textout(title = "Add User", modifier = Modifier, fontStyle =MaterialTheme.typography.titleLarge )


              textout(
                  title = "Due to security reasons, users should only" +
                      " be added via the website." +
                      "\n" +
                      "\nWe apologize for any inconvenience this may" +
                      " cause.", modifier =Modifier , fontStyle =MaterialTheme.typography.bodyLarge )



          button(text = "Go To Website") {
              val intent = Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse("https://console.firebase.google.com/u/1/project/savera-504a2/authentication/users")
              )
              openUrlLauncher.launch(intent)
          }
          }

            }
        }
    }




@Composable
fun BoxesCreated(image: Int, title: String, modifier: Modifier) {
    Surface(
        modifier = modifier
            .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
        color = Color(0xffF9A825),
        shape = RoundedCornerShape(20.dp),
       shadowElevation = 5.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,


            modifier = Modifier.fillMaxSize()
        ) {


            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(65.dp)
            )

            Text(
                text = title, color = Color.White,
                fontFamily = ralewaybold,
            )
        }

    }

}
