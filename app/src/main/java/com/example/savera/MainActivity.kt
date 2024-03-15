package com.example.savera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.savera.Navigation.Navigation
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

