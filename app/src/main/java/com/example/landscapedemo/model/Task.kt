package com.example.landscapedemo.model

import java.util.*

data class Task (
    val id: String = UUID.randomUUID().toString(),
    var name: String = "new Task",
    var description : String = "",
    var assignee : String? = null,
    var priority : String = "Low",
    var status : String = "Open",
    var label : MutableList<String> = mutableListOf(),
    var deadline: String? = null,
    val createUser: String,
    val createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateUser : String? = null,
    var lastUpdateTime : String? = null
    ){
}