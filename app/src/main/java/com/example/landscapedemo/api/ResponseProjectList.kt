package com.example.landscapedemo.api

data class ResponseProjectList(
    val code: Int,
    val `data`: List<Project>,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)