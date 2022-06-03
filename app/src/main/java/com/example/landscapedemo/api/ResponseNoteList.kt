package com.example.landscapedemo.api

data class ResponseNoteList(
    val code: Int,
    val `data`: List<Note>,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)