package com.example.landscapedemo.api

data class ResponseUpdateState(
    val code: Int,
    val `data`: State,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)