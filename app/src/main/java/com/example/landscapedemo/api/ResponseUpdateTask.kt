package com.example.landscapedemo.api

data class ResponseUpdateTask(
    val code: Int,
    val `data`: Task,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)