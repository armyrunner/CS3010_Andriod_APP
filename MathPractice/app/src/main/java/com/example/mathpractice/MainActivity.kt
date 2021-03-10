package com.example.mathpractice


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newWelcome = findViewById<EditText>(R.id.enter_name)
        val newBegin = findViewById<Button>(R.id.begin_button)
        val newScreen = findViewById<TextView>(R.id.welcome)

        newBegin.setOnClickListener {

            var name = newWelcome.text.toString()
            val intent = Intent(this@MainActivity,beginquiz::class.java)
            intent.putExtra("Name", name)
            startActivity(intent)
        }
    }

}

