package com.example.myfood.auth

sealed class AuthEvent {

    data class LoginEvent(val userName: String, val password: String): AuthEvent()

    data class SignUpEvent(val userName: String, val password: String): AuthEvent()

    data class LogoutEvent(val dummy: String): AuthEvent()

}
