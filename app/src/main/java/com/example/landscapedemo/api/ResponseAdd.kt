package com.example.landscapedemo.api

data class ResponseAdd(
    val code: Int,
    val `data`: Int,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)