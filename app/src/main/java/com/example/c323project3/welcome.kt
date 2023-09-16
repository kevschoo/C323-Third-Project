package com.example.c323project3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
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

    /** Represents the difficulty level selected by the user. */
    var difficulty = "easy"
    /** Represents the math operation selected by the user. */
    var operation = "addition"
    /** Represents the number of questions chosen by the user. */
    var numberOfQuestions = 10

    /**
     * Initializes the welcome screen fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

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

        btnStart.setOnClickListener {
            navigateToQuestionsFragment()
            val action = welcomeDirections.actionWelcomeToQuestions(numberOfQuestions,0,0,operation,difficulty)
            view.findNavController().navigate((action))
        }

        buttonMinus.setOnClickListener {
            val currentNum = editTextNumberOfQuestions.text.toString().toInt()
            editTextNumberOfQuestions.setText((currentNum - 1).toString())
        }

        buttonPlus.setOnClickListener {
            val currentNum = editTextNumberOfQuestions.text.toString().toInt()
            editTextNumberOfQuestions.setText((currentNum + 1).toString())
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