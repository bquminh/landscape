package com.example.landscapedemo.api

data class ResponseUpdateProject(
    val code: Int,
    val project: Project,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)