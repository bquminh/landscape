package com.example.landscapedemo.api

data class RequestAddGrouptask(
    val description: String,
    val endDate: String,
    val name: String,
    val startDate: String,
    val status: Int
)