package com.example.savera.Screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savera.Repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class dashboardViewmodel:ViewModel() {
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


}
