package com.example.todolist

import android.app.Application

class ToDoIntentApplication : Application() {

    override fun onCreate(){
        super.onCreate()
        TaskRepository.initialize(this)
    }

}