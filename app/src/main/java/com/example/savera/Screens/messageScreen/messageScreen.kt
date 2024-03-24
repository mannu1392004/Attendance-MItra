package com.example.savera.Screens.messageScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment


@Composable
fun ChatScreen(
    viewModel: messageScreenViewModel,
    msgNavigation: NavHostController,
    groupSelected: String?,
) {


    val messageText = remember { mutableStateOf(TextFieldValue("")) }
    val currUserName = remember { viewModel.userName }
    val showLoadMoreButton by viewModel.showLoadMoreButton.collectAsState()

    val m = viewModel.messagesStateFlow.collectAsState(emptyList()).value


    LaunchedEffect(groupSelected) {
        Log.d("lakshay", "ChatScreen: $groupSelected")
        if (groupSelected != null) {
            viewModel.initChatroom(groupSelected)
        }
        viewModel.onResume()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 1.dp, vertical = 10.dp)) {
        LazyColumn( reverseLayout = true,modifier = Modifier.weight(1f)) {

            items(m) { message ->
                MessageBubble(message = message, viewModel = viewModel)
            }
            item {
                if (m.isNotEmpty()) {
                    if (showLoadMoreButton) {
                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                            LoadMoreButton {
                                viewModel.loadMoreMessages()
                            }
                        }

                    }
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            TextField(
                value = messageText.value,
                onValueChange = { messageText.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                placeholder = { Text(text = "Type Your Message") },
                maxLines = 1,
                shape = RoundedCornerShape(17.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = {
                if (messageText.value.text.isNotEmpty()) {
                    viewModel.sendMessage(messageText.value.text)
                    messageText.value = TextFieldValue("")
                } else {
                    //add something TOAST like message
                }
            }, modifier = Modifier.padding(5.dp)) {
                Icon(Icons.Filled.Send, contentDescription = "Send message")
            }
        }
    }
}


@Composable
fun MessageBubble(message: Message, viewModel: messageScreenViewModel) {
    val currUserName = remember { viewModel.userName }

    val isSender = message.senderName == currUserName.value

    val background = if (isSender) Color(0xFFF8AC32) else Color(0xffF9A825)

    val alignment = if (isSender) Alignment.End else Alignment.Start
    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White


    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        if (!isSender) {
            Text(
                text = message.senderName,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
        Surface(
            modifier = Modifier
                .padding(top = if (!isSender) 4.dp else 0.dp),
            color = background, shape = RoundedCornerShape(8.dp),
            contentColor = if (isSender) Color.White else Color.Black
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}


@Composable
fun LoadMoreButton(onClick: () -> Unit) {
    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor =Color(0xffF9A825)  , contentColor = textColor), // Set background color to yellow
        modifier = Modifier
            .width(120.dp),

        //align load more button in center and change color themes
    ) {
        Text(text = "Load More")
    }
}
