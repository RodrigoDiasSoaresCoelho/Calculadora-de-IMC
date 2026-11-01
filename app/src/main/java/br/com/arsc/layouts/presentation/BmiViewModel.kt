package br.com.arsc.layouts.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import br.com.arsc.layouts.R
import br.com.arsc.layouts.domain.CalculateBmiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class BmiViewModel: ViewModel() {
    private val calculate = CalculateBmiUseCase()
    private val _uiState = MutableStateFlow(BmiUiState())

    val uiState: StateFlow<BmiUiState> = _uiState.asStateFlow()


    fun onWeightChange(value: String) {
        _uiState.update {
            it.copy(weight = value)
        }
    }

    fun onHeightChange(value: String) {
        _uiState.update {
            it.copy(height = value)
        }
    }

    fun calculate(){
        calculateBmi()
        calculateIdealWeight()
        getStatusImc()
    }

    fun calculateBmi() {
        val height = uiState.value.height.replace(",", ".").toDoubleOrNull() ?: 0.0
        val weight = uiState.value.weight.replace(",", ".").toLongOrNull() ?: 0

        val result = calculate.getBmi(height,weight)

        _uiState.update {
            it.copy(bmi = String.format(
                Locale.US,
                "%.1f",
                result
            ).replace(".", ","))
        }
    }

    fun calculateIdealWeight() {
        val height =  uiState.value.height
            .replace(",", ".")
            .toDoubleOrNull() ?: return

        _uiState.update {
            it.copy(idealWeight = "Seu peso ideal é entre: ${calculate.calculateIdealWeightRange(height)}")
        }
    }

    fun getStatusImc(){
        val imc =  uiState.value.bmi.replace(",", ".").toDoubleOrNull() ?: 0.0

        _uiState.update {
            it.copy(status = calculate.getStatusBmi(imc).toStringRes())
        }
    }

    @StringRes
    fun BmiStatus.toStringRes(): Int {
        return when (this) {
            BmiStatus.UNDERWEIGHT -> R.string.underweight
            BmiStatus.IDEAL_WEIGHT -> R.string.ideal_weight
            BmiStatus.OVERWEIGHT -> R.string.overweight
            BmiStatus.OBESITY_CLASS_I -> R.string.obesity_class_i
            BmiStatus.OBESITY_CLASS_II -> R.string.obesity_class_ii
            BmiStatus.OBESITY_CLASS_III -> R.string.obesity_class_iii
        }
    }
}