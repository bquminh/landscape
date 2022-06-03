package com.example.landscapedemo.api

data class ResponseTaskList(
    val code: Int,
    val `data`: List<Task>,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)