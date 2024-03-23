package com.example.savera.Screens.SplashScreen

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.savera.Navigation.Screens
import com.example.savera.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun SplashScreen(navController: NavHostController) {

    val animationchange = remember {
        mutableStateOf(true)
    }

val scale = remember {
    androidx.compose.animation.core.Animatable(1f)
}
    LaunchedEffect(Unit) {

        delay(1000)
        animationchange.value = !animationchange.value



scale.animateTo(
targetValue = 6f,
    animationSpec = tween(
        durationMillis =200
        ,easing = {
            OvershootInterpolator(3f)
                .getInterpolation(it)
        }
    )
)



        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()&& FirebaseAuth.getInstance().currentUser?.isEmailVerified == true)
            navController.navigate(Screens.NewuserCheck.name)

        else

        navController.navigate(Screens.LoginScreen.name)

    }






    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
       ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

    Surface(
        modifier = Modifier
            .padding(0.dp)
            .height(300.dp)
            .width(300.dp)
            .scale(scale.value)
            , color = Color(0xFFFFD60A)
        , shape = CircleShape
    ) {
        Column(modifier = Modifier.fillMaxSize()
        , verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

if (animationchange.value)
Image(painter = painterResource(id = R.drawable.savera_logo1_removebg_preview), contentDescription = "",
    modifier = Modifier.size(200.dp))

        }
    }



    }




}