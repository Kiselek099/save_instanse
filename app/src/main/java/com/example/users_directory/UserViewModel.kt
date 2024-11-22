package com.example.users_directory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userList = MutableLiveData<MutableList<User>>().apply {
        value = mutableListOf()
    }

}