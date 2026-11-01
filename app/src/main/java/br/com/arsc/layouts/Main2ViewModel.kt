package br.com.arsc.layouts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Main2ViewModel: ViewModel() {
    private val _peso = MutableStateFlow("")
    private val _altura = MutableStateFlow("")
    private val _imc = MutableStateFlow("0,0")
    private val _pesoIdeal = MutableStateFlow("")
    private val _status = MutableStateFlow("")
    val peso: StateFlow<String> = _peso.asStateFlow()
    val altura: StateFlow<String> = _altura.asStateFlow()
    val imc: StateFlow<String> = _imc.asStateFlow()
    val pesoIdeal: StateFlow<String> = _pesoIdeal.asStateFlow()
    val status: StateFlow<String> = _status.asStateFlow()

    fun onPesoChange(value: String) {
        _peso.value = value
    }

    fun onAlturaChange(value: String) {
        _altura.value = value
    }

    fun calcularIMC() {
        val pesoValue = peso.value.replace(",", ".").toDoubleOrNull() ?: 0.0
        val alturaValue = altura.value.replace(",", ".").toDoubleOrNull() ?: 0.0

        if (pesoValue <= 0 || alturaValue <= 0) return

        val alturaEmMetros = if (alturaValue >= 3) {
            alturaValue / 100
        } else {
            alturaValue
        }

        val result = pesoValue / (alturaEmMetros * alturaEmMetros)

        _imc.value  = String.format(
            java.util.Locale.US,
            "%.1f",
            result
        ).replace(".", ",")
    }

    fun calcularPesoIdeal() {
        val alturaValue = altura.value
            .replace(",", ".")
            .toDoubleOrNull() ?: return

        // Aceita altura em centímetros ou metros
        val alturaEmMetros = if (alturaValue >= 3) {
            alturaValue / 100
        } else {
            alturaValue
        }

        if (alturaEmMetros <= 0) return

        val pesoMinimo = 18.5 * alturaEmMetros * alturaEmMetros
        val pesoMaximo = 24.9 * alturaEmMetros * alturaEmMetros

        val range = String.format(
            java.util.Locale("pt", "BR"),
            "%.2fkg a %.2fkg",
            pesoMinimo,
            pesoMaximo
        ).replace(".", ",")

        _pesoIdeal.value = "Seu peso ideal é entre: $range"
    }

    fun getStatusImc(){
        val imc = imc.value.replace(",", ".").toDoubleOrNull() ?: 0.0

        _status.value = if( imc < 18.5){
            "Abaixo do peso"
        }else if(imc >= 18.5 && imc < 25.0){
            "Peso Ideal"
        }else if(imc >= 25.0 && imc < 30.0){
            "Levemente acima do peso"
        }else if(imc >= 30.0 && imc < 35.0){
            "Obesidade Grau I"
        }else if(imc >= 35.0 && imc < 40.0){
            "Obesidade Grau II"
        }else{
            "Obesidade Grau III"
        }
    }
}