package com.example.savera.Screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savera.Model.syllabusshower
import com.example.savera.Repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class dashboardViewmodal:ViewModel() {
    val classList:MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    init {
        viewModelScope.launch {
            AppRepository.fetchDocuments(collectionName = "Classes",
                onSuccess = {
classList.value = it
                },
                onFailure = {})
        }
    }

    fun addStudent(className:String,data:Any,name: String,
                   success:()->Unit,
                   error:(String)->Unit
                   ){
        viewModelScope.launch {
            AppRepository.addNewStudent(
                className = className,
                data = data,
                error = {
                        error(it)
                },
                successfull = {
                              success()
                }
                ,name = name
            )
        }
    }
// fetch Syllabus

    fun fetchSyllabus(success: (List<syllabusshower>) -> Unit) {
        viewModelScope.launch {
            AppRepository.fetchSyllabus(
                className = "Class 1",
                successfull = {
                    success(it)
                }
            )
        }
    }



}
