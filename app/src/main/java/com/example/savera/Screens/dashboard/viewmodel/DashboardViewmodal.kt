package com.example.savera.Screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savera.Model.syllabusshower
import com.example.savera.Model.ChapterList
import com.example.savera.Model.topicList
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
// fetch Subjects

    fun fetchSyllabus(success: (List<syllabusshower>) -> Unit,className: String) {
        viewModelScope.launch {
            AppRepository.fetchSyllabus(
                className = className,
                successfull = {
                    success(it)
                }
            )
        }
    }


    // fetching chapters
    fun fetchChapters(classname: String, successful: (List<ChapterList>) -> Unit,
                      subject:String) {
        viewModelScope.launch {
            AppRepository.fetchChapters(
                classes = classname,
                successfull = {
                    successful(it)
                },
                subject = subject
            )
        }
    }
    // fetching topics
    fun fetchTopics(className: String,
                    subject: String,
                    chapter:String,
                    success: (List<topicList>) -> Unit){
        AppRepository.fetchTopics(
            classes =className,
            subject = subject,
            chapter = chapter,
            successfull = {
                success(it)
            }
        )


    }
// updating

    fun update(
        classes: String,
        subject: String,
        chapter:String,
        topic:String,
        data:Any,
    ){
        AppRepository.ChangeStatus(
            classes = classes,
            subject, chapter, topic, data
        )
    }




}
