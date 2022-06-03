package com.example.landscapedemo.api

data class ResponseGrouptaskList(
    val code: Int,
    val `data`: GrouptaskList,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)