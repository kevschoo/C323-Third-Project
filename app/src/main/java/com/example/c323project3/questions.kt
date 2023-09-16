package com.example.c323project3

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
import kotlin.random.Random

class questions : Fragment() {

    private lateinit var btnDone: Button
    private lateinit var mathQuestionTextView: TextView
    private lateinit var etUserAnswer: EditText

    /** Represents the difficulty level of the questions. */
    var difficulty = "easy"
    /** Represents the operation type of the questions. */
    var operation = "addition"

    /** Total number of questions. */
    var numberOfQuestions: Int  = 10
    /** Holds the correct answer of the current problem. */
    var currentAnswer: Int = 0

    /** Count of correctly answered questions. */
    var totalCorrectAnswers: Int = 0
    /** Count of wrongly answered questions. */
    var totalWrongAnswers: Int = 0

    /**
     * Initializes the questions screen fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        difficulty = welcomeArgs.fromBundle(requireArguments()).difficulty
        operation = welcomeArgs.fromBundle(requireArguments()).operation
        numberOfQuestions = welcomeArgs.fromBundle(requireArguments()).questionAmount
        mathQuestionTextView = view.findViewById(R.id.math_question)
        etUserAnswer = view.findViewById(R.id.et_user_answer)

        return view
    }

    /**
     * Called immediately after onCreateView.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        mathQuestionTextView.text = ""
        btnDone = view.findViewById(R.id.btn_done)

        btnDone.setOnClickListener {
            checkAnswer()
        }
        setRandomMathProblem()
    }

    /**
     * Generates a random math problem based on the operation and difficulty.
     * @param operation Type of mathematical operation (e.g., "addition", "subtraction").
     * @param difficulty Level of difficulty (e.g., "easy", "medium", "hard").
     * @return A string representation of the generated math problem.
     */
    private fun generateRandomProblem(operation: String, difficulty: String): String
    {
        val upperLimit: Int = when (difficulty)
        {
            "easy" -> 10
            "medium" -> 25
            "hard" -> 50
            else -> 10
        }

        var operand1 = Random.nextInt(upperLimit)
        var operand2 = Random.nextInt(upperLimit)

        return when (operation)
        {
            "addition" -> {
                currentAnswer = operand1 + operand2
                "$operand1 + $operand2"
            }
            "subtraction" -> {
                if (operand1 < operand2)
                {
                    val temp = operand1
                    operand1 = operand2
                    operand2 = temp
                }
                currentAnswer = operand1 - operand2
                "$operand1 - $operand2"
            }
            "multiplication" -> {
                currentAnswer = operand1 * operand2
                "$operand1 x $operand2"
            }
            "division" -> {
                while (operand2 == 0 ) {  // Ensure we don't divide by zero
                    operand2 = Random.nextInt(upperLimit)
                }
                currentAnswer = operand1 / operand2  // This will give an integer division result
                "$operand1 ÷ $operand2"
            }
            else ->
            {
                currentAnswer = 0
                "Unknown operation"
            }
        }
    }

    /**
     * Sets a new math problem in the TextView.
     */
    private fun setRandomMathProblem()
    {
        val problem = generateRandomProblem(operation, difficulty)
        mathQuestionTextView.text = problem
    }

    /**
     * Navigates the user to the results screen after completing all questions.
     */
    private fun navigateToResults()
    {
        val currentView = view ?: return
        val action = questionsDirections.actionQuestionsToResults(correctAnswers = this.totalCorrectAnswers, incorrectAnswers = this.totalWrongAnswers, questionAmount = this.numberOfQuestions)
        currentView.findNavController().navigate(action)
    }

    /**
     * Checks the user's input against the correct answer and updates the score.
     */
    private fun checkAnswer()
    {

        val userInput = etUserAnswer.text.toString().toIntOrNull()

        if (userInput != null)
        {
            if (userInput == currentAnswer)
            {
                totalCorrectAnswers++
            }
            else
            {
                totalWrongAnswers++
            }
            setRandomMathProblem()
            etUserAnswer.text.clear()

            numberOfQuestions = numberOfQuestions-1
        }

        if(numberOfQuestions <= 0)
        {
            navigateToResults()
        }

    }

}