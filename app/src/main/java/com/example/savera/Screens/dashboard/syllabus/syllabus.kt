package com.example.savera.Screens.dashboard.syllabus

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.savera.Model.syllabusshower
import com.example.savera.Screens.account.mainScreen.accountpic
import com.example.savera.Screens.dashboard.viewmodel.dashboardViewmodal
import com.example.savera.Screens.events.textoutput
import com.example.savera.Screens.homeScreen.textout
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewaybold
import com.example.savera.ui.theme.ralewayfamilt
import kotlinx.coroutines.delay

@Composable
fun syllabus(dashboardViewmodel: dashboardViewmodal) {
    val classList = remember {
        mutableStateOf<List<String>>(
            listOf("Class 1","Class 2","Class 3","Class 4","Class 5","Class 6","Class 7"
                ,"Class 8","Class 9","Class 10","Class 11","Class 12")
        )
    }
    
    val syllabuslist = remember {
        mutableStateOf<List<syllabusshower>?>(emptyList())
    }
LaunchedEffect(Unit) {
    dashboardViewmodel.fetchSyllabus { 
        syllabuslist.value= it
    }
    
}
    
    
    

val selectedclass = remember {
    mutableStateOf("Select the Class")
}



Box(modifier = Modifier
    .fillMaxSize()
    .background(Color.White)
    ,

){

Column(verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {


    topbarSyllabus(classList,selectedclass)

    Spacer(modifier = Modifier.height(15.dp))

    mainUi(syllabuslist)


}



}

}
@Composable
fun mainUi(syllabuslist: MutableState<List<syllabusshower>?>) {









    Column(modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {


        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color(0xffFB5607),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(modifier = Modifier.padding(start = 10.dp, top = 20.dp, bottom = 20.dp)) {

                textout(
                    title = "Subjects",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(1f))
                textout(
                    title = "Previous Covered",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(0.3f))
                textout(
                    title = "%",
                    modifier = Modifier,
                    fontStyle = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.weight(0.1f))
            }

        }

        if (syllabuslist.value?.isEmpty() == true){
            CircularProgressIndicator(color = Color(0xffF9A825))
        }
        else{
           listmaker(syllabuslist)
        }

        
        
    }
}

@Composable
fun listmaker(syllabuslist: MutableState<List<syllabusshower>?>) {
    LazyColumn {


        items(syllabuslist.value?: emptyList()){
            surfacemakerforsyllabus(it)
        }

    }


}
@Composable
fun surfacemakerforsyllabus(it: syllabusshower) {
Surface(modifier = Modifier
    .fillMaxWidth()
    .padding(top = 10.dp),
    color = Color(0xffF9A825),
    shape = RoundedCornerShape(10.dp),
) {
Row(modifier = Modifier.padding(
    start = 
    10.dp,
    top = 20.dp,
    bottom = 20.dp
    )) {
textout(title = it.syllabus, modifier = Modifier, fontStyle =MaterialTheme.typography.titleMedium,
    color = Color.White)
    val text = it.previous.toString()
    Spacer(modifier = Modifier.weight(1f))


    if (it.status){
        textout(title = "Completed", modifier = Modifier, fontStyle =MaterialTheme.typography.titleMedium,
            color = Color(0xFF177E17)
        )
    }
    else{
        textout(title =if (text.length<20) {text}
        else {
            text.substring(0,20)+"..."
        }

            , modifier = Modifier, fontStyle =MaterialTheme.typography.bodyMedium,
            color = Color.White)
    }


    Spacer(modifier = Modifier.weight(0.3f))
    textout(title = it.percentage.toString(), modifier = Modifier
        , fontStyle =MaterialTheme.typography.titleMedium,
        color = Color.White
    )
    Spacer(modifier = Modifier.weight(0.1f))


}

}


}

@Composable
fun classSelection(classList: MutableState<List<String>>, selectedclass: MutableState<String>) {
val dropdown = remember {
    mutableStateOf(false)
}


    Surface(modifier = Modifier,
        color = Color(0xffFB5607),
        shape = RoundedCornerShape(10.dp)
        ) {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp,
                bottom = 10.dp, end = 4.dp)) {
            textout(
                title = selectedclass.value, modifier = Modifier.padding(7.dp),
                fontStyle = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

           Column {


                Icon(
                    imageVector = Icons.Filled.ArrowDropDown, contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        dropdown.value  = true
                    }
                )


                DropdownMenu(
                    expanded = dropdown.value,
                    onDismissRequest = { dropdown.value = false },
                    modifier = Modifier.height(100.dp)
                ) {

                        Column {


                            classList.value.forEach {

                                DropdownMenuItem(text = {
                                    textout(
                                        title = it,
                                        modifier = Modifier,
                                        fontStyle = MaterialTheme.typography.bodyMedium,
                                        fontFamily = lightrale

                                    )
                                }, onClick = {
                                    selectedclass.value = it
                                    dropdown.value = false


                                })


                            }
                        }



                }
            }
        }
    }

}

@Composable
fun topbarSyllabus(classList: MutableState<List<String>>, selectedclass: MutableState<String>) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
        color = Color(0xffF9A825),
        shape = RoundedCornerShape( bottomStartPercent = 90, topStartPercent = 90)
    ) {
        val rotation = remember {
            androidx.compose.animation.core.Animatable(0f)
        }


        LaunchedEffect(Unit) {

            rotation.animateTo(
                targetValue = 360f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        durationMillis = 1000
                    )
                )
            )
        }

        val helo = remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            while (true) {
                delay(100)
                helo.value += "H"
                delay(500)
                helo.value += "E"
                delay(500)
                helo.value += "L"
                delay(500)
                helo.value += "L"
                delay(500)
                helo.value += "O,"
                delay(500)
                helo.value = ""
                delay(500)
            }

        }





     Row(modifier = Modifier
         .fillMaxWidth()
         .padding(start = 20.dp, end = 5.dp),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween
         ) {


             Column(modifier = Modifier) {
                 textout(
                     title = helo.value, modifier = Modifier,
                     fontStyle = MaterialTheme.typography.titleLarge,
                     fontFamily = ralewaybold,
                     color = Color.White
                 )

                 textout(
                     title = "Mannu", modifier = Modifier,
                     fontStyle = MaterialTheme.typography.titleSmall,
                     fontFamily = ralewayfamilt,
                     color = Color.White
                 )






             }
             Spacer(modifier = Modifier.width(10.dp))
             accountpic(
                 profilePic = "https://firebasestorage.googleapis.com/v0/b/savera-504a2.appspot.com/o/Profile%20Pictures%2Fmannu1392004%40gmail.com%2F1000143376?alt=media&token=29d92201-21dd-44ca-b26f-cfcb908ee9fa",
                 modifier = Modifier
                     .size(60.dp)
                     .padding(2.dp)
                     .rotate(rotation.value)

                     .border(width = 1.dp, color = Color(0xfff707070), shape = CircleShape)
             )


         }







    }

}
