package com.example.landscapedemo.api

data class Data( //Data
    val `data`: List<State>,
    val pageSize: Int?,
    val totalCount: Int?
)