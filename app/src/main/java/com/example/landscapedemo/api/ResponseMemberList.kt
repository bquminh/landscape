package com.example.landscapedemo.api

data class ResponseMemberList(
    val code: Int,
    val `data`: List<Member>,
    val isError: Boolean,
    val isSuccess: Boolean,
    val message: String,
    val timeResponse: String
)