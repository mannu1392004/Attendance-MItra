package com.example.savera.Screens.MainScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.savera.Components.ChatScreenTopBar
import com.example.savera.Components.TopAppBar
import com.example.savera.Model.UserInformation
import com.example.savera.R
import com.example.savera.Screens.ChatsScreen.chatScreen
import com.example.savera.Screens.account.attendanceScreenNav
import com.example.savera.Screens.attendanceScreen.AttendanceScreen
import com.example.savera.Screens.dashboard.dashboard
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.events.eventscreen
import com.example.savera.Screens.homeScreen.homeScreen
import com.example.savera.ui.theme.ralewayfamilt


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation", "UnrememberedMutableInteractionSource")
@Composable
fun MainScreen(navController: NavHostController) {

val notShowTop = remember {
    mutableStateOf(false)
}

    val dashboardViewmodal :dashboardViewmodal  = viewModel()



    val userInfo = remember {
        mutableStateOf<UserInformation?>(null)
    }
    LaunchedEffect(Unit) {
        dashboardViewmodal.fetchUserDetails(
            info = {

                userInfo.value = it

            },
            failure = {

            }
        )
    }






    val activity = LocalContext.current as? Activity

    val selectindex = remember {
        mutableIntStateOf(0)
    }
    val showmessagetopbar = remember {
        mutableStateOf(false)
    }


    BackHandler {
        activity?.finish()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier, topBar = {
            if (showmessagetopbar.value)
                ChatScreenTopBar(showmessagetopbar)
            else
                if (!notShowTop.value)
                TopAppBar(title = "", showmessagetopbar)
        },


        bottomBar = {
            if (!showmessagetopbar.value)

                NavigationBar(

                    modifier = Modifier
                        .height(70.dp)

                        .background(color = Color.Transparent)


                ) {


                    Surface(
                        modifier = Modifier.fillMaxSize(), color = Color(0xffF9A825)
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            Surface(
                                modifier = Modifier.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                ) {

                                    selectindex.value = 0


                                },
                                shape = CircleShape,
                                color = Color(0xffF9A825),
                                border = BorderStroke(
                                    color =
                                    if (selectindex.value == 0
                                    )
                                        Color.White
                                    else
                                        Color(
                                            0xffF9A825
                                        ), width = 2.dp
                                )
                            ) {


                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(5.dp),
                                ) {
                                    AnimatedVisibility(
                                        visible = selectindex.value == 0
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(7.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {


                                            Image(
                                                painter = painterResource(id = R.drawable.home),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .padding(4.dp),
                                            )

                                            Text(
                                                text = "Home", color = Color.White,
                                                fontFamily = ralewayfamilt,
                                                 style =  MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }


                                    AnimatedVisibility(visible = selectindex.value != 0) {


                                        Image(
                                            painter = painterResource(id = R.drawable.home),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .padding(4.dp),
                                        )

                                    }
                                }


                            }


                            Surface(
                                modifier = Modifier.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                ) {

                                    selectindex.value = 1

                                },
                                shape = CircleShape,
                                color = Color(0xffF9A825),
                                border = BorderStroke(
                                    color =
                                    if (selectindex.value == 1)
                                        Color.White
                                    else
                                        Color(
                                            0xffF9A825
                                        ), width = 2.dp
                                )
                            ) {


                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    AnimatedVisibility(visible = selectindex.value == 1) {
                                        Row(
                                            modifier = Modifier.padding(7.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {


                                            Image(
                                                painter = painterResource(id = R.drawable.people),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .padding(4.dp),
                                            )

                                            Text(
                                                text = "Attendance", color = Color.White,
                                                fontFamily = ralewayfamilt,
                                                 style =  MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }


                                    AnimatedVisibility(visible = selectindex.value != 1) {


                                        Image(
                                            painter = painterResource(id = R.drawable.people),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .padding(4.dp),
                                        )

                                    }
                                }


                            }



                            Surface(
                                modifier = Modifier.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                ) {

                                    selectindex.value = 2

                                },
                                shape = CircleShape,
                                color = Color(0xffF9A825),
                                border = BorderStroke(
                                    color =
                                    if (selectindex.value == 2)
                                        Color.White
                                    else
                                        Color(
                                            0xffF9A825
                                        ), width = 2.dp
                                )
                            ) {


                                Row(
                                    verticalAlignment = Alignment.CenterVertically,

                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    AnimatedVisibility(visible = selectindex.value == 2) {
                                        Row(
                                            modifier = Modifier.padding(7.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {


                                            Image(
                                                painter = painterResource(id = R.drawable.appicon),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .padding(4.dp),
                                            )

                                            Text(
                                                text = "Dashboard", color = Color.White,

                                                fontFamily = ralewayfamilt,
                                                style =  MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }


                                    AnimatedVisibility(visible = selectindex.value != 2) {


                                        Image(
                                            painter = painterResource(id = R.drawable.appicon),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .padding(4.dp),
                                        )

                                    }
                                }


                            }


                            Surface(
                                modifier = Modifier.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                ) {

                                    selectindex.value = 3


                                },
                                shape = CircleShape,
                                color = Color(0xffF9A825),
                                border = BorderStroke(
                                    color =
                                    if (selectindex.value == 3)
                                        Color.White
                                    else
                                        Color(
                                            0xffF9A825
                                        ), width = 2.dp
                                )
                            ) {


                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    AnimatedVisibility(visible = selectindex.value == 3) {
                                        Row(
                                            modifier = Modifier.padding(7.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {


                                            Image(
                                                painter = painterResource(id = R.drawable.calender),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .padding(4.dp),
                                            )

                                            Text(
                                                text = "Events", color = Color.White,

                                                fontFamily = ralewayfamilt,
                                                 style =  MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }


                                    AnimatedVisibility(visible = selectindex.value != 3) {


                                        Image(
                                            painter = painterResource(id = R.drawable.calender),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .padding(4.dp),
                                        )

                                    }
                                }


                            }

                            Surface(
                                modifier = Modifier.clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,

                                    onClick = {
                                        selectindex.value = 4


                                    }

                                ),
                                shape = CircleShape,
                                color = Color(0xffF9A825),
                                border = BorderStroke(
                                    color =
                                    if (selectindex.value == 4)
                                        Color.White
                                    else
                                        Color(
                                            0xffF9A825
                                        ), width = 2.dp
                                )
                            ) {


                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    AnimatedVisibility(
                                        visible = selectindex.value == 4
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(7.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {


                                            Image(
                                                painter = painterResource(id = R.drawable.account),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(30.dp)
                                                    .padding(4.dp),
                                            )

                                            Text(
                                                text = "Account", color = Color.White,

                                                fontFamily = ralewayfamilt
                                           , style =  MaterialTheme.typography.titleMedium

                                            )
                                        }
                                    }


                                    AnimatedVisibility(visible = selectindex.value != 4) {
                                        Image(
                                            painter = painterResource(id = R.drawable.account),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .padding(4.dp),
                                        )

                                    }
                                }


                            }


                        }


                    }


                }


        }

    ) {
        Surface(modifier = Modifier.padding(it)) {


            if (!showmessagetopbar.value) {
                if (selectindex.value == 0) {
    notShowTop.value  = false
                    homeScreen(selectindex = selectindex)
                }
                if (selectindex.value == 1) {

                        notShowTop.value  = false


                    AttendanceScreen(selectindex,
                    )

                }

                if (selectindex.value == 2) {



                    dashboard(selectindex = selectindex,notShowTop,userInfo)

                }
                if (selectindex.value == 3) {
                    eventscreen(selectindex = selectindex,userInfo)
                    notShowTop.value  = false

                }
                if (selectindex.value == 4) {
                    attendanceScreenNav(selectindex,navController)
                    notShowTop.value  = true
                }

            } else {
                chatScreen()
            }
        }
    }
}