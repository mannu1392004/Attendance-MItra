package com.example.savera.Screens.messageScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class messageScreenViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _showLoadMoreButton = MutableStateFlow<Boolean>(true)
    val showLoadMoreButton: StateFlow<Boolean> = _showLoadMoreButton

    init {
        _userName.value =
            FirebaseAuth.getInstance().currentUser?.email?: "Anonymous"
    }


    private val _messagesStateFlow = MutableStateFlow<List<Message>>(emptyList())
    val messagesStateFlow: StateFlow<List<Message>> = _messagesStateFlow


    private val database =
        FirebaseDatabase.getInstance("https://savera-504a2-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child(
            "chats"
        )
    private val firestore = FirebaseFirestore.getInstance()


    private val pageSize = 20
    private var totalMessageCount = 0

    private var lastLoadedMessageKey: String? = null


    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            val lastMessageSnapshot = snapshot.children.lastOrNull()
            val lastMessage = lastMessageSnapshot?.getValue(Message::class.java) ?: Message("", "", 0, "")

            if (_messagesStateFlow.value.isEmpty() || lastMessage != _messagesStateFlow.value[0]) {
                _messagesStateFlow.value = listOf(lastMessage) + _messagesStateFlow.value
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error
        }
    }

    var chatroom = ""

    fun initChatroom(selectedGroup: String) {
        // Assign chatroom based on selectedGroup
        chatroom = when (selectedGroup) {
            "firstandsecond" -> "year1_year2"
            "secondandthird" -> "year2_year3"
            "thirdandfour" -> "year3_year4"
            "official" -> "savera_official"
            "first" -> "first"
            "second" -> "second"
            "third" -> "third"
            "fourth" -> "fourth"
            else -> "error" //throw some error instead of this
        }
        database.child(chatroom).addValueEventListener(listener)
        // Log.d("lakshay", "initChatroom:$chatroom ")
        loadInitialMessages()
    }

    fun sendMessage(messageText: String) {
        val currentTimeMillis = System.currentTimeMillis()
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(currentTimeMillis))

        val newMessage = Message(messageText, userName.value!!, currentTimeMillis, currentDate)
        Log.d("lakshay", "sendMessage: $newMessage ")
        database.child(chatroom).push().setValue(newMessage)
            .addOnSuccessListener {
 //               sendNotification(newMessage.text,newMessage.senderName)
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.e("MessageScreenViewModel", "Error sending message: $exception")
            }
    }
//    fun sendNotification(messageText: String,senderName: String) {
//      var topic = when(chatroom){
//          "year1_year2" -> "1st_2nd_Year"
//          "year2_year3" -> "2nd_3rd_Year"
//          "year3_year4" -> "3rd_4th_Year"
//          "savera_official" -> "official"
//          "first" -> "1st_Year"
//          "second" -> "2nd_Year"
//          "third" -> "3rd_Year"
//          "fourth" -> "4th_Year"
//          else -> "error"
//      }
//       // FCMMessageUtility.sendMessageToTopic(topic,messageText,senderName)
//        FCMMessageUtility.sendMessage("New Message",messageText,topic)
//    }

    suspend fun getProfilePictureUrl(email: String): String? {
        return try {
            val documentSnapshot = firestore.collection("teachers").document(email).get().await()
            val profilePicUrl = documentSnapshot.getString("ProfilePic")
            profilePicUrl
        } catch (e: Exception) {
            null
        }
    }

    override fun onCleared() {
        super.onCleared()
        database.child(chatroom).removeEventListener(listener)
    }
    fun onResume() {
        viewModelScope.launch {
            countTotalMessages()
        }
    }
    fun countTotalMessages() {
        database.child(chatroom)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    totalMessageCount =
                        snapshot.childrenCount.toInt() // Get total count of messages
                    if(totalMessageCount <= 20){
                        _showLoadMoreButton.value = false
                    }else if(totalMessageCount > 20){
                        _showLoadMoreButton.value = true
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("lakshay", "onCancelled: Got some database error")                }
            })
    }


    fun loadInitialMessages() {
        countTotalMessages()
        database.child(chatroom)
            .orderByKey()
            .limitToLast(pageSize)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newMessages = snapshot.children.reversed().map {
                        it.getValue(Message::class.java) ?: Message("", "", 0, "")
                    }
                    _messagesStateFlow.value = newMessages
                    lastLoadedMessageKey = snapshot.children.firstOrNull()?.key

                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }


    fun loadMoreMessages() {
        lastLoadedMessageKey?.let { lastKey ->
            database.child(chatroom)
                .orderByKey()
                .endBefore(lastKey)
                .limitToLast(pageSize)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val newMessages = snapshot.children.reversed().map {
                            it.getValue(Message::class.java) ?: Message("", "", 0, "")
                        }
                        _messagesStateFlow.value += newMessages
                        lastLoadedMessageKey = snapshot.children.firstOrNull()?.key

                        if(_messagesStateFlow.value.size >= totalMessageCount){
                            _showLoadMoreButton.value = false
                        }
                    }


                    override fun onCancelled(error: DatabaseError) {
                        // Handle error
                    }
                })
        }
    }


}

data class Message(val text: String, val senderName: String, val timestamp: Long, val date: String) {
    constructor() : this("", "", 0, "")
}