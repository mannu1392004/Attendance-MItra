package com.example.savera.Screens.dashboard.mainScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.savera.Model.UserInformation
import com.example.savera.Model.adminDetails
import com.example.savera.Model.studentAttendanceData
import com.example.savera.R
import com.example.savera.Repository.AppRepository
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.dashboard.DashboardScreen
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.animation
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.inputValue
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import com.example.savera.ui.theme.ralewayfamilt
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun dashboardMainScreen(
    navigation: NavHostController,
    dashboardViewmodel: dashboardViewmodal,
    userInfo: MutableState<UserInformation?>
) {
    val showAddUserDialogue = remember {
        mutableStateOf(false)
    }

    val showAddStudentDialogue = remember {
        mutableStateOf(false)
    }

    val showSeeAttendance = remember {
        mutableStateOf(false)
    }

    val showMakeAdmin = remember {
        mutableStateOf(false)
    }

    val adminDetails = remember {
        mutableStateOf(userInfo.value?.admin=="True")
    }

    val showaccessEror = remember {
        mutableStateOf(false)
    }

    // color to be change
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = Color.White
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
                            if (adminDetails.value) {
                                showAddStudentDialogue.value = true
                            } else {
                                showaccessEror.value = true
                            }

                        }
                )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BoxesCreated(
                    image = R.drawable.clipboard,
                    title = "Syllabus",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {

                            navigation.navigate(DashboardScreen.Syllabus.name)

                        }
                )


                BoxesCreated(
                    image = R.drawable.volunteersattendance,
                    title = "Volunteers\n" +
                            "Attendance", modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            navigation.navigate(DashboardScreen.VolunteersAttendance.name)
                        }
                )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                BoxesCreated(
                    image = R.drawable.makeadmin,
                    title = "Modify Admins",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            if (adminDetails.value) {
                                showMakeAdmin.value = true
                            } else {
                                showaccessEror.value = true
                            }

                        }
                )

                BoxesCreated(
                    image = R.drawable.removeaccess,
                    title = "See\nAttendance",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            if (adminDetails.value)
                                showSeeAttendance.value = true
                            else {
                                showaccessEror.value = true
                            }

                        }
                )


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                BoxesCreated(
                    image = R.drawable.list,
                    title = "Add Syllabus",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {

                            if (adminDetails.value)
                                navigation.navigate(DashboardScreen.AddSyllabus.name)
                            else {
                                showaccessEror.value = true
                            }
                        }
                )

                BoxesCreated(
                    image = R.drawable.customer_review,
                    title = "See Feedbacks",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            if (adminDetails.value)
                                navigation.navigate(DashboardScreen.SeeFeedback.name)
                            else {
                                showaccessEror.value = true
                            }

                        }
                )


            }


        }


        if (showAddUserDialogue.value) {
            AdduserDialogue(showAddUserDialogue)
        }

        if (showAddStudentDialogue.value) {
            AddStudents(showAddStudentDialogue, dashboardViewmodel)
        }

        if (showSeeAttendance.value) {
            showSeeAttendanceDialogue(showSeeAttendance,dashboardViewmodel)

        }

        if (showMakeAdmin.value) {
            ShowMakeAdmindialogue(dashboardViewmodel,showMakeAdmin)
        }

        if (showaccessEror.value){
            showaccessError(showaccessEror)

        }


    }

}

@Composable
fun showaccessError(showaccessEror: MutableState<Boolean>) {

    Dialog(onDismissRequest = { /*TODO*/ }) {


    Surface(Modifier.fillMaxWidth(),
        color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            textout(
                title = "Error",
                modifier = Modifier,
                fontStyle = MaterialTheme.typography.titleLarge,
                color = Color.Red
            )

            Text(
                text = "Authorized Access Only",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontFamily = ralewayfamilt
            )


            button(text = "Ok") {
                showaccessEror.value = false


            }
            Spacer(modifier = Modifier.height(20.dp))


        }


    }
    }



}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showSeeAttendanceDialogue(showSeeAttendance: MutableState<Boolean>,
                              dashboardViewmodel: dashboardViewmodal) {
val data = remember {
    mutableStateOf<List<studentAttendanceData>>(emptyList())
}

val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))


    LaunchedEffect(Unit) {

        dashboardViewmodel.fetchAttendance(
            date = date
        ){
            data.value = it
        }

}



    Dialog(onDismissRequest = { /*TODO*/ }) {


        Surface(modifier = Modifier.fillMaxWidth(),
            color = Color.White) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    textout(
                        title = "Attendance", modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                        modifier = Modifier.clickable {
                            showSeeAttendance.value = false

                        })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp, end = 20.dp,
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    textout(
                        title = "Classes", modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium
                    )

                    textout(
                        title = "Status/Present", modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium
                    )

                }
                if (data.value.isNotEmpty()){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .height(400.dp)
                    ) {

                        items(data.value) { it ->
                            attendanceMaker(it, date)
                        }


                    }
            }
                else{
                    CircularProgressIndicator()
                }




            }

        }


    }

}
@Composable
fun attendanceMaker(studentAttendanceData: studentAttendanceData, date: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, top = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {

        Text(text = studentAttendanceData.className,
            fontFamily = ralewayfamilt,
            color = Color.Black,
            style = MaterialTheme.typography.titleMedium
            )





if (studentAttendanceData.status) {
    Text(
        text = studentAttendanceData.value,
        fontFamily = ralewayfamilt,
        color = Color.Black,
        style = MaterialTheme.typography.titleMedium
    )
}
        else{
    Text(
        text = "Not Submitted",
        fontFamily = ralewayfamilt,
        color = Color.Red,
        style = MaterialTheme.typography.titleMedium
    )
        }

    }


}

