package com.example.mgalafawafrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mgalafawafrontend.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MgalafawaApp()
        }
    }
}

@Composable
fun MgalafawaApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.FareCalculator.route) { FareCalculatorScreen(navController) }
        composable(Screen.Payment.route) { PaymentScreen(navController) }
        composable(Screen.Receipt.route) { ReceiptScreen(navController) }

        // ✅ WebView route
        composable("webview/{paymentUrl}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("paymentUrl") ?: ""
            WebViewScreen(navController, java.net.URLDecoder.decode(url, "UTF-8"))
        }
    }
}
