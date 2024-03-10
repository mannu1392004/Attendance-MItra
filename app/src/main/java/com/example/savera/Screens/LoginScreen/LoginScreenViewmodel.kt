package com.example.savera.Screens.LoginScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginScreenViewmodel : ViewModel() {
    val errormessage : MutableState<String> = mutableStateOf("")




    val auth: FirebaseAuth = Firebase.auth

    fun signIn(
        email: String, password: String,
        home: () -> Unit

    ) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {
                                home()
                            } else {
                                user.sendEmailVerification().addOnCompleteListener() { task ->
                                    if (task.isSuccessful) {

                                        errormessage.value ="Your email is not yet verified. Please check your inbox for an email from Savera."
                                    } else {
                                        val err = task.exception?.localizedMessage


                                        errormessage.value = "$err"
                                    }

                                }

                            }
                        }
                    }
                    if (!task.isSuccessful) {

                        task.exception?.localizedMessage?.let { errormessage.value = "${it.toString()}" }

                    }

                }
            } catch (e: Exception) {
                Log.d("error", e.localizedMessage.toString())
            }
        }
    }

}