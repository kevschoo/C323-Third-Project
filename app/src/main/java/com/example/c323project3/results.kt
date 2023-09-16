package com.example.c323project3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class results : Fragment() {

    private lateinit var btnDone: Button
    private lateinit var resultsTextView: TextView

    /** Represents the difficulty level of the questions. */
    var difficulty = "easy"
    /** Represents the operation type of the questions. */
    var operation = "addition"

    /** Total number of questions. */
    var numberOfQuestions: Int  = 10
    /** Total number of correctly answered questions. */
    var totalCorrectAnswers: Int = 0
    /** Total number of wrongly answered questions. */
    var totalWrongAnswers: Int = 0

    /**
     * Initializes the results screen fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)
        totalCorrectAnswers = questionsArgs.fromBundle(requireArguments()).correctAnswers
        totalWrongAnswers = questionsArgs.fromBundle(requireArguments()).incorrectAnswers

        resultsTextView = view.findViewById(R.id.result)
        btnDone = view.findViewById(R.id.btn_try_again)
        return view
    }

    /**
     * Called immediately after onCreateView.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        btnDone.setOnClickListener {
            val action = resultsDirections.actionResultsToWelcome()
            view.findNavController().navigate((action))
        }

        showResults()
    }

    /**
     * Sets the results view with user's performance metrics.
     */
    private fun showResults()
    {
        numberOfQuestions = totalCorrectAnswers+totalWrongAnswers
        val correctText = "Correct: $totalCorrectAnswers"
        val incorrectText = "Incorrect: $totalWrongAnswers"
        val scoreText = "Score: $totalCorrectAnswers/$numberOfQuestions"

        resultsTextView.text = "$correctText\n$incorrectText\n$scoreText"
    }

}