package com.example.landscapedemo.api

data class RequestUpdateProject(
    val archived: Boolean,
    val customer: String,
    val description: String,
    val endDate: String,
    val id: Int,
    val lastUpdatedTime: String,
    val lastUpdatedUser: String,
    val name: String,
    val startDate: String,
    val status: Int
)