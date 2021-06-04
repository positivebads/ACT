package com.automatedcontacttracing.act.basicinfo.model

data class BasicInfo(
    val lastName : String,
    val firstName : String,
    val middleInitial : String,
    val age : String,
    val gender : String,
    val emailAddress : String,
    val contactNumber : String,
    val address : String,
    val temperature : String
)