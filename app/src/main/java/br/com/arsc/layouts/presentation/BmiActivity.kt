package br.com.arsc.layouts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.arsc.layouts.R
import br.com.arsc.layouts.ui.theme.LayoutsTheme
import br.com.arsc.layouts.util.formatWeight
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

class BmiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: BmiViewModel = viewModel()

            LayoutsTheme {
                Surface (modifier = Modifier.fillMaxSize())
                {
                    BmiScreenRoute(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun BmiScreenRoute(viewModel: BmiViewModel){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BmiScreen(
        uiState = uiState,
        onWeightChange = viewModel::onWeightChange,
        onHeightChange = viewModel::onHeightChange,
        onCalculate = viewModel::calculate
    )
}

@Composable
fun BmiScreen(uiState: BmiUiState,
              onWeightChange: (String) -> Unit,
              onHeightChange: (String) -> Unit,
              onCalculate: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(colorResource(R.color.yellow))
        ) {
            Image(
                painter = painterResource(R.drawable.balance),
                contentDescription = "Icon Balance",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(80.dp)
            )
            Text(
                text = stringResource(R.string.bmi_calculator),
                color = colorResource(R.color.white),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,

            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .offset(y = 140.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.white)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.your_data),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.yellow),
                        textAlign = TextAlign.Center
                    )

                    val weightText = formatWeight(uiState.weight)

                    var weightFieldValue by remember {
                        mutableStateOf(
                            TextFieldValue(
                                text = weightText,
                                selection = TextRange(weightText.length)
                            )
                        )
                    }

                    OutlinedTextField(
                        value = weightFieldValue,
                        onValueChange = {input ->

                            val digits = input.text
                                .filter { it.isDigit() }
                                .take(5)

                            onWeightChange(digits)

                            val formatted = formatWeight(digits)

                            weightFieldValue = TextFieldValue(
                                text = formatted,
                                selection = TextRange(formatted.length)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp
                            ),
                        label = {
                            Text(
                                text = stringResource(R.string.weight_in_kg),
                                color = colorResource(R.color.yellow)
                            )
                        },
                        placeholder = { Text(text = stringResource(R.string.ex_weight)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.yellow),
                            focusedBorderColor = colorResource(R.color.yellow),
                        ),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                    )

                    var heightFieldValue by remember {
                        mutableStateOf(
                            TextFieldValue(
                                text = uiState.height,
                                selection = TextRange(uiState.height.length)
                            )
                        )
                    }
                    OutlinedTextField(
                        value = heightFieldValue,
                        onValueChange = {  input ->

                            val digits = input.text
                                .filter { it.isDigit() }
                                .take(3)

                            onHeightChange(digits)

                            heightFieldValue = TextFieldValue(
                                text = digits,
                                selection = TextRange(digits.length)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp
                            ),
                        label = {
                            Text(
                                text = stringResource(R.string.height_in_centimeters),
                                color = colorResource(R.color.yellow)
                            )
                        },
                        placeholder = { Text(text = stringResource(R.string.ex_height)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.yellow),
                            focusedBorderColor = colorResource(R.color.yellow),
                        ),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                    )

                    val keyboardController = LocalSoftwareKeyboardController.current

                    Button (
                        onClick = {
                            onCalculate()
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.yellow),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.calculate),
                            fontSize = 18.sp,
                        )
                    }

                }

            }

            Card(
                modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = 64.dp
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.yellow)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start

                    ) {
                        Text(
                            text = stringResource(R.string.result),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Start
                        )
                        Row(
                            modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.your_bmi_is),
                                fontSize = 18.sp,
                                color = colorResource(R.color.white),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = uiState.bmi,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.white),
                                textAlign = TextAlign.Start
                            )
                        }

                        Text(
                            text = stringResource(uiState.status),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Text(
                                text = uiState.idealWeight,
                                fontSize = 18.sp,
                                color = colorResource(R.color.white),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BmiScreenPreview() {
    val previewState = BmiUiState(
        weight = "75,00",
        height = "175",
        bmi = "24,5",
        status = R.string.status,
        idealWeight = "Seu peso de referência está entre 56,66kg e 76,26kg"
    )

    LayoutsTheme {
        BmiScreen(
            uiState = previewState,
            onWeightChange = {},
            onHeightChange = {},
            onCalculate = {}
        )
    }
}