package br.com.arsc.layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arsc.layouts.ui.theme.LayoutsTheme
import br.com.arsc.layouts.ui.theme.Playfair

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LayoutsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LayoutScreen()
                }
            }
        }
    }

    @Composable
    fun LayoutScreen() {
        var text by remember { mutableStateOf("") }
        var yearsOld by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var checkBoxJava by remember { mutableStateOf(false) }
        var checkBoxKotlin by remember { mutableStateOf(false) }
        var checkBoxCSharp by remember { mutableStateOf(false) }
        var color by remember { mutableStateOf(Color.White) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(color),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.End),
                painter = painterResource(R.drawable.settings),
                contentDescription = "Icon Settings"
            )
            Text(
                text = "Hello World",
                fontFamily = Playfair
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Digite seu nome") },
                placeholder = { Text(text = "Digite o nome e sobrenome") },
                trailingIcon = { },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.person),
                        contentDescription = "Icon Settings"
                    )
                },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = yearsOld,
                onValueChange = { yearsOld = it },
                label = { Text(text = "Digite sua idade") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = "Icon Settings"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Digite seu E-mail") },
                placeholder = { Text(text = "@emial.com") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                shape = RoundedCornerShape(32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Checkbox(
                    checked = checkBoxJava,
                    onCheckedChange = {
                        checkBoxJava = it
                    },
                    enabled = true,
                    modifier = Modifier
                        .height(32.dp)
                )
                Text(text = "Java")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkBoxKotlin,
                    onCheckedChange = {
                        checkBoxKotlin = it
                    },
                    enabled = true,
                    modifier = Modifier
                        .height(32.dp)
                )
                Text(text = "Kotlin")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkBoxCSharp,
                    onCheckedChange = {
                        checkBoxCSharp = it
                    },
                    enabled = true,
                    modifier = Modifier
                        .height(32.dp)
                )
                Text(text = "C#")
            }

            var langSelected by remember { mutableStateOf(0) }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                RadioButton(
                    selected = langSelected == 0,
                    onClick = {
                        langSelected = 0
                    })
                Text(text = "Windows")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = langSelected == 1,
                    onClick = {
                        langSelected = 1
                    })
                Text(text = "Linux")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = langSelected == 2,
                    onClick = {
                        langSelected = 2
                    })
                Text(text = "Mac")
            }
            Row() {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Cadastrar")
                }

                OutlinedButton (
                    onClick = {
                        color = if(color == Color.White)
                            Color.Yellow
                        else
                            Color.White

                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Color")
                }
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun LayoutScreenPreview() {
        LayoutScreen()
    }
}

