package com.example.savera.Screens.HomeScreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.savera.Components.TopAppBar
import com.example.savera.Components.mainContent
import com.example.savera.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Preview
@Composable
fun HomeScreen() {
    val activity = LocalContext.current as? Activity
    val selectindex = remember {
        mutableIntStateOf(0)
    }

    BackHandler {
        activity?.finish()
    }

    Scaffold(topBar = {

        TopAppBar(title = "")
    },

        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(70.dp)
            ) {
Surface(modifier = Modifier.fillMaxSize()
    , color = Color(0xffF9A825)
    ) {
    Row(modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {


        Surface(
            modifier = Modifier.clickable {
                selectindex.value = 0
            },
            shape = CircleShape,
            color = Color(0xffF9A825),
            border = BorderStroke(
                color =
                if (selectindex.value == 0)
                    Color.White
                else
                    Color(
                        0xffF9A825
                    ), width = 0.5.dp
            )
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                AnimatedVisibility(visible = selectindex.value == 0) {
                    Row (modifier = Modifier.padding(7.dp)){


                        Image(
                            painter = painterResource(id = R.drawable.hut),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(4.dp),
                        )

                        Text(text = "Home", color = Color.White)
                    }
                }


                AnimatedVisibility(visible =selectindex.value!=0 ) {


                    Image(
                        painter = painterResource(id = R.drawable.hut),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(9.dp),
                    )

                }
            }


        }


        Surface(
            modifier = Modifier.clickable {
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
                    ), width = 0.5.dp
            )
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                AnimatedVisibility(visible = selectindex.value == 1) {
                    Row(modifier = Modifier.padding(7.dp)) {


                        Image(
                            painter = painterResource(id = R.drawable.hut),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(4.dp),
                        )

                        Text(text = "Home", color = Color.White)
                    }
                }


                AnimatedVisibility(visible =selectindex.value!=1) {


                    Image(
                        painter = painterResource(id = R.drawable.hut),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(9.dp),
                    )

                }
            }


        }



        Surface(
            modifier = Modifier.clickable {
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
                    ), width = 0.5.dp
            )
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                AnimatedVisibility(visible = selectindex.value == 2) {
                    Row(modifier = Modifier.padding(7.dp)) {


                        Image(
                            painter = painterResource(id = R.drawable.hut),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(4.dp),
                        )

                        Text(text = "Home", color = Color.White)
                    }
                }


                AnimatedVisibility(visible =selectindex.value!=2 ) {


                    Image(
                        painter = painterResource(id = R.drawable.hut),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(9.dp),
                    )

                }
            }


        }


        Surface(
            modifier = Modifier.clickable {
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
                    ), width = 0.5.dp
            )
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                AnimatedVisibility(visible = selectindex.value == 3) {
                    Row(modifier = Modifier.padding(7.dp)) {


                        Image(
                            painter = painterResource(id = R.drawable.hut),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(4.dp),
                        )

                        Text(text = "Home", color = Color.White)
                    }
                }


                AnimatedVisibility(visible =selectindex.value!=3 ) {


                    Image(
                        painter = painterResource(id = R.drawable.hut),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(9.dp),
                    )

                }
            }


        }

        Surface(
            modifier = Modifier.clickable {
                selectindex.value = 4
            },
            shape = CircleShape,
            color = Color(0xffF9A825),
            border = BorderStroke(
                color =
                if (selectindex.value == 4)
                    Color.White
                else
                    Color(
                        0xffF9A825
                    ), width = 0.5.dp
            )
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                AnimatedVisibility(visible = selectindex.value == 4) {
                    Row(modifier = Modifier.padding(7.dp)){


                        Image(
                            painter = painterResource(id = R.drawable.hut),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(4.dp),
                        )

                        Text(text = "Home", color = Color.White)
                    }
                }


                AnimatedVisibility(visible =selectindex.value!=4 ) {


                    Image(
                        painter = painterResource(id = R.drawable.hut),
                        contentDescription = "",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(9.dp),
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

            mainContent()


        }
    }}
