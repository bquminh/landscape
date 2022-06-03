package com.example.landscapedemo.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class State(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "new State",
    var description: String = "",
    var projectId : String,
    var startDate: String = "Start date: ",
    var endDate: String = "End date: ",
    var status: String = "Not started",
    val createUser: String,
    val createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateUser : String? = null,
    var lastUpdateTime : String? = null
){
    constructor(): this("","","","","","","","")
}

