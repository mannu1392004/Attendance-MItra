package com.example.savera

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.savera.Experiment.try2

import com.example.savera.Navigation.Navigation
import com.example.savera.ui.theme.SaveraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

