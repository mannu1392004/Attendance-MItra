package com.example.savera.Screens.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savera.Repository.AppRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val _showDialogState = MutableStateFlow(false)
    val showDialogState: StateFlow<Boolean> = _showDialogState.asStateFlow()

    private var _userNInputState = MutableStateFlow("")
    var userNInputState: StateFlow<String> = _userNInputState.asStateFlow()

    private var _userYInputState = MutableStateFlow("")
    var userYInputState: StateFlow<String> = _userYInputState.asStateFlow()

    fun onDialogDismiss() {
        _showDialogState.value = false
    }

    fun onUserYearInputChange(newValue: String) {
        _userYInputState.value = newValue
    }

    fun onUserNameInputChange(newValue: String) {
        _userNInputState.value = newValue
    }

    fun checkYearInformation() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                val userId = currentUser.uid
                val userDocRef = firestore.collection("teachers").document(userId)
                val documentSnapshot = userDocRef.get().await()
                if (documentSnapshot.exists()) {
                    val year = documentSnapshot.data?.get("year")?.toString()?.toIntOrNull()

                    Log.d("lakshay", "checkYearInformation: $year ")
                } else {
                    Log.d("lakshay", "year info not exist ")
                    // User document doesn't exist, handle appropriately
                    // (e.g., create a new document with default year)
                    _showDialogState.value = true // Open dialog for year input

                }
            }
        }
    }

    fun onYearNameEntered(year: Int, name: String) {
        saveYearInformation(year, name)
        _showDialogState.value = false
    }

    private fun saveYearInformation(year: Int, name: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {

            val userId = currentUser.email?.split("@")?.get(0)
            val userUpdates = hashMapOf<String, Any>(
                "year" to year,
                "name" to name
            )
            val userDocRef = userId?.let { firestore.collection("teachers").document(it) }
            viewModelScope.launch {
                if (userDocRef != null) {
                    userDocRef.set(userUpdates)
                        .addOnSuccessListener { Log.d("TAG", "Year information saved!") }
                        .addOnFailureListener { e ->
                            Log.w(
                                "TAG",
                                "Error saving year information",
                                e
                            )
                        }
                }
            }
        }
    }


    // feedback functionality
    fun addFeedback(
        collectionName: String,
        documentPath: String,
        data: HashMap<String, String>,
        Successfull: () -> Unit,
        error: (String) -> Unit,
    ) {
        AppRepository.addfeedback(collectionName,
            documentPath = documentPath,
            successfull = {
                Successfull()

            }, failure = {
                error(it)


            }, hashMap = data

        )
    }


}