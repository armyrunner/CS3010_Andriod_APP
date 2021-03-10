package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var date: Date = Date(),
                var isDone: Boolean = false,
                var description: String = "")