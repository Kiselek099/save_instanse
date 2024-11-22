package com.example.users_directory

data class User (val name:String,val age:Int){
    override fun toString(): String {
        return "Имя : $name, возраст : $age"
    }
}