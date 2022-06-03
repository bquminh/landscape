package com.example.landscapedemo.model

import java.io.Serializable
import java.util.*

data class Project(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var description: String,
    var notes: MutableList<Note> = mutableListOf(),
    var owner: String = "",
    var admins: MutableList<String> = mutableListOf(),
    var members: MutableList<String> = mutableListOf(),
    var states: MutableList<State> = mutableListOf(),
    var status: String = "Not Started",
    var startDate: String,
    var endDate: String,
    var createUser: String,
    var createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateUser : String? = null,
    var lastUpdateTime : String? = null
):Serializable{
    constructor() : this(
        createUser      = "",
        createTime      = "",
        id              = "",
        name            = "",
        description     = "",
        notes           = mutableListOf(),
        owner           = "",
        admins          = mutableListOf(),
        members         = mutableListOf(),
        states          = mutableListOf(),
        status          = "Not Started",
        startDate       = "",
        endDate         = "",
        lastUpdateUser  = null,
        lastUpdateTime  = null)

}