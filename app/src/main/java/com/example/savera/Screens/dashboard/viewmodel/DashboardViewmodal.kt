package com.example.savera.Screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savera.Model.ChapterList
import com.example.savera.Model.UserInformation
import com.example.savera.Model.adminDetails
import com.example.savera.Model.feedBackType
import com.example.savera.Model.studentAttendanceData
import com.example.savera.Model.syllabusshower
import com.example.savera.Model.topicList
import com.example.savera.Repository.AppRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class dashboardViewmodal : ViewModel() {
    val classList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            AppRepository.fetchDocuments(collectionName = "Classes",
                onSuccess = {
                    classList.value = it
                },
                onFailure = {})
        }
    }

    fun addStudent(
        className: String, data: Any, name: String,
        success: () -> Unit,
        error: (String) -> Unit,
    ) {
        viewModelScope.launch {
            AppRepository.addNewStudent(
                className = className,
                data = data,
                error = {
                    error(it)
                },
                successfull = {
                    success()
                }, name = name
            )
        }
    }
// fetch Subjects

    fun fetchSyllabus(success: (List<syllabusshower>) -> Unit, className: String) {
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
    fun fetchChapters(
        classname: String, successful: (List<ChapterList>) -> Unit,
        subject: String,
    ) {
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
    fun fetchTopics(
        className: String,
        subject: String,
        chapter: String,
        success: (List<topicList>) -> Unit,
    ) {
        AppRepository.fetchTopics(
            classes = className,
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
        chapter: String,
        topic: String,
        data: Any,
    ) {
        AppRepository.ChangeStatus(
            classes = classes,
            subject, chapter, topic, data
        )
    }

    val email = FirebaseAuth.getInstance().currentUser?.email
    fun fetchUserDetails(info: (UserInformation) -> Unit, failure: (String) -> Unit) {
        if (email != null) {
            AppRepository.userInformat(email = email,
                onSuccess = {
                    info(it)
                },
                onFailure = {
                    failure(it)
                })
        }
    }

    // volunteers Attendance
    fun volattendance(date: String, email: String, success: () -> Unit) {
        viewModelScope.launch {
            AppRepository.volunteersAttendance(
                date = date,
                email = email,
                successfull = { success() }
            )
        }

    }

    fun fetchAdmins(success: (List<adminDetails>) -> Unit) {
        viewModelScope.launch {
            AppRepository.getAdminDetails {
                success(it)
            }
        }
    }

    fun fetchAttendance(date: String, success: (List<studentAttendanceData>) -> Unit) {
        AppRepository.showAttendance(date = date) {
            success(it)
        }

    }

    fun fetchSyllabus1(className: String,success: (List<String>) -> Unit){

        AppRepository.fetchSylabus(className){
            success(it)
        }

    }

    fun fetchChaptersForEdit(className: String,
                             subject: String,
                             success: (List<String>) -> Unit){
        AppRepository.fetchCHapters(
            className = className,
            subject = subject
        ){
            success(it)
        }
    }

fun fetchtopicsforedit(className: String,
                       subject: String,
                       chapter: String,
                       success: (List<String>) -> Unit){

    viewModelScope.launch {

        AppRepository.fetchTopicsforedit(
            className = className,
            subject = subject,
            chapter = chapter,
        ){
            success(it)
        }

    }


}


    fun getFeedbacks(success: (List<feedBackType>) -> Unit){
        AppRepository.seeFeedBack {
            success(it)

        }
    }


}
