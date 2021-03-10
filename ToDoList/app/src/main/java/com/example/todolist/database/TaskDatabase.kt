package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.todolist.Task

@Database(entities = [Task::class],version=1,exportSchema = false)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDatabase: RoomDatabase(){
    abstract fun TaskDao() :TaskDao

}