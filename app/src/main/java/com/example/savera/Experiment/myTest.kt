package com.example.savera.Experiment

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.foundation.SwipeToDismissBoxState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun test(){

    val x = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(state = x, backgroundContent ={

    } ) {
        Text(text = "hbhbhj")
    }



}