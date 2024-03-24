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
                Log.d("error in feedback",it)


            }, hashMap = data

        )
    }

    // checking userfirst time or not
    fun checking(email:String,exists:()->Unit,notexists:()->Unit){
        viewModelScope.launch {
            AppRepository.checkYearInformation(currentUser = email,
                exist = {
                      exists()
                },
                notexist = {
                   notexists()
                })
        }

    }

// adding new user
    fun addnewuser(documentPath: String,
                   data: HashMap<String, String>,
successfull:()->Unit,
                   error: (String) -> Unit
                   ){
        viewModelScope.launch {
            AppRepository.addnewuser(
                documentPath = documentPath,
                data = data,
                successfull = {
                       successfull()
                },
                failure = {
                    error(it)
                }

            )
        }


    }





}