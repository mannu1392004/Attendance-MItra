package com.example.savera.Screens.dashboard.syllabusDetail

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.savera.Model.ChapterList
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.dashboard.DashboardScreen
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.ralewaybold
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun syllabusDetail(
    selected: MutableState<String>,
    dashboardViewmodel: dashboardViewmodal,
    Syllabus: MutableState<List<ChapterList>>,
    chapterSelected: MutableState<String>,
    navigation: NavHostController,
) {
    val offset = remember {
        Animatable(0f)
    }
    if (Syllabus.value.isNotEmpty())
    LaunchedEffect(Unit) {
        delay(100)
        offset.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 400)
        )
    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffF9A825)
    ) {

        if (Syllabus.value.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        start = 5.dp, end = 5.dp
                    )
                    .offset(y = (offset.value - 1) * 500.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        textout(
                            title = "Hello,", modifier = Modifier,
                            fontStyle = MaterialTheme.typography.displaySmall,
                            color = Color.White
                        )

                        textout(
                            title = "Mannu", modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )

                    }

                    accountpic(profilePic = "https://firebasestorage.googleapis.com/v0/b/savera-504a2.appspot.com/o/Profile%20Pictures%2Fmannu1392004%40gmail.com%2F1000143376?alt=media&token=29d92201-21dd-44ca-b26f-cfcb908ee9fa")
                }

                Spacer(modifier = Modifier.height(20.dp))


                Row(modifier = Modifier.fillMaxWidth()) {
                    textout(
                        title = "Subject: ",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium,
                        fontFamily = ralewaybold
                    )
                    textout(
                        title = selected.value,
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium,
                        fontFamily = ralewaybold
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    textout(
                        title = "Completion: ",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium,
                        fontFamily = ralewaybold
                    )
                    textout(
                        title = "10%",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleMedium,
                        fontFamily = ralewaybold
                    )
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {

                    items(Syllabus.value) {

                        chaptermaker(it,chapterSelected,navigation)
                    }

                }


            }
        }
        else {
            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){

                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
    }


}

@Composable
fun chaptermaker(
    it: ChapterList,
    chapterSelected: MutableState<String>,
    navigation: NavHostController
) {
    val show = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Surface(
            modifier = Modifier
                .clickable {
                    show.value = true
                }
                .fillMaxWidth()
                .padding(top = 8.dp),
            color = Color.White,
            shape = RoundedCornerShape(10.dp)
        ) {

            Row(
                modifier = Modifier.padding(
                    start = 14.dp, end = 14.dp,
                    top = 8.dp, bottom = 8.dp
                )

                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                textout(
                    title = "Ch ${it.no}",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleMedium,
                    fontFamily = ralewaybold
                )

                textout(
                    title = it.name,
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleMedium,
                    fontFamily = ralewaybold
                )


                RadioButton(
                    selected = it.status, onClick = { /*TODO*/ },
                    colors = RadioButtonColors(
                        selectedColor = Color(0xff159900),
                        disabledUnselectedColor = Color(0xff159900),
                        disabledSelectedColor = Color(0xff159900),
                        unselectedColor = Color(0xff159900)
                    )
                )

            }

        }
        if (show.value)
        Surface(color = Color(0xffFB5607),
            shape = RoundedCornerShape(bottomStartPercent = 35, bottomEndPercent = 35)
            , modifier = Modifier.clickable(onClick = {
                                                      chapterSelected.value = it.name
                navigation.navigate(DashboardScreen.TopicDetails.name)

            }
            , indication = null,
                interactionSource =
                remember {
                    MutableInteractionSource()
                }
            ) ) {
            Row(modifier = Modifier.padding(start = 23.dp, end = 23.dp),
                verticalAlignment = Alignment.CenterVertically) {


                textout(
                    title = "View details",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )

                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription ="",
                    tint = Color.White)

            }
        }


    }
}
