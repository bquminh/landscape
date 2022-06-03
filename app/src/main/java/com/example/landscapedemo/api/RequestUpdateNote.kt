package com.example.landscapedemo.api

data class RequestUpdateNote(
    val archived: Boolean,
    val content: String,
    val id: Int,
    val pin: Boolean,
    val projectId: Int
)