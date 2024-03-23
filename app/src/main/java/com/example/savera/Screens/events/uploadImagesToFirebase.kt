package com.example.savera.Screens.events

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun uploadImageToFirebase(context: Context, imageUri: Uri, onSuccess:(String)->Unit,
exception:(String)->Unit, eventname:String,mainfoldername:String) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imagesRef = storageRef.child("$mainfoldername/$eventname/${imageUri.lastPathSegment}")
    val uploadTask = imagesRef.putFile(imageUri)

    uploadTask.addOnSuccessListener {
            uploadTaskSnapshot ->
        uploadTaskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
            onSuccess(uri.toString())
        }.addOnFailureListener { exception ->

            exception(exception.toString())

        }
    }.addOnFailureListener { exception ->
        Toast.makeText(context, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
    }


}

@Composable
fun loadBitmap(context: Context, uri: Uri): Bitmap? {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } else {
        ImageDecoder.createSource(context.contentResolver, uri)?.let { source ->
            ImageDecoder.decodeBitmap(source)
        }
    }
}