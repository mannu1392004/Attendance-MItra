package com.example.savera.Screens.homeScreen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.savera.Model.developer
import com.example.savera.Model.nameAndAtt
import com.example.savera.R
import com.example.savera.Repository.AppRepository
import com.example.savera.ui.theme.ralewayfamilt
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeui(youtubestate: MutableState<Float>, homeScreenViewModel: HomeScreenViewModel) {

    val openUrlLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    val list = remember {
        mutableStateOf<List<nameAndAtt>>(emptyList())
    }


    LaunchedEffect(Unit) {
        AppRepository.calculateTop3 {
            list.value = it
        }


    }


    val feedback = remember {
        mutableStateOf("")
    }
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(20.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            // documentry
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xffEEEEEE),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 10.dp
            ) {
                Column(modifier = Modifier) {
                    textout(
                        title = "Documentary",
                        modifier = Modifier.padding(10.dp),
                        fontStyle = MaterialTheme.typography.headlineSmall
                    )

                    LazyYouTubePlayer(youtubestate)

                }


            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xffEEEEEE),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 10.dp
            ) {
                Column(modifier = Modifier.padding(17.dp)) {
                    textout(
                        title = "About Savera",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    textout(
                        title = "Savera School is an evening school in DCRUST Murthal run by Student volunteers to educate the underprivileged section of the society.\n",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.bodyLarge
                    )
                    button(text = "More Info") {

                    }

                }


            }






            Spacer(modifier = Modifier.height(20.dp))


            // ranking
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xffEEEEEE),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 10.dp
            ) {

                LaunchedEffect(list.value) {
                    list.value = list.value.sortedByDescending { it.attendance }
                }

                Column(modifier = Modifier.padding(17.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        textout(
                            title = "Volunteers Ranking",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(7.dp))

                    if (list.value.isNotEmpty() && list.value.size >= 3) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable._ndpos),
                                    contentDescription = "",
                                    modifier = Modifier.size(90.dp)
                                )

                                textout(
                                    title =
                                    list.value[1].name,
                                    modifier = Modifier,
                                    fontStyle = MaterialTheme.typography.bodySmall
                                )

                            }


                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable._st),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(90.dp)
                                )
                                textout(
                                    title = list.value[0].name,
                                    modifier = Modifier,
                                    fontStyle = MaterialTheme.typography.bodySmall
                                )


                            }


                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable._rdpos),
                                    contentDescription = "",
                                    modifier = Modifier.size(90.dp)
                                )

                                textout(
                                    title = list.value[2].name,
                                    modifier = Modifier,
                                    fontStyle = MaterialTheme.typography.bodySmall
                                )

                            }

                        }
                    }

                }


            }


