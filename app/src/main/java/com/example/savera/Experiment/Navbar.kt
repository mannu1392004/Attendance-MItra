import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Preview
@Composable
fun navbar(){
val selected = remember {
    mutableStateOf(0)
}

    Scaffold(modifier = Modifier
    ,
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.White)
                .shadow(elevation = 1.dp),
                ) {
                Row(modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom) {


                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        color = Color(0xffF9A825)

                    ) {

                    }
                }
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, start = 19.dp, end = 20.dp)
                    , horizontalArrangement = Arrangement.SpaceBetween,
                    ) {


                    Surface(modifier = Modifier
                        .size(50.dp)
                        .align(
                            if (selected.value == 0) Alignment.Top
                            else Alignment.Bottom
                        ),
                        color = Color(0xffF9A825),
                        shape = CircleShape,

                        border = BorderStroke(color = if (selected.value == 0) Color.White
                            else Color(0xffF9A825)
                            , width = 3.dp
                      )
                    ) {
                        Image(imageVector = Icons.Filled.Home,
                            contentDescription = "",
                           contentScale = ContentScale.Inside,modifier = Modifier.clickable {
                                selected.value = 0
                            }
                            ) // Change the size here
                    }

                    Surface(modifier = Modifier
                        .size(50.dp)
                        .align(
                            if (selected.value == 1) Alignment.Top
                            else Alignment.Bottom
                        ),
                        color = Color(0xffF9A825),
                        shape = CircleShape,
                        border = BorderStroke(color = if (selected.value == 1) Color.White
                        else Color(0xffF9A825), width = 3.dp))
                     {
                        Image(imageVector = Icons.Filled.Home,
                            contentDescription = "",
                            contentScale = ContentScale.Inside,modifier = Modifier.clickable {
                                selected.value = 1
                            }
                            ) // Change the size here
                    }


                    Surface(modifier = Modifier
                        .size(50.dp)
                        .align(
                            if (selected.value == 2) Alignment.Top
                            else Alignment.Bottom
                        ),
                        color = Color(0xffF9A825),
                        shape = CircleShape,
                        border = BorderStroke(color =if (selected.value == 2) Color.White
                        else Color(0xffF9A825), width =3.dp
                       )
                    ) {
                        Image(imageVector = Icons.Filled.Home,
                            contentDescription = "",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.clickable {
                                selected.value = 2
                            }) // Change the size here
                    }


                    Surface(modifier = Modifier
                        .size(50.dp)
                        .align(
                            if (selected.value == 3) Alignment.Top
                            else Alignment.Bottom
                        ),
                        color = Color(0xffF9A825),
                        shape = CircleShape,
                        border = BorderStroke(color = if (selected.value == 3) Color.White
                        else Color(0xffF9A825), width = 3.dp)
                    ) {
                        Image(imageVector = Icons.Filled.Home,
                            contentDescription = "",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.clickable {
                                selected.value = 3
                            }) // Change the size here
                    }



                    Surface(modifier = Modifier
                        .size(50.dp)
                        .align(
                            if (selected.value == 4) Alignment.Top
                            else Alignment.Bottom
                        ),
                        color = Color(0xffF9A825),
                        shape = CircleShape,
                        border = BorderStroke(color =if (selected.value == 4) Color.White
                        else Color(0xffF9A825), width = 3.dp)
                    ) {
                        Image(imageVector = Icons.Filled.Home,
                            contentDescription = "",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.clickable {
                                selected.value = 4
                            }) // Change the size here
                    }

                }

            }






        }

    ) {
        Surface(modifier = Modifier.padding(it)) {


        }

    }


}