package com.example.landscapedemo.api

data class Task(
    var assigneeId: String,
    var createdTime: String,
    var createdUser: String,
    var description: String,
    var endDate: String,
    var grouptaskId: Int,
    var id: Int,
    var name: String,
    var priority: Int,
    var stageId: Int,
    var status: Int
)