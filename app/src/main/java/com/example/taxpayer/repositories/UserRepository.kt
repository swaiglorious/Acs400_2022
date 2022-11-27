package com.example.taxpayer.repositories

import android.app.Application
import com.example.taxpayer.data.DataManager
import com.example.taxpayer.data.User

class UserRepository() {
    val allUsers : List<User> = DataManager.allUsers

    fun addUser(user: User){
        DataManager.allUsers.add(user)
    }

//    fun getUser(id:Int) : User{
//        val results = allUsers.find { user -> user.id == id }
//        return results[0]
//    }
//
    fun getUserPhoneWithNida(nida:String) : String{
        val results = allUsers.find { user -> user.nida == nida }
        val phone : String = results!!.phoneNumber
        return phone
    }
    fun getUserWithNida(nida:String) : User{
        val results = allUsers.find { user -> user.nida == nida }
        val user = results!!
        return results
    }
}