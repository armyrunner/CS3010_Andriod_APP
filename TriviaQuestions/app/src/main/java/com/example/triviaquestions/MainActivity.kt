package com.example.triviaquestions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var triviabtn: Button
    private lateinit var drawbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        triviabtn = findViewById(R.id.triviabtn)

        drawbtn = findViewById(R.id.drawbtn)

        triviabtn.setOnClickListener{ view: View ->
            val intent = Intent(this,TriviaActivity::class.java)
            startActivity(intent)
        }

        drawbtn.setOnClickListener{

            val intent = Intent(this,CanvasActivity::class.java)
            startActivity(intent)

        }
    }

}
