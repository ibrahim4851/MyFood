package com.example.myfood.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.myfood.Screen
import com.example.myfood.ui.theme.urbanistFontFamily

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Sign Up",
                    Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 32.sp
                )
                Spacer(Modifier.size(38.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username, onValueChange = { username = it })
                Spacer(Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password, onValueChange = { password = it })
                Spacer(Modifier.size(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = passwordConfirmation,
                    onValueChange = { passwordConfirmation = it })
                Spacer(Modifier.size(8.dp))
                Spacer(Modifier.size(8.dp))
                Button(
                    onClick = {
                        if (password == passwordConfirmation) {
                            viewModel.onEvent(AuthEvent.SignUpEvent(username, password))
                            navController.navigate(Screen.HomeScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Passwords Are not Matching",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Up")
                }
                ClickableText(
                    text = AnnotatedString(text = "I have an account"),
                    onClick = { navController.navigate(Screen.LoginScreen.route) },
                    style = TextStyle(
                        color = Color(0xFFF55A00),
                        fontSize = 18.sp,
                        fontFamily = urbanistFontFamily
                    )
                )
            }
        }
    }
}