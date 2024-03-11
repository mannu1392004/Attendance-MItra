package com.example.savera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.savera.Navigation.Navigation
import com.example.savera.Screens.HomeScreen.HomeScreen
import com.example.savera.Screens.LoadingScreen.loadingScreen
import com.example.savera.ui.theme.SaveraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaveraTheme {
                // A surface container using the 'background' color from the theme


Navigation()
                }
            }
        }
    }

