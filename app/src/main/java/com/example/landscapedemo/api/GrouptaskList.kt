package com.example.landscapedemo.api

data class GrouptaskList(
    val `data`: List<Grouptask>,
    val pageSize: Int,
    val totalCount: Int
)