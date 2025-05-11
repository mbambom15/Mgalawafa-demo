package com.example.mgalafawafrontend.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.mgalafawafrontend.Screen // <-- Add this import

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(navController: NavController, paymentUrl: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    // Check if the URL contains success or cancel keywords to handle the response
                    if (url.contains("success")) {
                        // Redirect to the Receipt screen on successful payment
                        navController.navigate(Screen.Receipt.route)
                        return true
                    } else if (url.contains("cancel")) {
                        // Go back to the previous screen if payment is canceled
                        navController.popBackStack()
                        return true
                    }
                    return false
                }
            }
            loadUrl(paymentUrl)
        }
    })
}
