package com.example.savera.Experiment


import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

lateinit var fusedLocationProviderClient: FusedLocationProviderClient
@Composable
fun try2() {
    val context = LocalContext.current
val latitude = remember {
    mutableDoubleStateOf(0.0)
}
    val longitude = remember {
        mutableDoubleStateOf(0.0)
    }

    val answer = remember {
        mutableStateOf<Float>(0.2F)
    }




    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
Column {
    Text(text = "latitude ${latitude.value}  longitude ${longitude.value}")

}}