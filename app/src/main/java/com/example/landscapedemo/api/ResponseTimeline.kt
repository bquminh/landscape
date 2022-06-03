package com.example.landscapedemo.api

data class ResponseTimeline(
    val code: Int,
    val `data`: List<GrouptaskTimeline>,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)