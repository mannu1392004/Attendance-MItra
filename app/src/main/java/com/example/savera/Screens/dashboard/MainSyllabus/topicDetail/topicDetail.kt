package com.example.savera.Screens.dashboard.MainSyllabus.topicDetail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.savera.Model.UserInformation
import com.example.savera.Model.topicList
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.ralewaybold

@Composable
fun topicDetail(
    topicData: MutableState<List<topicList>>
    ,
    selected: MutableState<String>,
    selectedclass: MutableState<String>,
    dashboardViewmodel: dashboardViewmodal,
    chapterSelected: MutableState<String>,
    userInfo: MutableState<UserInformation?>,
                ) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffF9A825)
    ) {

        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    start = 5.dp, end = 5.dp
                )
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

                    userInfo.value?.let {
                        textout(
                            title = it.name, modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    }

                }

                userInfo.value?.let {
                    accountpic(
                        profilePic = it.profilePic
                    )
                }
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

            }

            Column(modifier = Modifier.fillMaxSize()) {
                Surface(modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, bottom = 25.dp, start = 10.dp, end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        textout(title =chapterSelected.value , modifier = Modifier,
                            fontStyle =MaterialTheme.typography.titleMedium )



                    }



                }
                Spacer(modifier = Modifier.height(15.dp))
                Surface(color = Color(0xffFB5607),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        textout(title = "Topics", modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleLarge,
                            color = Color.White)

                        LazyColumn {

                            items(topicData.value){
                                topicCrater(it,selected,selectedclass,dashboardViewmodel,chapterSelected)
                            }

                        }


                    }
                }

            }






        }


    }

}
@Composable
fun topicCrater(
    it: topicList,
    selected: MutableState<String>,
    selectedclass: MutableState<String>,
    dashboardViewmodel: dashboardViewmodal,
    chapterSelected: MutableState<String>
) {
    val state = remember {
        mutableStateOf(it.status)
    }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            , color = Color.White,
            shape = RoundedCornerShape(10.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)) {
textout(title = it.name, modifier = Modifier, fontStyle =MaterialTheme.typography.titleMedium )

         Checkbox(checked = state.value, onCheckedChange = {
             x->
             state.value  = !state.value
             dashboardViewmodel.update(
                 classes = selectedclass.value,
                 subject = selected.value,
                 chapter = chapterSelected.value,
                 topic = it.name,
                 data = hashMapOf(
                     "done" to state.value
                 )
             )




         })

            }

        }

}
