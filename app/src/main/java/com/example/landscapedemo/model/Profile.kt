package com.example.landscapedemo.model

import java.util.*

data class Profile(
    val id : String,
    var username : String,
    var password : String,
    var name : String? = null,
    var role : String? = null,
    var dob : String? = null,
    var email : String? = null,
    var mobile : String? = null,
    var branch : String? = null,
    var department : String? = null,
    var mainPosition: String? = null,
    var status: String? = null,
    val createTime: String = Calendar.getInstance().time.toString(),
    var lastUpdateTime : String? = null

    ){
    constructor() : this("","","","","","","","","","","","","",""
    )
}