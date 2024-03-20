package com.example.savera.Screens.messageScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController

@Composable
fun ChatScreen(viewModel: messageScreenViewModel,msgNavigation : NavHostController) {
    val messageText = remember { mutableStateOf(TextFieldValue("")) }
    val currUserName = remember { viewModel.userName }
    val year = remember { viewModel.year }
    // Access user data
    //    val userDisplayName = currUserName.value ?: ""
    //    val userYearValue = year.value ?: 0


    LaunchedEffect(Unit) {
        viewModel.fetchUserData()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(viewModel.messages) { message ->
                MessageBubble(message = message,currUserName)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
        ) {
            TextField(
                value = messageText.value,
                onValueChange = { messageText.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Type Your Message") },
                maxLines = 1
            )
            IconButton(onClick = {
                viewModel.sendMessage(messageText.value.text)
                messageText.value = TextFieldValue("") // Clear text field after sending
            }) {
                Icon(Icons.Filled.Send, contentDescription = "Send message")
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message, CurrUsername: LiveData<String>) {
    val isSender = message.senderName == CurrUsername.value  // Check if message sender matches user name

    val background = if (isSender) MaterialTheme.colorScheme.primary else Color.LightGray

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(color = background, shape = RoundedCornerShape(8.dp)),
        contentColor = if (isSender) Color.White else Color.Black
    ) {
        Text(text = "${message.senderName}: ${message.text}")  // Include sender name in the message
    }
}