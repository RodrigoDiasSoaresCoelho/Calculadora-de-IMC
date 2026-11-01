package br.com.arsc.layouts.presentation

import br.com.arsc.layouts.R


data class BmiUiState (
    val weight: String = "",
    val height: String = "",
    val bmi: String = "0,0",
    val idealWeight: String = "",
    val status: Int = R.string.status
)