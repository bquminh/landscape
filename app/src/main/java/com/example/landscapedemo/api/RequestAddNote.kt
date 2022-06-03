package com.example.landscapedemo.api

data class RequestAddNote(
    val archived: Boolean,
    val content: String,
    val pin: Boolean,
    val projectId: Int
)