package com.example.myfood.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AboutScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                    Text("ABOUT")
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.Notifications, null)
                    }
                }
                Text(text = aboutText)
            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen(navController = rememberNavController())
}

val aboutText = "Welcome to MyFood App, where cooking becomes an adventure!\n" +
        "\n" +
        "\uD83C\uDF73 Explore a diverse collection of recipes curated for every skill level and taste.\n" +
        "\n" +
        "\uD83D\uDCF1 Easy-to-follow instructions make cooking a breeze.\n" +
        "\n" +
        "\uD83D\uDCA1 Personalize your experience, save favorites, and discover new culinary delights.\n" +
        "\n" +
        "Join us and transform your kitchen into a place of creativity and deliciousness!"