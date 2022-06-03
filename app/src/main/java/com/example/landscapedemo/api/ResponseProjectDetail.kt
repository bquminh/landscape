package com.example.landscapedemo.api

data class ResponseProjectDetail(
    val code: Int,
    val `data`: Project,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)