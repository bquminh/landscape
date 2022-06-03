package com.example.landscapedemo.model

import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    var projectId : String,
    var title : String,
    var content : String = "",
    val createUser: String,
    val createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateUser : String? = null,
    var lastUpdateTime : String? = null
){
    constructor() : this("","","","","","")
}
