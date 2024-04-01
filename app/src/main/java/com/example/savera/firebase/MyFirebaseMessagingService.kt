package com.example.savera.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.savera.MainActivity
import com.example.savera.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.let {
            Log.d("lakshay", "Message Notification Body: ${it.body}")
            it.body?.let { body -> sendNotification(body) }
        }


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email ?: return

        val db = FirebaseFirestore.getInstance()
        val teachersRef = db.collection("teachers")
        val docRef = teachersRef.document(email)

        val data = hashMapOf("Token" to token)
        docRef.update(data as Map<String, Any>)
            .addOnSuccessListener {
                Log.d("Firestore", "Token updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error updating token", exception)
            }
    }


    private fun sendNotification(messageBody: String) {
        val requestCode = 0
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val channelId = "1"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Savera")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}




//test work below
//    private fun isAppInForeground(): Boolean {
//         val lifecycleOwner = ProcessLifecycleOwner.get()
//         var isInBackground = true
//        //test this part [PENDING]
//        val lifecycleState = lifecycleOwner.lifecycle.currentState
//        val notInBackground = lifecycleState >= Lifecycle.State.STARTED
//        if (notInBackground) {
//            isInBackground = false
//        } else if (lifecycleState == Lifecycle.State.DESTROYED) {
//            isInBackground = true
//        }
//        return !isInBackground
//    }
//    private fun showNotification(title: String, body: String,senderName: String) {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
//            createNotificationChannel()
//        }
//
//        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setSmallIcon(R.drawable.savera_logo1_removebg_preview)
//
//        notificationManager.notify(1, notificationBuilder.build())
//
//    }
//
//
//
//
//    private val CHANNEL_ID = getString(R.string.default_notification_channel_id)
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Messaging" // Replace with your channel name string resource
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance)
//            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
