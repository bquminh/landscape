package com.example.landscapedemo.api

data class Project(
    var admins: List<String> = mutableListOf(),
    var archived: Boolean?,
    var createdTime: String?,
    var customer: String?,
    var description: String?,
    var endDate: String?,
    var id: Int,
    var lastUpdatedTime: String?,
    var lastUpdatedUser: String?,
    var members: List<String> = mutableListOf(),
    var name: String,
    var owner: String?,
    var pin: Boolean?,
    var startDate: String?,
    var status: Int?,
    var tasklate: Int?,
    var taskremain: Int?,
    var tasksum: Int?
){
    constructor(): this(mutableListOf(),true,"","","","",0,"","",
        mutableListOf(),"","",false,"",0,0,0,0)
}