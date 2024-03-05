package com.example.savera.Screens.LoginScreen

import android.graphics.LinearGradient
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.example.savera.R
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewayfamilt
import java.nio.file.WatchEvent

@Composable
fun LoginScreen(navController: NavHostController) {

    var email = remember {
        mutableStateOf("")
    }

    var password = remember {
        mutableStateOf("")
    }


    val animitable = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        animitable.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(durationMillis = 1500)
        )

    }



    Surface(modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFD60A)
    ) {


        Column(
            modifier = Modifier.fillMaxSize()
                .offset(y = (animitable.value-1)*500.dp)
                .background(androidx.compose.ui.graphics.Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,



        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_2), contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )

                Image(
                    painter = painterResource(id = R.drawable.savera_logo_removebg_preview),
                    contentDescription = "",
                    modifier = Modifier.absolutePadding(
                        left = 90.dp,
                        top = 10.dp
                    )
                )


            }
            Column(
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        start = 50.dp,
                        end = 80.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Welcome Back!",
                    fontFamily = ralewayfamilt,
                )
                Text(
                    text = "Please sign in to continue..",
                    fontFamily = lightrale
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(value = email.value, onValueChange = {
                    email.value = it
                })
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(value = password.value, onValueChange = {
                    password.value = it
                })
                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xffFF5858),


                                    Color(0xffFFFF45)

                                )

                            ), shape = RoundedCornerShape(10.dp)

                        )
                            .padding()
                            .clip(RoundedCornerShape(10.dp))
                    ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color.Transparent
                        )

                    ) {
Text(text = "SIGN iN")
                    }

                }



            }


        }
    }
}