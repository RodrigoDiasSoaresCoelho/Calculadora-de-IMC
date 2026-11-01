package br.com.arsc.layouts

import br.com.arsc.layouts.domain.CalculateBmiUseCase
import br.com.arsc.layouts.presentation.BmiStatus
import org.junit.Test

import org.junit.Assert.*

class CalculateBmiUseCaseTest {
    private val useCase = CalculateBmiUseCase()

    @Test
    fun `should calculate bmi correctly`() {

        val weight = 7000L
        val height = 175.0

        val result = useCase.getBmi(height, weight)

        assertEquals(22.86, result, 0.01)
    }

    @Test
    fun `should return zero when height is invalid`() {

        // Arrange
        val weight = 7000L
        val height = 0.0

        // Act
        val result = useCase.getBmi(height, weight)

        // Assert
        assertEquals(0.0, result, 0.0)
    }

    @Test
    fun `should calculate reference weight range`() {

        // Arrange
        val height = 175.0

        // Act
        val result = useCase.calculateIdealWeightRange(height)

        // Assert
        assertEquals("56,66kg a 76,26kg", result)
    }

    @Test
    fun `should return normal weight status`() {

        // Arrange
        val bmi = 22.86

        // Act
        val result = useCase.getStatusBmi(bmi)

        // Assert
        assertEquals(BmiStatus.IDEAL_WEIGHT, result)
    }

    @Test
    fun `should return obesity grade one`() {

        // Arrange
        val bmi = 32.0

        // Act
        val result = useCase.getStatusBmi(bmi)

        // Assert
        assertEquals(BmiStatus.OBESITY_CLASS_I, result)
    }
}