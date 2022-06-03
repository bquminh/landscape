package com.example.landscapedemo.api

data class ResponseStateList(
    val code: Int,
    val data: Data,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)