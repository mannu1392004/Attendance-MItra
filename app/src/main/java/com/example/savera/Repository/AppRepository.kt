package com.example.savera.Repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object AppRepository {


    private val firestore = FirebaseFirestore.getInstance()

    // Function to fetch a list of documents within a collection
    fun fetchDocuments(collectionName: String, onSuccess: (List<String>) -> Unit, onFailure: (Exception) -> Unit) {
        val collectionRef = firestore.collection(collectionName)
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                val documentList = mutableListOf<String>()
                for (document in querySnapshot.documents) {
                    documentList.add(document.id)
                }
                onSuccess(documentList)
                Log.d("lisssssssssssssssssssssssssssssssssssswdnndejkdemdrj",
                    documentList.toString())
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
                Log.d("failureeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                    exception.toString())
            }
    }

    // Function to fetch a list of subcollections within a document
    fun GetStudentList(documentPath: String,collectionName: String ,onSuccess: (List<String>) -> Unit, onFailure: (Exception) -> Unit) {
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

    // function to add the data
    fun adddata(collectionName: String,documentPath: String,studentName:String,date:String,
                data:Any,error:(String)->Unit){
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




}