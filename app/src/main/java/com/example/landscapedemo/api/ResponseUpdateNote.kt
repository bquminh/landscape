package com.example.landscapedemo.api

data class ResponseUpdateNote(
    val code: Int,
    val `data`: Note,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)