@Composable
fun ShowMakeAdmindialogue(
    dashboardViewmodel: dashboardViewmodal,
    showMakeAdmin: MutableState<Boolean>
) {

    val data = remember {
        mutableStateOf<List<adminDetails?>>(emptyList())
    }
    LaunchedEffect(Unit) {
       dashboardViewmodel.fetchAdmins {

            data.value = it

        }
    }


    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(modifier = Modifier.fillMaxWidth(),
            color = Color.White) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                    horizontalArrangement = Arrangement.End) {
                    
                    textout(title = "Admin Control Unit", modifier = Modifier,
                        fontStyle =MaterialTheme.typography.titleMedium )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Filled.Close, contentDescription ="" ,
                        modifier =Modifier.clickable {
                            showMakeAdmin.value = false
                        })
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp, end = 20.dp,
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    textout(title = "Volunteers", modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium)

                    textout(title = "Status", modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium)

                }
if (data.value.isNotEmpty()) {
    adminMaker(data)
}
                else{
                    CircularProgressIndicator()
                }

            }


        }


    }


}
@Composable
fun adminMaker(data: MutableState<List<adminDetails?>>) {

    LazyColumn(modifier = Modifier
        .height(
            350.dp
        )
        .padding(bottom = 10.dp)

    ) {
    items(data.value){

val status = remember {
    it?.let { it1 -> mutableStateOf(it1.admin) }
}
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {



            if (it != null) {
                accountpic(profilePic = it.image)
                textout(title = it.name,
                    modifier = Modifier, fontStyle =MaterialTheme.typography.bodyLarge )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(checked = status?.value=="True",
                    onCheckedChange ={bollean->

                       if ( status?.value == "True"){
                           status.value = "False"
                       }
                        else{
                           status?.value = "True"
                        }


                    status?.value?.let { it1 -> AppRepository.addAdmin(email =it.email , value = it1) }

                } )
            }

        }



    }

}

}


@SuppressLint("SuspiciousIndentation")
@Composable
fun AddStudents(
    showAddStudentDialogue: MutableState<Boolean>,
    dashboardViewmodel: dashboardViewmodal,
) {

    val phoneno = remember {
        mutableStateOf("")
    }

    val name = remember {
        mutableStateOf("")
    }
    val dropdownmenu = remember {
        mutableStateOf(false)
    }

    val classselected = remember {
        mutableStateOf("")
    }

    val fatherName = remember {
        mutableStateOf("")
    }

    val state = remember {
        mutableStateOf(0)
    }

    val error = remember {
        mutableStateOf("")
    }


    val list = dashboardViewmodel.classList.collectAsState()

    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(color = Color.White, modifier = Modifier.clip(RoundedCornerShape(10.dp))) {

            if (state.value == 0)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                            modifier = Modifier.clickable {
                                showAddStudentDialogue.value = false
                            })
                    }
                    textout(
                        title = "Add Student",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleLarge
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        textout(
                            title = "Class-",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.bodyLarge
                        )

                        textout(
                            title = classselected.value,
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleMedium
                        )
                        if (!list.value.isNullOrEmpty())
                            Column {


                                Icon(imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "",
                                    modifier = Modifier.clickable {
                                        dropdownmenu.value = !dropdownmenu.value
                                    })
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

                    inputValue(name = name, placeholder = "Name", keyboard = "")

                    inputValue(name = fatherName, placeholder = "Father's Name", keyboard = "")

                    inputValue(name = phoneno, placeholder = "Phone No.", keyboard = "number")

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

        if (state.value == 1) {
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
        if (state.value == 2) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    textout(
                        title = error.value,
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.bodyLarge
                    )
                    button(text = "Ok") {
                        showAddStudentDialogue.value = false
                    }
                }
            }


        }


        if (state.value == 3) {
            animation(newUser = showAddStudentDialogue, Boolean = true, state)
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


        Surface(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)), color = Color.White) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(14.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "",
                        modifier = Modifier.clickable {
                            showAddUserDialogue.value = false
                        })
                }

                textout(
                    title = "Add User",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleLarge
                )


                textout(
                    title = "Due to security reasons, users should only" +
                            " be added via the website." +
                            "\n" +
                            "\nWe apologize for any inconvenience this may" +
                            " cause.",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.bodyLarge
                )



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
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

    }

}


