package com.example.savera.Repository

import android.util.Log
import com.example.savera.Model.events_Data
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

object AppRepository {




    // Function to fetch a list of documents within a collection
    fun fetchDocuments(collectionName: String, onSuccess: (List<String>) -> Unit, onFailure: (Exception) -> Unit) {
         val firestore = FirebaseFirestore.getInstance()

        val collectionRef = firestore.collection(collectionName)
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val documentList = mutableListOf<String>()
                for (document in querySnapshot.documents) {
                    documentList.add(document.id)
                }
                onSuccess(documentList)

            }
            .addOnFailureListener { exception ->
                onFailure(exception)

            }
    }

    // Function to fetch a list of subcollections within a document
    fun GetStudentList(documentPath: String,collectionName: String
                       ,onSuccess: (List<String>) -> Unit,
                       onFailure: (Exception) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()

        firestore
            .collection(collectionName)
            .document(documentPath)
            .collection("Students")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val documentList = mutableListOf<String>()
                for (document in documentSnapshot.documents) {
                    documentList.add(document.id)
                }

                onSuccess(documentList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // function to add the data to attendance
    fun adddata(collectionName: String,documentPath: String
                ,studentName:String,date:String,
                data:Any,error:(String)->Unit){
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(collectionName)
            .document(documentPath)
            .collection("Students")
            .document(studentName)
            .collection("Attendance")
            .document(date)
            .set(data)
            .addOnSuccessListener {

            }
            .addOnFailureListener{
                error(it.localizedMessage)
            }
    }

    // function to add the feed back
    // also add  present Student
    fun addfeedback(collectionName: String,documentPath: String
                    ,hashMap: HashMap<String,String>
                    ,successfull:()->Unit,
                    failure:(String)->Unit) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(collectionName).get().addOnSuccessListener { documentSnapshot ->
            val documentList = mutableListOf<String>()
            for (document in documentSnapshot.documents) {
                documentList.add(document.id)
            }

            if (documentList.contains(documentPath))
                firestore.collection(collectionName)
                    .document(documentPath)
                    .update(hashMap as Map<String, Any>)
                    .addOnSuccessListener {
                        successfull()
                    }
                    .addOnFailureListener {
                        failure(it.localizedMessage.toString())
                    }
            else
                firestore.collection(collectionName)
                    .document(documentPath)
                    .set(hashMap as Map<String, Any>)
                    .addOnSuccessListener {
                        successfull()
                    }
                    .addOnFailureListener {
                        failure(it.localizedMessage.toString())


                    }


        }


        }




// add events details

    fun addevents(collectionName: String,
                  documentPath: String,
                  list: HashMap<String,String>
                  ,successfull:()->Unit,
                  failure:(String)->Unit
                  ) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(collectionName)
            .document(documentPath)
            .set(list)
            .addOnSuccessListener {
                successfull()
            }
            .addOnFailureListener {
                failure(it.localizedMessage)
            }
    }

 // getting events from firebase
    fun getEvents(): MutableStateFlow<List<events_Data>> {
        var eventsState:MutableStateFlow<List<events_Data>> = MutableStateFlow(emptyList())

     val firestore = FirebaseFirestore.getInstance()

     firestore.collection("Events")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Handle any exceptions
                    return@addSnapshotListener
                }

                val events = mutableListOf<events_Data>()

                snapshot?.documents?.forEach { documents ->
                    val event = events_Data(
                        date = documents.getString("Date") ?: "",
                        description = documents.getString("Description") ?: "",
                        eventName = documents.getString("Event Name") ?: "",
                        location = documents.getString("Location") ?: "",
                        time = documents.getString("Time") ?: "",
                        createdBy = documents.getString("created by") ?: "",
                        imageUrl = documents.getString("imageurl") ?: ""
                    )

                    events.add(event)
                }

                eventsState.value = events
            }


        return eventsState
    }

    // newUser check code
    suspend fun checkYearInformation(
        currentUser: String,
        exist: () -> Unit,
        notexist: () -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

        if (currentUser != null) {
                val userDocRef = firestore.collection("teachers")
                    .document(currentUser)
            val documentSnapshot = userDocRef.get().await()
                if (documentSnapshot.exists()) {
                    exist()
                } else {
                    notexist()
                }
            }
        }



   // new user add code
    fun addnewuser(documentPath: String,data: Any,successfull: () -> Unit,
                   failure: (String) -> Unit){
       val firestore = FirebaseFirestore.getInstance()
       firestore.collection("teachers")
           .document(documentPath)
           .set(data)
           .addOnSuccessListener {
               successfull()
           }
           .addOnFailureListener{
               failure(it.localizedMessage)
           }

    }

// Add new Student
    fun addNewStudent(name: String,className: String,data: Any,successfull: () -> Unit,
                      error: (String) -> Unit){
        val firestore = FirebaseFirestore.getInstance()
    firestore.collection("Classes")
        .document(className)
        .collection("Students")
        .document(name)
        .set(data)
        .addOnSuccessListener {
            successfull()
        }
        .addOnFailureListener{
            error(it.localizedMessage)
        }
    }

    //check if attendance taken or not
    suspend fun checkAttendanceTakenOrNot(
        className: String,
        date: String,
        taken: () -> Unit,
        nottaken: () -> Unit,
    ) {
        val firestore = FirebaseFirestore.getInstance()

Log.d("wfbkjfcewfllewmfkjrhfwefklhwjklqwewr",date)
            val userDocRef = firestore.collection("Classes")
                .document(className)
            val documentSnapshot = userDocRef.get().await()
          if (documentSnapshot.contains(date)){
              taken()
          }
        else{
            nottaken()
        }

        }
//  user information






    }










