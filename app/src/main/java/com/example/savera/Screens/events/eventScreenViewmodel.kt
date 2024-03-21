package com.example.savera.Screens.events


import androidx.lifecycle.ViewModel
import com.example.savera.Repository.AppRepository


class eventScreenViewmodel:ViewModel() {

    fun addeventsDetail(collection:String,
                        document: String,
                        data:HashMap<String,String>,
                        onsuccess:()->Unit,
                        failure:(String)->Unit){

        AppRepository.addevents(
            collectionName = collection,
            documentPath = document,
            list = data,
            successfull = {
onsuccess()
            },
            failure = {
failure(it)
            }
        )

    }



}