// developers
            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xffEEEEEE),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 10.dp
            ) {
                Column(modifier = Modifier.padding(17.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        textout(
                            title = "Our Developers",
                            modifier = Modifier,
                            fontStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(7.dp))


                    imageShower()

                }


            }


            // ideas
            val context = LocalContext.current
            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xffEEEEEE),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 10.dp
            ) {
                Column(modifier = Modifier.padding(17.dp)) {
                    textout(
                        title = "Any Ideas Or Suggestions",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedLabelColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        containerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),


                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                            .border(
                                width = 2.dp,
                                color = Color(0xffF57F17),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(15.dp)
                            ),

                        value = feedback.value,
                        onValueChange = { feedback.value = it },
                        placeholder = {
                            Text(
                                "Type your ideas here!",
                                fontFamily = ralewayfamilt,
                                color = Color(0xff33333380)
                            )
                        }

                    )
                    Spacer(modifier = Modifier.height(20.dp))


                    button(text = "Submit") {
                        val gmail = FirebaseAuth.getInstance().currentUser?.email

                        val localDate =
                            LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

                        val localTime =
                            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))

                        homeScreenViewModel.addFeedback(collectionName = "Feedback",
                            documentPath = gmail.toString(),
                            data = hashMapOf(
                                "$localDate $localTime" to feedback.value
                            ),
                            error = {
                                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                            },
                            Successfull = {
                                Toast.makeText(context, "Sent to Admin", Toast.LENGTH_SHORT).show()
                                feedback.value = ""
                            })


                    }

                }


            }

            Spacer(modifier = Modifier.height(20.dp))

            //   connext with us
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xffEEEEEE),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier.padding(17.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    textout(
                        title = "Connect With us",
                        modifier = Modifier,
                        fontStyle = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row {
                        Image(painter = painterResource(id = R.drawable.img),
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://www.facebook.com/saveradcrust")
                                    )
                                    openUrlLauncher.launch(intent)
                                })
                        Spacer(modifier = Modifier.width(20.dp))
                        Image(painter = painterResource(id = R.drawable.img_1),
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {

                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://www.instagram.com/saveraschool/")
                                    )
                                    openUrlLauncher.launch(intent)

                                })
                        Spacer(modifier = Modifier.width(20.dp))
                        Image(painter = painterResource(id = R.drawable.img_9),
                            contentDescription = "",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://www.youtube.com/@SaveraSchool")
                                    )
                                    openUrlLauncher.launch(intent)
                                })
                    }

                }


            }


        }
    }

    }
    @Composable
    fun imageShower() {
        val x = arrayListOf(
            developer(
                name = "Lakshay Dureja",
                post = "Android Developer",
                pic = R.drawable.laskshyasir
            ),
            developer(
                name = "Mannu",
                post = "Android Developer",
                pic = R.drawable.mannu
            ),
            developer(
                name = "Alok Pandit",
                post = "Ui Designer",
                pic = R.drawable.alok
            )

        )

        var i = 0
        val image = remember {
            mutableStateOf(x[0])
        }

        LaunchedEffect(Unit) {
            while (i < 3) {
                image.value = x[i]
                i++
                delay(3000)
                if (i == 3) {
                    i = 0
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = image.value.pic), contentDescription = "",
                modifier = Modifier.size(200.dp)
            )
            textout(
                title = image.value.name,
                modifier = Modifier,
                fontStyle = MaterialTheme.typography.titleMedium
            )
            textout(
                title = image.value.post,
                modifier = Modifier,
                fontStyle = MaterialTheme.typography.titleMedium
            )

        }


    }


    @Composable
    fun textout(
        title: String,
        modifier: Modifier,
        fontStyle: TextStyle,
        color: Color = Color.Black,
        fontFamily: androidx.compose.ui.text.font.FontFamily = ralewayfamilt,

        ) {
        Text(
            text = title,
            modifier = modifier,
            style = fontStyle,
            fontFamily = fontFamily,
            color = color,
            overflow = TextOverflow.Visible
        )


    }

    @Composable
    fun button(
        text: String,
        modifier: Modifier = Modifier.background(
            color = Color(0xffF57F17), shape = RoundedCornerShape(10.dp)
        ),
        press: () -> Unit,
    ) {
        Button(
            onClick = { press() }, colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.Transparent
            ), modifier = modifier

        ) {
            Text(
                text = text, fontFamily = ralewayfamilt,
                color = Color.White
            )
        }


    }


    @Composable
    fun LazyYouTubePlayer(youtubestate: MutableState<Float>) {
        var playerReady by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(1000)
            playerReady = true
        }

        if (playerReady) {
            YouTubePlayer(savedPosition = youtubestate)


        } else {
            // Display thumbnail preview or loading indicator
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()

                }


            }


        }
    }


    @Composable
    fun YouTubePlayer(
        savedPosition: MutableState<Float>,
    ) {

        val youtubeVideoId = rememberSaveable { mutableStateOf("z8cqhEywCzc") }

        val savedState = rememberSaveable { mutableStateOf(youtubeVideoId.value) }

        val lifecycleOwner = LocalLifecycleOwner.current

        AndroidView(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
            factory = { context ->

                YouTubePlayerView(context = context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {

                            youTubePlayer.loadVideo(savedState.value, savedPosition.value)
                            youTubePlayer.setVolume(0)

                        }

                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {

                            savedPosition.value = second

                        }
                    })
                }
            })
    }