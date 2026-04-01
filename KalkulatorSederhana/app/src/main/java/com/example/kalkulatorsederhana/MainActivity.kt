package com.example.kalkulatorsederhana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kalkulatorsederhana.ui.theme.KalkulatorSederhanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorSederhanaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Hasil: ") }

    fun calculate(operation: (Double, Double) -> Double, isDivision: Boolean = false) {
        val num1 = input1.toDoubleOrNull()
        val num2 = input2.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            result = "Error: Masukkan angka yang valid"
            return
        }

        if (isDivision && num2 == 0.0) {
            result = "Error: Tidak bisa dibagi dengan nol!"
            return
        }

        val calculation = operation(num1, num2)
        result = "Hasil: $calculation"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = input1,
            onValueChange = { input1 = it },
            label = { Text("Angka Pertama") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = input2,
            onValueChange = { input2 = it },
            label = { Text("Angka Kedua") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { calculate({ a, b -> a + b }) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text("Add")
            }
            Button(
                onClick = { calculate({ a, b -> a - b }) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text("Sub")
            }
            Button(
                onClick = { calculate({ a, b -> a * b }) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text("Mul")
            }
            Button(
                onClick = { calculate({ a, b -> a / b }, isDivision = true) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text("Div")
            }
        }

        Text(
            text = result,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    KalkulatorSederhanaTheme {
        CalculatorScreen()
    }
}
