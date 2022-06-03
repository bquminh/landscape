package com.example.landscapedemo.api

data class RequestAddTask(
    val assigneeId: String,
    val description: String,
    val endDate: String,
    val grouptaskId: Int,
    val name: String,
    val priority: Int,
    val status: Int
)