package com.example.todolist

import androidx.lifecycle.ViewModel

class ToDoListViewModel : ViewModel() {

//    val tasks = mutableListOf<Task>()

//    init {
//        for(i in 0 until 100) {
//            val task = Task()
//            task.title = "Task #$i"
//            task.isDone = i % 2 == 0
//            tasks += task
//        }
//    }

    private val taskRepository = TaskRepository.get()
//    val tasks = taskRepository.getTasks()
    val taskListLiveData = taskRepository.getTasks()

    fun addTask(task:Task){
        taskRepository.addTask(task)
    }

}