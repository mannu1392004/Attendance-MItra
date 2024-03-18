package com.example.savera.Screens.attendanceScreen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.savera.Navigation.mainScreenNavigation.mainScreen
import com.example.savera.ui.theme.ralewayfamilt
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
 fun AttendanceScreen(
    selectindex: MutableIntState,
    mainscreennav: NavHostController,
    AttendanceScreenViewmodel: AttendanceScreenViewmodel,

    ) {

   val dropdownmenu = remember {
       mutableStateOf(false)
   }
    val list = AttendanceScreenViewmodel.list.collectAsState()



    var selectedItem by remember { mutableStateOf("") }

    val options = listOf("Option 1", "Option 2", "Option 3")



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
    val currentDate = remember { LocalDate.now() }
    val formattedDate = remember(currentDate) {
        currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }


    Surface(modifier = Modifier.fillMaxSize()
        ,
        color = MaterialTheme.colorScheme.surfaceVariant) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)) {


            Row(modifier = Modifier.fillMaxWidth()) {


                Surface(color = MaterialTheme.colorScheme.surfaceVariant,
                    shadowElevation = 7.dp,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {


                        Text(
                            text = "$currentDayOfWeek ", modifier = Modifier,
                            fontFamily = ralewayfamilt,
                        )
                        Text(
                            text = " $formattedDate", modifier = Modifier,
                            fontFamily = ralewayfamilt,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(0.2f))

                Surface(color = MaterialTheme.colorScheme.surfaceVariant,
                    shadowElevation = 7.dp,
                            modifier = Modifier.weight(1f)
                                .clickable {
                                           Log.d("list size ","${list.value.size}")
                                },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Select the Class", modifier = Modifier,
                            fontFamily = ralewayfamilt,
                        )

                        Row (){
                            Text(text = selectedItem)
                            if (!list.value.isNullOrEmpty())
                            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "",
                                modifier = Modifier.clickable {
                                    dropdownmenu.value=!dropdownmenu.value
                                })
                        }


                        if (!list.value.isNullOrEmpty())
                        DropdownMenu(
                            expanded = dropdownmenu.value,
                            onDismissRequest = { dropdownmenu.value = false }
                        ) {
                       list.value.forEach{
                           DropdownMenuItem(text = { Text(text = it) }, onClick = {
                               selectedItem = it
                               dropdownmenu.value=false
                           })

                       }

                            }
                    }

                }

            }
        }

    }




}

