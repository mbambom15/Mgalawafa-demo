package com.example.mgalafawafrontend.screens

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mgalafawafrontend.Screen
import java.net.URLEncoder

@Composable
fun PaymentScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Choose Payment Method", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(24.dp))

        // Simulating NFC Payment - This should later be integrated with an NFC library
        Button(onClick = {
            // Here you should handle NFC payment logic. For now, we simulate a successful payment.
            navController.navigate(Screen.Receipt.route)
        }) {
            Text("Pay with NFC")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // QR Code Payment - Generate a payment URL and navigate to the WebView screen
        Button(onClick = {
            val paymentUrl = "https://your-server.com/api/ozow-payment-url?amount=25.0&reference=MGALA123"
            // Passing the payment URL to WebView screen for processing the payment
            navController.navigate("webview/${URLEncoder.encode(paymentUrl, "UTF-8")}")
        }) {
            Text("Pay with QR Code")
        }
    }
}
