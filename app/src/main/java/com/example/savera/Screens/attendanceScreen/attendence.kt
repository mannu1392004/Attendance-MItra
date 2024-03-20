package com.example.savera.Screens.attendanceScreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen
import com.example.savera.R
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import com.example.savera.ui.theme.ralewayfamilt
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun AttendanceScreen(
    selectindex: MutableIntState,
    mainscreennav: NavHostController,
    AttendanceScreenViewmodel: AttendanceScreenViewmodel,
    selectedItemInAttendance: MutableState<String>,

    ) {
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember(currentDate) {
        currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }


    val dropdownmenu = remember {
        mutableStateOf(false)
    }
    val list = AttendanceScreenViewmodel.list.collectAsState()

    val Student_List = AttendanceScreenViewmodel.Student_List.collectAsState()



    BackHandler {
        selectindex.value = 2
        mainscreennav.navigate(route = mainScreen.Dashboard.name)
    }

    val currentDayOfWeek = remember {
        LocalDate.now().dayOfWeek.getDisplayName(
            TextStyle.FULL,
            Locale.getDefault()
        )
    }



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
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {


                        Text(
                            text = "$currentDayOfWeek ", modifier = Modifier,
                            fontFamily = ralewayfamilt, color = Color.White
                        )
                        Text(
                            text = " $formattedDate", modifier = Modifier,
                            fontFamily = ralewayfamilt,color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(0.2f))

                Surface(
                    color = Color(0xffF9A825),
                    shadowElevation = 7.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            Log.d("list size ", "${list.value.size}")
                        },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Select the Class", modifier = Modifier,
                            fontFamily = ralewayfamilt,color = Color.White
                        )

                        Row() {
                            Text(text = selectedItemInAttendance.value, fontFamily = ralewaybold,
                                color = Color.White)
                            if (!list.value.isNullOrEmpty())
                                Icon(imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "",
                                    modifier = Modifier

                                        .clickable {
                                        dropdownmenu.value = !dropdownmenu.value
                                    }, tint = Color.White
                                ,)
                        }


                        if (!list.value.isNullOrEmpty())
                            DropdownMenu(
                                expanded = dropdownmenu.value,
                                onDismissRequest = { dropdownmenu.value = false }
                              , modifier = Modifier.height(150.dp),

                            ) {
                                   list.value.forEach {
                                       DropdownMenuItem(text = {
                                           Text(
                                               text = it, fontFamily = lightrale
                                           )
                                       }, onClick = {
                                           selectedItemInAttendance.value = it
                                           AttendanceScreenViewmodel.fetchstudentslist(it)
                                           dropdownmenu.value = false


                                       }

                                       )


                                   }




                            }

                    }

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            if (Student_List.value.isNotEmpty()&&!selectedItemInAttendance.value.isNullOrEmpty())
                LazyColumn(modifier = Modifier
                    .background(
                        color = Color(0xffF9A825),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)

                ) {
                    item {
                        Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp,
                            end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {
Surface(modifier = Modifier,
    shape = RoundedCornerShape(topStart =  25.dp,
        bottomEnd = 25.dp,)
    , color = Color(0xffD9D9D9).copy(alpha = 0.4f)
) {
    Text(text = "Name", modifier = Modifier.padding(10.dp)
        , color = Color.White,
        fontFamily = ralewaybold)
}
                            Surface(modifier = Modifier,
                                shape = RoundedCornerShape(topStart =  25.dp,
                                    bottomEnd = 25.dp),
                                 color = Color(0xffD9D9D9).copy(alpha = 0.4f)) {
                                Text(text = "Mark(Present/Absent)",
                                    modifier = Modifier.padding(10.dp),
                                     color = Color.White,
                                    fontFamily = ralewaybold)
                            }


                        }

Spacer(modifier = Modifier.height(10.dp))
                    }


                    items(Student_List.value) {
                        var Selection = AttendanceScreenViewmodel.studentSelectionMap.getOrPut(it) {
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
                }


            if (!selectedItemInAttendance.value.isNullOrEmpty()&&Student_List.value.isNullOrEmpty()){

                CircularProgressIndicator()
            }


            if (selectedItemInAttendance.value.isNullOrEmpty()){
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                   Image(painter = painterResource(id = R.drawable.cart), contentDescription = "")
                }



            }

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
    currentDayOfWeek: String
) {
    Surface(modifier = Modifier.clip(RoundedCornerShape(topStart =  25.dp,
        bottomEnd = 25.dp))
        ,color = Color(0xffD9D9D9).copy(alpha = 0.4f)

    ) {


Row(modifier = Modifier
    .fillMaxWidth()
    .horizontalScroll(rememberScrollState())
    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
    , verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    Text(text = it, modifier = Modifier
        .padding(4.dp)
        .weight(1f),
        fontFamily = ralewaybold,
        color = Color.White)




    Surface(
        modifier = Modifier.weight(1f),
        color = Color.Transparent
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Present", fontFamily = ralewayfamilt, color = Color.White)
            RadioButton(selected =  (Selection.value==1)

                , onClick = { Selection.value=1
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




                }, colors = RadioButtonColors(selectedColor = Color(0xff008133),
                disabledSelectedColor = Color(0xff008133),
                unselectedColor = Color(0xff008133),
                disabledUnselectedColor = Color(0xff008133)
            ))
        }
    }




    Surface(
            modifier = Modifier.weight(1f)
    , color = Color.Transparent) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Absent", fontFamily = ralewayfamilt, color = Color.White)
            RadioButton(selected = Selection.value==2, onClick = {Selection.value=2




                AttendanceScreenViewmodel.attendancemarked(
                    collectionName = "Classes",
                    documentPath = selectedItem,
                    studentName = it,
                    date ="$formattedDate  $currentDayOfWeek",
                    error = {},
                    data = hashMapOf(
                        "Result" to "Absent",
                    )
                )




                                                                 },

                colors = RadioButtonColors(selectedColor = Color(0xffFF0000),
                disabledSelectedColor = Color(0xffFF0000),
                unselectedColor = Color(0xffFF0000),
                disabledUnselectedColor = Color(0xffFF0000)
            ))
        }
    }

}

}
    Spacer(modifier = Modifier.height(10.dp))
}

