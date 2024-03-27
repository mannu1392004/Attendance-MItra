package com.example.savera.Model

import org.w3c.dom.Document

data class events_Data(
    val document: String,
    val date: String,
    val description: String,
    val eventName: String,
    val location: String,
    val time: String,
    val createdBy: String,
    val imageUrl: String
)