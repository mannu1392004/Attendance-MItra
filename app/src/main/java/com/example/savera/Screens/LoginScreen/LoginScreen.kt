package com.example.savera.Screens.LoginScreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.savera.Navigation.Screens
import com.example.savera.R
import com.example.savera.ui.theme.lightrale
import com.example.savera.ui.theme.ralewayfamilt

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(navController: NavHostController,
                loginScreenViewmodel: LoginScreenViewmodel = viewModel()) {

    val activity1 = LocalContext.current as? Activity

    BackHandler {
          activity1?.finish()

    }



    val textgradient = Brush.linearGradient(
        listOf(
            Color(0xffFF5858),

            Color(0xffFFFF45)
        )
    )

    val loading = remember {
        mutableStateOf(false)
    }

    val showdialogue  = remember {
        mutableStateOf(false)

    }
    val errormessage = remember {
        loginScreenViewmodel.errormessage
    }


    val showpassword = remember {
        mutableStateOf(false)
    }

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
            animationSpec = tween(durationMillis = 1000)
        )

    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFD60A)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (animitable.value - 1) * 500.dp)
                .background(androidx.compose.ui.graphics.Color.White)
                .verticalScroll(rememberScrollState()),
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
                        top = 5.dp,
                        start = 50.dp,
                        end = 50.dp,

                        )
                    .fillMaxWidth(),

                ) {
                Text(
                    text = "Welcome Back!",
                    fontFamily = ralewayfamilt,
                    fontSize = 5.em
                )
                Text(
                    text = "Please sign in to continue..",
                    fontFamily = lightrale
                )

                Spacer(modifier = Modifier.height(40.dp))



                Text(
                    text = "Email",
                    fontFamily = ralewayfamilt,
                    fontSize = 3.em,
                    style = TextStyle(

                    )
                )


                OutlinedTextField(value = email.value, onValueChange = {
                    email.value = it

                },

                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp, brush = Brush.linearGradient(
                                listOf(
                                    Color(0xffFF5858),

                                    Color(0xffFFFF45)
                                )
                            ), shape = RoundedCornerShape(8.dp)
                        ),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = "Savera@test.com",
                            fontFamily = lightrale, modifier = Modifier.padding(bottom = 5.dp)
                        )
                    }, singleLine = true,
                    maxLines = 1, colors = TextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    )


                )


                Spacer(modifier = Modifier.height(30.dp))



                Text(
                    text = "Password", fontFamily = ralewayfamilt,
                    fontSize = 3.em,
                    style = TextStyle(
                    )

                )



                OutlinedTextField(value = password.value, onValueChange = {
                    password.value = it

                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp, brush = Brush.linearGradient(
                                listOf(
                                    Color(0xffFF5858),

                                    Color(0xffFFFF45)
                                )
                            ), shape = RoundedCornerShape(8.dp)
                        ),


                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(
                            text = "* * * * * * * *",
                            fontFamily = lightrale
                        )
                    }, trailingIcon = {
                        Image(painter = painterResource(
                            id = if (!showpassword.value) R.drawable.img_3
                            else R.drawable.img_4
                        ), contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    if (!password.value.isNullOrEmpty())
                                        showpassword.value = !showpassword.value

                                }

                        )

                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    maxLines = 1,

                    visualTransformation = if (!showpassword.value) PasswordVisualTransformation()
                    else VisualTransformation.None

                )



                Spacer(modifier = Modifier.height(30.dp))



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(




                        onClick = {
if (email.value.isNullOrEmpty()||password.value.length<=6){
    showdialogue.value= true
    errormessage.value = "Uh-oh! Either your email field is as empty as deep space, or your password is not playing by the rules. Remember, passwords should be at least 6 characters long."
}


else {
    val screen = Screens.LoadingScreen.name
    navController.navigate(route = "$screen/${email.value}/${password.value}")

}
                        },

                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xffFF5858),

                                        Color(0xffFFFF45),

                                        )

                                ), shape = RoundedCornerShape(10.dp)

                            )
                            .padding()
                            .clip(RoundedCornerShape(10.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = androidx.compose.ui.graphics.Color.Transparent
                        )

                    ) {


                        Text(
                            text = "SIGN IN",
                            color = androidx.compose.ui.graphics.Color.White
                        )
                    }

                }


            }

if (showdialogue.value)
alertbox(errormessage.value,showdialogue)
          

        }
    }
}
@Composable
fun alertbox(value: String, showdialogue: MutableState<Boolean>) {
    AlertDialog(onDismissRequest = {showdialogue.value =!showdialogue.value  }, confirmButton = { /*TODO*/ },
        title = { Text(text = "Error", color = Color.Black)},
        text = { Text(text = value, color = Color.Black)}, modifier = Modifier
    , containerColor = Color.White)
    
}
