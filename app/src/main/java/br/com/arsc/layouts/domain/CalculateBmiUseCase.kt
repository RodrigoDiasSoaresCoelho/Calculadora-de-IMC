package br.com.arsc.layouts.domain

import br.com.arsc.layouts.presentation.BmiStatus
import java.util.Locale

class CalculateBmiUseCase {
    fun getBmi(height: Double, weight: Long):Double{
        if (weight <= 0 || height <= 0) return 0.0
        val newWeight = weight.div(100.0) ?: 0.0

        val meters = convertHeightToMeters(height)

        return newWeight / (meters * meters)
    }

    fun calculateIdealWeightRange(height: Double):String{
        val meters = convertHeightToMeters(height)

        if (meters <= 0) return ""

        val minHeight = 18.5 * meters * meters
        val maxHeight = 24.9 * meters * meters

        return String.format(
            Locale("pt", "BR"),
            "%.2fkg a %.2fkg",
            minHeight,
            maxHeight
        )
    }

    fun getStatusBmi(bmi:Double): BmiStatus {
        return when {
            bmi < 18.5 -> BmiStatus.UNDERWEIGHT
            bmi < 25.0 -> BmiStatus.IDEAL_WEIGHT
            bmi < 30.0 -> BmiStatus.OVERWEIGHT
            bmi < 35.0 -> BmiStatus.OBESITY_CLASS_I
            bmi < 40.0 -> BmiStatus.OBESITY_CLASS_II
            else -> BmiStatus.OBESITY_CLASS_III
        }
    }

    private fun convertHeightToMeters(height: Double): Double {
        return height / 100
    }
}