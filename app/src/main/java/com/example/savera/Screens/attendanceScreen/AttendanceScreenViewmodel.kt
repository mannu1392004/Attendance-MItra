package com.example.savera.Screens.attendanceScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.savera.Repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow

class AttendanceScreenViewmodel:ViewModel() {

    var list : MutableStateFlow<List<String>> =MutableStateFlow(emptyList())
    init {
        fetchDocumentsFromCollection()
    }
    fun fetchDocumentsFromCollection() {
        AppRepository.fetchDocuments("Classes",
            onSuccess = { documentList ->
          Log.d("lissssssssssssssssss",documentList.toString())
           list.value = documentList


            },
            onFailure = { exception ->


            }
        )
    }


}