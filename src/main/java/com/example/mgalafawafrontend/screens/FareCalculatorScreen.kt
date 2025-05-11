package com.example.mgalafawafrontend.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mgalafawafrontend.Screen

@Composable
fun FareCalculatorScreen(navController: NavController) {
    var pickup by remember { mutableStateOf("") }
    var dropOff by remember { mutableStateOf("") }
    var fare by remember { mutableStateOf<Double?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = pickup,
            onValueChange = { pickup = it },
            label = { Text("Pickup Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dropOff,
            onValueChange = { dropOff = it },
            label = { Text("Drop-off Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { fare = 25.0 }) {
            Text("Calculate Fare")
        }

        fare?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Estimated Fare: R$it", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.Payment.route) }) {
                Text("Proceed to Payment")
            }
        }
    }
}