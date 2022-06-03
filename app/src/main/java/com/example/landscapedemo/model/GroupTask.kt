package com.example.landscapedemo.model

import java.util.*

class GroupTask(
    val id : String = UUID.randomUUID().toString(),
    var projectId : String,
    var stateId : String,
    var status : Int = 0,
    var name : String = "new Group Task",
    var description: String = "",
    var startDate: String = "",
    var endDate: String = "",
    val createUser: String,
    val createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateUser : String? = null,
    var lastUpdateTime : String? = null
) {
    constructor(): this("","","",0,"","","","","","","","")

}