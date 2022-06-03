package com.example.landscapedemo.api

data class Grouptask(
    var createdTime: String,
    var createdUser: String,
    var description: String,
    var endDate: String,
    var id: Int,
    var lastUpdatedTime: String,
    var lastUpdatedUser: String,
    var name: String,
    var stageId: Int,
    var startDate: String,
    var status: Int
){
    constructor(): this("","","","",0,"","","",0,"",0)
}