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

class HomeScreenViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val _showDialogState = MutableStateFlow(false)
    val showDialogState: StateFlow<Boolean> = _showDialogState.asStateFlow()

    private var _userInputState = MutableStateFlow("")
    var userInputState: StateFlow<String> = _userInputState.asStateFlow()

    fun onDialogDismiss() {
        _showDialogState.value = false
    }

    fun onUserInputChange(newValue: String) {
        _userInputState.value = newValue
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

    fun onYearEntered(year: Int) {
        saveYearInformation(year)
        _showDialogState.value = false
    }

    private fun saveYearInformation(year: Int) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userUpdates = hashMapOf<String, Any>("year" to year)
            val userDocRef = firestore.collection("teachers").document(userId)
            viewModelScope.launch {
                userDocRef.set(userUpdates)
                    .addOnSuccessListener { Log.d("TAG", "Year information saved!") }
                    .addOnFailureListener { e -> Log.w("TAG", "Error saving year information", e) }
            }
        }
    }


    // feedback functionality
fun addFeedback(collectionName:String,documentPath:String,data:HashMap<String,String>,Successfull:()->Unit,
                error:(String)->Unit){
    AppRepository.addfeedback(collectionName,
        documentPath = documentPath,
        successfull = {
Successfull()

        }
    , failure = {
error(it)


        }
        , hashMap = data

    )
}





}