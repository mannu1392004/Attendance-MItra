package com.example.savera.Screens.attendanceScreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen
import com.example.savera.R
import com.example.savera.Screens.homeScreen.animation
import com.example.savera.Screens.homeScreen.button
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import com.example.savera.ui.theme.ralewayfamilt
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun AttendanceScreen(
    selectindex:MutableState<Int>,
    AttendanceScreenViewmodel: AttendanceScreenViewmodel = viewModel(),

    ) {

    var selectedItemInAttendance = remember { mutableStateOf("") }

    val studentSelectionMap = mutableStateMapOf<String, MutableState<Int>>()

    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember(currentDate) {
        currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }


    val dropdownmenu = remember {
        mutableStateOf(false)
    }
    val list = AttendanceScreenViewmodel.list.collectAsState()

    

    val studentSubmit = remember {
        mutableStateOf(false)
    }
    
    val AttendanceTaken = remember {
        mutableStateOf(false)
    }
    

    BackHandler {
        selectindex.value = 2
    }

    val currentDayOfWeek = remember {
        LocalDate.now().dayOfWeek.getDisplayName(
            TextStyle.FULL,
            Locale.getDefault()
        )
    }

    val studentList = AttendanceScreenViewmodel.Student_List.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
       
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
        ) {


            Row(modifier = Modifier.fillMaxWidth()) {


                Surface(
                    color = Color(0xffF9A825),
                    shadowElevation = 7.dp,

                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {


                        Text(
                            text = "$currentDayOfWeek ", modifier = Modifier,
                            fontFamily = ralewayfamilt, color = Color.White
                        )
                        Text(
                            text = " $formattedDate", modifier = Modifier,
                            fontFamily = ralewayfamilt, color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(0.2f))

                Surface(
                    color = Color(0xffF9A825),
                    shadowElevation = 7.dp,
                    modifier = Modifier

                        .clickable {

                        },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Select the Class", modifier = Modifier,
                            fontFamily = ralewayfamilt, color = Color.White
                        )

                        Row() {
                            Text(
                                text = selectedItemInAttendance.value, fontFamily = ralewaybold,
                                color = Color.White
                            )

                            if (!list.value.isNullOrEmpty())
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "",
                                    modifier = Modifier

                                        .clickable {
                                            dropdownmenu.value = !dropdownmenu.value
                                        },
                                    tint = Color.White,
                                )
                        }
                        if (!list.value.isNullOrEmpty())
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
                                    },

                                        onClick = {

                                        selectedItemInAttendance.value = it
                                            AttendanceScreenViewmodel.fetchstudentslist(it)
                                            dropdownmenu.value = false
                                        AttendanceScreenViewmodel.checkAttendance(date =formattedDate,
                                            nottaken = {
                                                       AttendanceTaken.value = false},
                                            taken = {
                                               AttendanceTaken.value = true     
                                            },
                                            classname = it)

                                    }

                                    )


                                }


                            }

                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            if (studentList.value.isNotEmpty() && !selectedItemInAttendance.value.isNullOrEmpty())
                if (!AttendanceTaken.value)
                LazyColumn(
                    modifier = Modifier
                        .background(
                            color = Color(0xffF9A825),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(10.dp)

                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp, top = 10.dp,
                                    end = 10.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                modifier = Modifier,
                                shape = RoundedCornerShape(
                                    topStart = 25.dp,
                                    bottomEnd = 25.dp,
                                ), color = Color(0xffD9D9D9).copy(alpha = 0.4f)
                            ) {
                                textout(
                                    title = "Name", modifier = Modifier.padding(10.dp),
                                    fontStyle = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                            Surface(
                                modifier = Modifier,
                                shape = RoundedCornerShape(
                                    topStart = 25.dp,
                                    bottomEnd = 25.dp
                                ),
                                color = Color(0xffD9D9D9).copy(alpha = 0.4f)
                            ) {
                                textout(
                                    title = "Mark(Present/Absent)",
                                    modifier = Modifier.padding(10.dp),
                                    fontStyle = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }


                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
item {
                    LaunchedEffect(studentList.value) {
                        studentSelectionMap.keys.forEach { key ->
                            studentSelectionMap.remove(key)
                        }

                    }
                    }
    items(studentList.value) {


        val Selection = studentSelectionMap.getOrPut(it) {
            mutableStateOf(0)
        }


        studentname(
            it,
            Selection,
            AttendanceScreenViewmodel,
            formattedDate,
            selectedItemInAttendance.value,
            currentDayOfWeek
        )
    }


                item {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {


                        button(text = "Submit"){
                            studentSubmit.value = true
                            
                        }
                       
                        
                        
                    }

                }


                }

            
            
            if (studentSubmit.value){
                ShowDialogue(
                    studentSubmit,
                    studentSelectionMap,
                    AttendanceScreenViewmodel,
                    selectedItemInAttendance
                    )
            }

            if (!selectedItemInAttendance.value.isNullOrEmpty() && studentList.value.isNullOrEmpty()&&!AttendanceTaken.value) {
                CircularProgressIndicator()
            }
            if (AttendanceTaken.value){
                attendanceTaken()
            }
            
            
            
            
            if (selectedItemInAttendance.value.isNullOrEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.cart), contentDescription = "")
                }


            }

        }
    }


}

