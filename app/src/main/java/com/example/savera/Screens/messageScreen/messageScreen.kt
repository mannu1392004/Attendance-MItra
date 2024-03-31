package com.example.savera.Screens.messageScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import coil.compose.rememberAsyncImagePainter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ChatScreen(
    viewModel: messageScreenViewModel,
    msgNavigation: NavHostController,
    groupSelected: String?,
) {


    val messageText = remember { mutableStateOf(TextFieldValue("")) }
//   val currUserName = remember { viewModel.userName }
    val showLoadMoreButton by viewModel.showLoadMoreButton.collectAsState()

    val m = viewModel.messagesStateFlow.collectAsState(emptyList()).value


    LaunchedEffect(groupSelected) {
        Log.d("lakshay", "ChatScreen: $groupSelected")
        if (groupSelected != null) {
            viewModel.initChatroom(groupSelected)
        }
        viewModel.onResume()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 1.dp, vertical = 10.dp)
    ) {
        LazyColumn(reverseLayout = true, modifier = Modifier.weight(1f)) {

            items(m) { message ->
                MessageBubble(message = message, viewModel = viewModel)
            }
            item {
                if (m.isNotEmpty()) {
                    if (showLoadMoreButton) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                maxLines = 10,
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
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send message")
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
    val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    var profilePicUrl by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(key1 = message.senderName) {
        profilePicUrl = viewModel.getProfilePictureUrl(message.senderName)
    }


    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start,
        verticalAlignment = if (isSender) Alignment.Top else Alignment.CenterVertically
    ) {
        if (!isSender) {
            profilePicUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            horizontalAlignment = alignment,
            modifier = Modifier.weight(1f)
        ) {
            if (!isSender) {
                Text(
                    text = message.senderName.substringBefore("@"),
                    modifier = Modifier.padding(start = 3.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Surface(
                modifier = Modifier
                    .widthIn(max = 270.dp)
                    .padding(top = if (!isSender) 4.dp else 0.dp),
                color = background,
                shape = RoundedCornerShape(
                    topStart =  8.dp,
                    topEnd = 8.dp,
                    bottomEnd = 8.dp,
                    bottomStart = 8.dp
                ),
                contentColor = if (isSender) Color.White else Color.Black
            ) {
                Box(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = message.text,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
            }

            Text(
                text = "${dateFormat.format(Date(message.timestamp))} ${timeFormat.format(Date(message.timestamp))}",
                modifier = Modifier
                    .padding(
                        start = if (isSender) 8.dp else 3.dp,
                        end = if (isSender) 3.dp else 8.dp,
                        bottom = 0.dp,
                        top = 2.dp
                    ),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }

        if (isSender) {
        Spacer(modifier = Modifier.width(8.dp))
            profilePicUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
fun LoadMoreButton(onClick: () -> Unit) {
    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xffF9A825),
            contentColor = textColor
        ), // Set background color to yellow
        modifier = Modifier
            .width(120.dp),

        //align load more button in center and change color themes
    ) {
        Text(text = "Load More")
    }
}
