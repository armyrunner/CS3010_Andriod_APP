package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.AbsSavedState
import android.view.Gravity

import android.widget.Button
import android.widget.Toast
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "GeoQuiz"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy{
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)


        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0)?: 0
        quizViewModel.currentIndex = currentIndex


        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_view_text)
        cheatButton = findViewById(R.id.cheat_button)


        val text1 = "Correct!"
        val text2 = "Incorrect!"
        val duration = Toast.LENGTH_SHORT

        trueButton.setOnClickListener { view: View ->
            val toast = Toast.makeText(applicationContext,text1,duration)
            toast.show()
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            val toast = Toast.makeText(applicationContext,text2,duration)
            toast.show()
            checkAnswer(false)
        }

        nextButton.setOnClickListener { view: View ->
            quizViewModel.moveToNext()
            updateQuestion()

        }
        cheatButton.setOnClickListener { view: View ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity,answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false) ?: false
        }
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG,"onResume() called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onSaveInstanceState(onSavedInstanceState: Bundle) {
        super.onSaveInstanceState(onSavedInstanceState)
        onSavedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG,"onStop() called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }


    private fun updateQuestion (){

        val questionTextResId =  quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when{
            quizViewModel.isCheater -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else->R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT)
            .show()
    }


}
