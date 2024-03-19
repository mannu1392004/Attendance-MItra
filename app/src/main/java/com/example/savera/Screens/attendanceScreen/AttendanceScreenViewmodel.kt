package com.example.savera.Screens.attendanceScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savera.Repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AttendanceScreenViewmodel:ViewModel() {


    // getting student list
    val Student_List :MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    fun fetchstudentslist(classname:String){
        AppRepository.GetStudentList(
            documentPath = classname,
            onSuccess = {
               Student_List.value = it
            },
            collectionName = "Classes",
            onFailure = {

            }
        )



    }



    // geting classes
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
    fun attendancemarked(collectionName: String,documentPath: String,studentName:String,date:String,
                         data:Any,error:(String)->Unit){
        viewModelScope.launch {
            AppRepository.adddata(
                collectionName = collectionName,
                documentPath,
                studentName,
                data = data,
                date = date,
                error = {
                    error(it)
                }

            )
        }

    }




}