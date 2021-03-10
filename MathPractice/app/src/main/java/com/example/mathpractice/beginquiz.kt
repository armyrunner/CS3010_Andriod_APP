package com.example.mathpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProviders


class beginquiz() : AppCompatActivity() {

    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var showResultsButton: Button
    private lateinit var moveforward: Button
    private lateinit var questionTextView: TextView

    var correctAnswers = 0
    var incorrectAnswers = 0

    private val quizViewModel: QuizViewModel by lazy{
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginquiz)

        btnOne = findViewById(R.id.btn_one)
        btnTwo = findViewById(R.id.btn_two)
        btnThree = findViewById(R.id.btn_three)
        btnFour = findViewById(R.id.btn_four)

        showResultsButton = findViewById(R.id.results_button)
        moveforward = findViewById((R.id.btn_next))
        questionTextView = findViewById(R.id.mathematician_question)


        showResultsButton.isEnabled = false

        val name = intent.getStringExtra("Name")
        val nameReslut = findViewById<TextView>(R.id.newName)
        nameReslut.text = "Welcome "+name+" "

        btnOne.setOnClickListener{
            goodanswer("einstein")
            badanswer("einstein")
            disableButton("einsterin",btnOne,btnTwo,btnThree,btnFour)
        }

        btnTwo.setOnClickListener {
            goodanswer("newton")
            badanswer("newton")
            disableButton("newton",btnOne,btnTwo,btnThree,btnFour)

        }

        btnThree.setOnClickListener {
            goodanswer("laplace")
            badanswer("laplace")
            disableButton("laplace",btnOne,btnTwo,btnThree,btnFour)
        }

        btnFour.setOnClickListener {
            goodanswer("euler")
            badanswer("euler")
            disableButton("euler",btnOne,btnTwo,btnThree,btnFour)
        }

        showResultsButton.setOnClickListener {
            val ans = "Your Score is: " + correctAnswers.toString()
            questionTextView.setText(ans)
        }


        moveforward.setOnClickListener {
            quizViewModel.moveToNext()
            updatequestion()
            completed()
        }

        updatequestion()
        completed()
    }

    private fun updatequestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        activateButtons(btnOne,btnTwo,btnThree,btnFour)

    }
    private fun goodanswer(input: String){
        if(input == quizViewModel.currentQuestionAnswer){
            Toast.makeText(this,R.string.correct_toast,LENGTH_SHORT).show()
            this.correctAnswers += 1

        }
    }

    private fun badanswer(input: String){
        if(input != quizViewModel.currentQuestionAnswer){
            Toast.makeText(this,R.string.incorrect_toast,LENGTH_SHORT).show()
            this.correctAnswers += 1

        }
    }

    private fun completed(){
        if(correctAnswers + incorrectAnswers == quizViewModel.mathBankSize){
            deactivateNxtBtn(moveforward)
            deactivateAllBtn(btnOne,btnTwo,btnThree,btnFour)
            showResultsButton.isEnabled = true
            questionTextView.setText(R.string.show_result)
        }

    }


    private fun disableButton(input: String, btn1: Button, btn2:Button, btn3:Button, btn4:Button){
        val calculateAnswer = quizViewModel.currentQuestionAnswer
        if(input == calculateAnswer){
            btn1.isEnabled = false
            btn2.isEnabled = false
            btn3.isEnabled = false
            btn4.isEnabled = false
        }
        else{
            btn1.isEnabled = false
            btn2.isEnabled = false
            btn3.isEnabled = false
            btn4.isEnabled = false

        }
    }

    private fun deactivateAllBtn(btn1: Button, btn2:Button, btn3:Button, btn4:Button){

        btn1.isEnabled = false
        btn2.isEnabled = false
        btn3.isEnabled = false
        btn4.isEnabled = false

    }

    private fun deactivateNxtBtn(btn1:Button){
        btn1.isEnabled = true
    }

    private fun activateButtons(btn1: Button, btn2:Button, btn3:Button, btn4:Button) {
        btn1.isEnabled = true
        btn2.isEnabled = true
        btn3.isEnabled = true
        btn4.isEnabled = true
    }
}
