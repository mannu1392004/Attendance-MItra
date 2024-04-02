package com.example.savera.Screens.dashboard.seeFeedbacks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.savera.Model.feedBackType
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.ralewayfamilt

@Composable
fun seeFeedback(dashboardViewmodel: dashboardViewmodal, notShowTop: MutableState<Boolean>) {

    notShowTop.value = true
    val data = remember {
        mutableStateOf<List<feedBackType>>(emptyList())
    }


    LaunchedEffect(Unit) {
        dashboardViewmodel.getFeedbacks {
            data.value = it
        }
    }



Surface(color = Color.White,
    modifier = Modifier.fillMaxSize()){

    Column {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xffF9A825)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(20.dp)
            ) {
                textout(
                    title = "Feedbacks",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }



        LazyColumn {

            if (data.value.isEmpty()) {
                item {
                    CircularProgressIndicator()
                }
            } else {
                items(data.value) {
                    feedbackMaker(it)


                }
            }

        }
    }

}


}

@Composable
fun feedbackMaker(feedBackType: feedBackType) {
    Card(modifier = Modifier.padding(10.dp), colors = CardColors(containerColor = Color(0xFFBDB9B9), contentColor = Color.Black, disabledContainerColor = Color(0xFFBDB9B9), disabledContentColor = Color.Black)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                textout(title = feedBackType.date, modifier = Modifier,
                    fontStyle =MaterialTheme.typography.titleMedium )
                
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                Spacer(modifier = Modifier.width(70.dp))

               Text(text = feedBackType.data,
                   fontFamily = ralewayfamilt,
                   style = MaterialTheme.typography.bodyMedium
                   ,
                   textAlign = TextAlign.Center)




            }
            textout(title = feedBackType.email, modifier = Modifier
                , fontStyle = MaterialTheme.typography.titleMedium)
        }
    }
    
    
}

