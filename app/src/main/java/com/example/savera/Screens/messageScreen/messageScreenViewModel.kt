package com.example.savera.Screens.messageScreen

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class messageScreenViewModel : ViewModel()
{
   //val userName = "Savera User"
   private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _year = MutableLiveData<Int>()
    val year: LiveData<Int> = _year



    private val database = FirebaseDatabase.getInstance().reference.child("chats")
    var chatroom: String by mutableStateOf("") // Initially empty
    var messages: List<Message> by mutableStateOf(emptyList())

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newMessages = snapshot.children.map { it.getValue(Message::class.java) ?: Message("", "") }
            messages = newMessages
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error
        }
    }

    fun initChatroom(selectedYear: Int) {
        when (selectedYear) {
            in 1..2 -> chatroom = "year1_year2"
            in 2..3 -> chatroom = "year2_year3"
            in 3..4 -> chatroom = "year3_year4"
            else -> chatroom = "savera_official"
        }
        database.child(chatroom).addValueEventListener(listener)
    }

    fun sendMessage(messageText: String) {
        //re-implement this again with livedata datatype

//        val newMessage = Message(messageText, userName)
//        database.child(chatroom).push().setValue(newMessage)
    }

    override fun onCleared() {
        super.onCleared()
        database.child(chatroom).removeEventListener(listener)
    }

    suspend fun fetchUserData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val firestore = FirebaseFirestore.getInstance()

        val userId = currentUser?.email?.split("@")?.get(0) ?: return

        val userDocRef = firestore.collection("teachers").document(userId)

        val documentSnapshot = userDocRef.get().await()
        if (documentSnapshot.exists()) {
            val data = documentSnapshot.data
            val name = data?.get("name") as? String ?: ""
            val year = data?.get("year") as? Int ?: 0
            _userName.postValue(name)
            _year.postValue(year)
        } else {
            // case when document does not exist
        }

    }
}



data class Message(val text: String, val senderName: String)
