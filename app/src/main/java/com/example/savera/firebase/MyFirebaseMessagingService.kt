package com.example.savera.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
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
