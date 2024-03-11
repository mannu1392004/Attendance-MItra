package com.example.savera.Components

import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainContent() {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(500.dp)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
                .shadow(elevation = 120.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                ),

        ) {
            Text(
                text = "Documentary",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.typography.headlineMedium.color
                ),                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        MyApp()
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
                .shadow(elevation = 120.dp)
        ) {
            Text(
                text = "About Savera",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.typography.headlineMedium.color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Savera School is an evening school in DCRUST Murthal run by Student volunteers to educate the underprivileged section of the society.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp)
            )
            Spacer(modifier = Modifier.height(9.dp)) // Add some space between heading and text

            Button(onClick = {  },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffF57F17),
                    contentColor = Color.White),
                modifier = Modifier.padding(9.dp)
            ) {
                Text("Know More")
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
                .shadow(elevation = 120.dp)
        ) {
            Text(
                text = "Any idea or suggestions",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.typography.headlineMedium.color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFD8A09),
                    unfocusedBorderColor = Color(0xFFFD8A09),
                    cursorColor = Color(0xFFFD8A09),
                    focusedLabelColor = Color(0xFFFD8A09),
                    unfocusedLabelColor = Color(0xFFFD8A09)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Type your ideas here!") }
            )
        }
    }
}

@Composable
fun GoogleDriveVideoPlayer(embeddedUrl: String) {
    AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            settings.setSupportZoom(true)
            settings.pluginState = WebSettings.PluginState.ON
            loadDataWithBaseURL(null, "<html><body style='margin:0px;padding:0px;'><iframe width='100%' height='100%' src='$embeddedUrl' frameborder='0' allowfullscreen></iframe></body></html>", "text/html", "UTF-8", null)
        }
    })
}

@Composable
fun MyApp() {
    val embeddedUrl = "https://drive.google.com/file/d/12nhHt7RQSfWKPBKZ_g7l8onfSYfCDcJb/view?usp=drive_link"
    GoogleDriveVideoPlayer(embeddedUrl = embeddedUrl)
}


