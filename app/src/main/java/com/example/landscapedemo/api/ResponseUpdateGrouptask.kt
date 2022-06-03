package com.example.landscapedemo.api

data class ResponseUpdateGrouptask(
    val code: Int,
    val `data`: Grouptask,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)