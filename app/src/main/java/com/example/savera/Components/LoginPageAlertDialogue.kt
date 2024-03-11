package com.example.savera.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.savera.ui.theme.ralewayfamilt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun alertdialogue(detail:String,show:MutableState<Boolean>){


    Surface(modifier = Modifier.fillMaxSize()) {
        Column {



}
        if (show.value)
AlertDialog(onDismissRequest = {
    show.value = !show.value
}
) {
 Surface (modifier = Modifier,
     color = Color.White,
     shadowElevation = 10.dp,
     shape = RoundedCornerShape(20.dp)
 ){
Column(verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(10.dp)) {
    Text(text = "Error!", fontFamily = ralewayfamilt,
        fontSize = 6.em,color=Color.Black)
    Spacer(modifier = Modifier.height(10.dp))
    Text(text =detail,
        color = Color(0xFFFF4A4A)
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row(modifier = Modifier.fillMaxWidth().padding(4.dp),
        horizontalArrangement = Arrangement.End,
        ) {

        Button(onClick = { show.value=!show.value }
        , colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
            modifier = Modifier.background(brush = Brush.linearGradient(
                listOf(Color(0xffFF5858),

                    Color(0xffFFFF45))
            ),
                shape = RoundedCornerShape(10.dp)
                )
,
        ) {

            Text(text = "OK", color = Color.White)
        }

    }




}


 }

}


    }


}