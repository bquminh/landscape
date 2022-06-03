package com.example.landscapedemo.api

data class Note(
    var archived: Boolean,
    var content: String,
    var createdTime: String,
    var createdUser: String,
    var id: Int,
    var lastUpdatedTime: String,
    var lastUpdatedUser: String,
    var pin: Boolean,
    var projectId: Int
)