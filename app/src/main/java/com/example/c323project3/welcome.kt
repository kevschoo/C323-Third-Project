package com.example.c323project3

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class welcome : Fragment() {

    private lateinit var btnStart: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonPlus: Button
    private lateinit var editTextNumberOfQuestions: EditText
    private lateinit var radioEasy: RadioButton
    private lateinit var radioMedium: RadioButton
    private lateinit var radioHard: RadioButton
    private lateinit var radioAddition: RadioButton
    private lateinit var radioSubtraction: RadioButton
    private lateinit var radioMultiplication: RadioButton
    private lateinit var radioDivision: RadioButton

    private lateinit var feedbackTextView: TextView
    /** Represents the difficulty level selected by the user. */
    var difficulty = "easy"
    /** Represents the math operation selected by the user. */
    var operation = "addition"
    /** Represents the number of questions chosen by the user. */


    var prevDifficulty = ""
    var prevOperation = ""
    var numberOfQuestions = 10

    var prevNumberOfQuestions = 0
    var correctAnswers = 0
    var incorrectAnswers = 0
    /**
     * Initializes the welcome screen fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        correctAnswers = welcomeArgs.fromBundle(requireArguments()).correct
        incorrectAnswers = welcomeArgs.fromBundle(requireArguments()).incorrect
        prevNumberOfQuestions = correctAnswers + incorrectAnswers
        prevOperation = welcomeArgs.fromBundle(requireArguments()).operation
        prevDifficulty = welcomeArgs.fromBundle(requireArguments()).difficulty

        return view
    }

    /**
     * Called immediately after onCreateView.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        btnStart = view.findViewById(R.id.btn_start)
        buttonMinus = view.findViewById(R.id.button_minus)
        buttonPlus = view.findViewById(R.id.button_plus)
        editTextNumberOfQuestions = view.findViewById(R.id.edittext_number_of_questions)
        radioEasy = view.findViewById(R.id.radio_easy)
        radioMedium = view.findViewById(R.id.radio_medium)
        radioHard = view.findViewById(R.id.radio_hard)
        radioAddition = view.findViewById(R.id.radio_addition)
        radioSubtraction = view.findViewById(R.id.radio_subtraction)
        radioMultiplication = view.findViewById(R.id.radio_multiplication)
        radioDivision = view.findViewById(R.id.radio_division)
        feedbackTextView = view.findViewById(R.id.feedback)

        btnStart.setOnClickListener {

            navigateToQuestionsFragment()
            if (numberOfQuestions > 0)
            {
                val action = welcomeDirections.actionWelcomeToQuestions(questionAmount = this.numberOfQuestions, 0, 0, operation, difficulty)
                view.findNavController().navigate((action))
            }
        }

        buttonMinus.setOnClickListener {
            val currentNum = editTextNumberOfQuestions.text.toString().toInt()
            editTextNumberOfQuestions.setText((currentNum - 1).toString())
        }

        buttonPlus.setOnClickListener {
            val currentNum = editTextNumberOfQuestions.text.toString().toInt()
            editTextNumberOfQuestions.setText((currentNum + 1).toString())
        }


        if ( correctAnswers + incorrectAnswers > 0)
        {
            val score = (correctAnswers.toFloat() / (correctAnswers + incorrectAnswers) * 100).toInt()
            //feedbackTextView.text = "$correctAnswers $incorrectAnswers $numberOfQuestions $prevOperation $prevDifficulty"
            if (score >= 80 && prevNumberOfQuestions > 0)
            {
                feedbackTextView.text = "You got $correctAnswers correct out of $prevNumberOfQuestions $prevDifficulty $prevOperation questions. Good Job. Score: $score%"
                feedbackTextView.visibility = View.VISIBLE
                feedbackTextView.setTextColor(Color.BLACK)
            }
            else if ( prevNumberOfQuestions > 0)
            {
                feedbackTextView.text = "You got $correctAnswers correct out of $prevNumberOfQuestions $prevDifficulty $prevOperation questions. You need to try harder. Score: $score%"
                feedbackTextView.visibility = View.VISIBLE
                feedbackTextView.setTextColor(Color.RED)
            }
            else
            {
                feedbackTextView.text = "Select a Difficulty, Operation, and Number of Questions! "
            }
        }
        else
        {
            feedbackTextView.text = "Select a Difficulty, Operation, and Number of Questions! "
        }
    }

    /**
     * Navigates to the questions fragment with user's preferences.
     */
    private fun navigateToQuestionsFragment()
    {
        // Fetching the user's selection from UI elements
        difficulty = when
        {
            radioEasy.isChecked -> "easy"
            radioMedium.isChecked -> "medium"
            radioHard.isChecked -> "hard"
            else -> "easy"
        }

        operation = when
        {
            radioAddition.isChecked -> "addition"
            radioSubtraction.isChecked -> "subtraction"
            radioMultiplication.isChecked -> "multiplication"
            radioDivision.isChecked -> "division"
            else -> "addition"
        }

        numberOfQuestions = editTextNumberOfQuestions.text.toString().toInt()

    }
}