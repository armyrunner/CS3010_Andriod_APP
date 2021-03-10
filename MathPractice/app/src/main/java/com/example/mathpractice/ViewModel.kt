package com.example.mathpractice

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"ViewModel instance about to be destroyed")

    }
    var currentIndex = 0

    private val questionBank = listOf(

        Question(R.string.question_1,"einstein"),
        Question(R.string.question_2,"newton"),
        Question(R.string.question_3,"laplace"),
        Question(R.string.question_4,"euler")
    )

    val currentQuestionAnswer: String
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    val mathBankSize:Int
        get() = questionBank.size
}