@SuppressLint("UnrememberedMutableState")
@Composable
fun attendanceTaken() {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val x = remember {
            androidx.compose.animation.core.Animatable(0.5f)
        }
       LaunchedEffect(Unit) {
           x.animateTo(
               targetValue = 1f,
               animationSpec = infiniteRepeatable(
                   animation = tween(durationMillis = 1000),
                   repeatMode = RepeatMode.Reverse
               )
           )
       }

        Image(painter = painterResource(id = R.drawable.checklist), contentDescription ="",
            modifier = Modifier.scale(x.value))


        textout(title = "Attendance Taken", modifier = Modifier, fontStyle = MaterialTheme.typography.titleMedium)



    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShowDialogue(


    studentSubmit: MutableState<Boolean>,
    studentSelectionMap: SnapshotStateMap<String, MutableState<Int>>,
    AttendanceScreenViewmodel: AttendanceScreenViewmodel,
    selectedItemInAttendance: MutableState<String>,

    ) {
    Dialog(onDismissRequest = { /*TODO*/ }) {

        val Studentleft = remember {
        mutableStateOf(false)
    }
        var error = remember {
            mutableStateOf("")
        }

        val state = remember {
            mutableStateOf(0)
        }




        val present = remember {
            mutableStateOf(0)
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {

            LaunchedEffect(Unit) {
                for (x in studentSelectionMap.values) {
                    if (x.value == 1) {
                        present.value++
                    }
                    if (x.value == 0) {
                        Studentleft.value = true
                    }
                }
            }



            if (state.value == 0) {
                if (Studentleft.value) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        textout(
                            title =
                            "Looks like we're missing a few brain cells in the counting department! Should we send out a search party for the lost students?",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.bodyMedium
                        )

                        button(text = "Recount") {
                            studentSubmit.value = false
                        }

                    }

                } else {
                    Column(
                        modifier = Modifier.padding(bottom = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        textout(
                            title = "Submit",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )

                        textout(
                            title = "Total Student Present- ${present.value}", modifier = Modifier,
                            fontStyle = MaterialTheme.typography.bodyLarge,

                            )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            button(text = "Yes") {
                                state.value = 1
                                val date =
                                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                AttendanceScreenViewmodel.addPresent(
                                    classname = selectedItemInAttendance.value,
                                    successful = {
                                        state.value = 3
                                        selectedItemInAttendance.value = ""
                                    },
                                    error = {
                                        state.value = 2
                                        error.value = it
                                    },
                                    hashMap = hashMapOf(
                                        date to present.value.toString()
                                    )
                                )


                            }

                            button(text = "No") {
                                studentSubmit.value = false
                            }
                        }


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

            if (state.value == 2){
                Surface {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        textout(title = error.value, modifier = Modifier, fontStyle =MaterialTheme.typography.bodyLarge )
                        button(text = "Ok") {
                            studentSubmit.value = false
                        }
                    }
                }


            }



            if (state.value==3){
                animation(newUser =studentSubmit , Boolean = false, state =state )
            }



        }

    }






@Composable
fun studentname(
    it: String,
    Selection: MutableState<Int>,
    AttendanceScreenViewmodel: AttendanceScreenViewmodel,
    formattedDate: String,
    selectedItem: String,
    currentDayOfWeek: String,
) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 25.dp,
                bottomEnd = 25.dp
            )
        ), color = Color(0xffD9D9D9).copy(alpha = 0.4f)

    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()

                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
           textout(title = it, modifier = Modifier.weight(1f),
               fontStyle =MaterialTheme.typography.bodyLarge,
               color = Color.White)



            Surface(
                modifier = Modifier.weight(1f),
                color = Color.Transparent
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.horizontalScroll(rememberScrollState())) {


                  textout(title = "Present", modifier = Modifier,
                      fontStyle =MaterialTheme.typography.bodyMedium,
                      color = Color.White)


                    RadioButton(
                        selected = (Selection.value == 1), onClick = {
                            Selection.value = 1
                            AttendanceScreenViewmodel.attendancemarked(
                                collectionName = "Classes",
                                documentPath = selectedItem,
                                studentName = it,
                                date = "$formattedDate  $currentDayOfWeek",
                                error = {},
                                data = hashMapOf(
                                    "Result" to "Present",
                                )
                            )


                        }, colors = RadioButtonColors(
                            selectedColor = Color(0xff008133),
                            disabledSelectedColor = Color(0xff008133),
                            unselectedColor = Color(0xff008133),
                            disabledUnselectedColor = Color(0xff008133)
                        )
                    , modifier = Modifier.weight(1f)
                    )
                }
            }




            Surface(
                modifier = Modifier.weight(1f), color = Color.Transparent
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                   ) {

                    textout(title = "Absent", modifier = Modifier, fontStyle =MaterialTheme.typography.bodyMedium,
                        color = Color.White)

                    RadioButton(
                        selected = Selection.value == 2, onClick = {
                            Selection.value = 2




                            AttendanceScreenViewmodel.attendancemarked(
                                collectionName = "Classes",
                                documentPath = selectedItem,
                                studentName = it,
                                date = "$formattedDate  $currentDayOfWeek",
                                error = {},
                                data = hashMapOf(
                                    "Result" to "Absent",
                                )
                            )


                        },

                        colors = RadioButtonColors(
                            selectedColor = Color(0xffFF0000),
                            disabledSelectedColor = Color(0xffFF0000),
                            unselectedColor = Color(0xffFF0000),
                            disabledUnselectedColor = Color(0xffFF0000)
                        )
                    )
                }
            }

        }

    }
    Spacer(modifier = Modifier.height(10.dp))
}

