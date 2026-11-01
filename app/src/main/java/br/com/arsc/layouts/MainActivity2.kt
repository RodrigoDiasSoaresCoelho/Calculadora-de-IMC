package br.com.arsc.layouts

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.arsc.layouts.ui.theme.LayoutsTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: Main2ViewModel = viewModel()

            LayoutsTheme {
                Surface (modifier = Modifier.fillMaxSize())
                {
                    IMCScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun IMCScreen(viewModel: Main2ViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .height(250.dp)
                .background(colorResource(R.color.yellow))
        ) {
            Image(
                painter = painterResource(R.drawable.balance),
                contentDescription = "Icon Balance",
                modifier = Modifier.padding(top = 16.dp)
                    .size(80.dp)
            )
            Text(
                text = "Calculadora de IMC",
                color = colorResource(R.color.white),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,

            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
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
                        text = "Seus dados",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.yellow),
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        value = viewModel.peso.collectAsState().value,
                        onValueChange = {
                            viewModel.onPesoChange(it)
                        },
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                            top = 16.dp
                        ),
                        label = {
                            Text(
                                text = "Peso em kg",
                                color = colorResource(R.color.yellow)
                            )
                        },
                        placeholder = { Text(text = "65,00kg") },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(R.color.yellow),
                            focusedBorderColor = colorResource(R.color.yellow),
                        ),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                    )

                    OutlinedTextField(
                        value = viewModel.altura.collectAsState().value,
                        onValueChange = {viewModel.onAlturaChange(it)},
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                top = 16.dp
                            ),
                        label = {
                            Text(
                                text = "Altura em centimetros",
                                color = colorResource(R.color.yellow)
                            )
                        },
                        placeholder = { Text(text = "160cm") },
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
                            viewModel.calcularIMC()
                            viewModel.calcularPesoIdeal()
                            viewModel.getStatusImc()
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
                            text = "Calcular",
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
                            text = "Resultado",
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
                                text = "Seu IMC é ",
                                fontSize = 18.sp,
                                color = colorResource(R.color.white),
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = viewModel.imc.collectAsState().value,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.white),
                                textAlign = TextAlign.Start
                            )
                        }

                        Text(
                            text = viewModel.status.collectAsState().value,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                            .fillMaxWidth().
                            padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Text(
                                text = viewModel.pesoIdeal.collectAsState().value,
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
fun IMCScreenPreview() {
    LayoutsTheme {
        IMCScreen(viewModel = viewModel())
    }
}