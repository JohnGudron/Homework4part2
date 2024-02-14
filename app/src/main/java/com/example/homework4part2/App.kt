package com.example.homework4part2

import android.app.Application
import com.example.homework4part2.model.UserService

class App: Application() {
    val userService = UserService()
}