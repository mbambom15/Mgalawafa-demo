// Screen.kt
package com.example.mgalafawafrontend

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object FareCalculator : Screen("fare_calculator")
    object Payment : Screen("payment")
    object Receipt : Screen("receipt")
}
