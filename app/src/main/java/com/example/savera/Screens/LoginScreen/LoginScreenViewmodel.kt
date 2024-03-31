package com.example.savera.Screens.LoginScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class LoginScreenViewmodel : ViewModel() {
    val errormessage : MutableState<String> = mutableStateOf("")




    val auth: FirebaseAuth = Firebase.auth

    fun signIn(
        email: String, password: String,
        home: () -> Unit,
        emailsent:(String)->Unit,
        othererror:(String)->Unit,
        anyelse:(String)->Unit


    ) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {

                                FirebaseMessaging.getInstance().token
                                    .addOnSuccessListener { token ->
                                        // Token retrieved successfully, store it in Firestore or handle as needed

                                        Log.d("FCM Token", "Token: $token")
                                        val user = FirebaseAuth.getInstance().currentUser
                                        val email = user?.email ?: return@addOnSuccessListener // Handle null email

                                        val db = FirebaseFirestore.getInstance()
                                        val teachersRef = db.collection("teachers")
                                        val docRef = teachersRef.document(email)

                                        // Create a map for the data to be saved
                                        val data = hashMapOf("Token" to token)

                                        // Save the token to Firestore
                                        docRef.set(data)
                                            .addOnSuccessListener {
                                                // Token saved successfully
                                                Log.d("Firestore", "Token saved to database")

                                                //technically after saving the token, we should navigate to the home screen
                                            }
                                            .addOnFailureListener { exception ->
                                                // Handle failure to save to Firestore
                                                Log.w("Firestore", "Error saving token", exception)
                                            }
                                    }
                                    .addOnFailureListener { exception ->
                                        // Handle failure to retrieve FCM token
                                        Log.e("FCM Token", "Error getting token", exception)
                                    }
                                home()
                            } else {
                                user.sendEmailVerification().addOnCompleteListener() { task ->
                                    if (task.isSuccessful) {
                                        emailsent(
                                            "Your email is not yet verified. Please check your inbox for an email from Savera."
                                        )


                                    } else {
                                        val err = task.exception?.localizedMessage

                                       othererror(err.toString())
                                    }

                                }

                            }
                        }
                    }
                    if (!task.isSuccessful) {

                        task.exception?.localizedMessage?.let {
                            anyelse(it)
                        } }



                }
            } catch (e: Exception) {
                e.localizedMessage?.let { Log.d("error", it.toString()) }
            }
        }
    }

}