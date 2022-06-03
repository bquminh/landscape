package com.example.landscapedemo.api

data class RequestAddState(
    val description: String,
    val endDate: String,
    val name: String,
    val startDate: String,
    val status: Int
)