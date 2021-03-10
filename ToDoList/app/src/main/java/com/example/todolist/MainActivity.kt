package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

private const val TAG = "MainActivity"


class MainActivity : AppCompatActivity(),ToDoListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.task_fragment_list)


        if(currentFragment == null){
            val fragment = ToDoListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.task_fragment_list,fragment)
                .commit()
        }


    }

    override fun onTaskSelected(taskId: UUID){
//        Log.d(TAG,"MainActivity.onTaskSelected: $taskId")
//        val fragment = TaskFragment()
        val fragment = TaskFragment.newInstance(taskId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.task_fragment_list,fragment)
            .addToBackStack(null)
            .commit()
    }
}
