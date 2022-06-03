package com.example.landscapedemo.model

import java.util.*

data class Comment(
    val id : String = UUID.randomUUID().toString(),
    var stateId : String,
    var content : String,
    val createUser: String,
    val createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateTime : String? = null
) {
    constructor(): this("","","","","","")
}