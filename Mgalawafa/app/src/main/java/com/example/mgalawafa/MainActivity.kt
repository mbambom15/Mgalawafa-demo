package com.example.mgalawafa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.mgalawafa.ui.theme.MgalawafaTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MgalawafaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MinibusTaxiFareCalculator()
                }
            }
        }
    }
}

@Composable
fun MinibusTaxiFareCalculator() {
    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
    var destination by remember { mutableStateOf(TextFieldValue("")) }
    var fare by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.padding(6.dp))
        Text("Mgalawafa", style = MaterialTheme.typography.headlineMedium)


        OutlinedTextField(
            value = startLocation,
            onValueChange = { startLocation = it },
            label = { Text("From (e.g., Soweto, JHB CBD)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("To (e.g., PTA CBD, Alexandra)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                isLoading = true
                fare = calculateMinibusFare(
                    start = startLocation.text.trim(),
                    destination = destination.text.trim()
                )
                isLoading = false
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Calculate Fare")
            }
        }

        if (fare.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estimated Fare:", style = MaterialTheme.typography.titleMedium)
                    Text("R$fare", style = MaterialTheme.typography.headlineSmall)
                    Text("(Standard minibus taxi fare)", style = MaterialTheme.typography.bodySmall)
                }
            }

            // Add to favorites button for frequent routes
            Button(
                onClick = { /* TODO: Save favorite route */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text("Save This Route")
            }
        }
    }
}

fun calculateMinibusFare(start: String, destination: String): String {
    // Simplified pricing model based on common South African minibus taxi routes
    // Prices are in South African Rand (ZAR)

    val routeFares = mapOf(
        // Johannesburg routes
        "soweto to jhb cbd" to 25,
        "jhb cbd to soweto" to 25,
        "alexandra to jhb cbd" to 20,
        "jhb cbd to alexandra" to 20,
        "dube to jhb cbd" to 18,
        "jhb cbd to dube" to 18,

        // Pretoria routes
        "mamelodi to pta cbd" to 22,
        "pta cbd to mamelodi" to 22,
        "soshanguve to pta cbd" to 25,
        "pta cbd to soshanguve" to 25,

        // Between cities
        "jhb cbd to pta cbd" to 45,
        "pta cbd to jhb cbd" to 45,

        // Durban routes
        "kwa mashu to durban cbd" to 20,
        "durban cbd to kwa mashu" to 20,
        "u mlazi to durban cbd" to 25,
        "durban cbd to u mlazi" to 25
    )

    // Try to match the route (case insensitive)
    val routeKey = "${start.lowercase()} to ${destination.lowercase()}"

    return routeFares[routeKey]?.toString() ?: run {
        // For unmatched routes, calculate based on approximate distance
        val distance = estimateDistance(start, destination)
        // Base fare of R10 + R2 per km (typical minibus pricing)
        val calculatedFare = 10 + (distance * 2)
        calculatedFare.roundToInt().toString()
    }
}

fun estimateDistance(start: String, destination: String): Double {
    // Very simplified distance estimation for unmatched routes
    return when {
        start.contains("jhb", ignoreCase = true) && destination.contains("pta", ignoreCase = true) -> 58.0
        start.contains("pta", ignoreCase = true) && destination.contains("jhb", ignoreCase = true) -> 58.0
        start.contains("cbd", ignoreCase = true) && !destination.contains("cbd", ignoreCase = true) -> 15.0
        !start.contains("cbd", ignoreCase = true) && destination.contains("cbd", ignoreCase = true) -> 15.0
        else -> 8.0 // Default for short local trips
    }
}