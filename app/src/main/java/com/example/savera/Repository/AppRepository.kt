package com.example.savera.Repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

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





}