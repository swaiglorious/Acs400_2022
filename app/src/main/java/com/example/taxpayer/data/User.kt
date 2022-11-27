package com.example.taxpayer.data

data class User(
    val id:Int,
    val name : String,
    val tin : String,
    val nida : String,
    val issueDate : String,
    val physicalLocation: String,
    val taxOffice: String,
    val street: String,
    val phoneNumber:String
    )