package com.example.myfood.auth

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfood.DbConstants.SHARED_PREF_USER_NAME
import com.example.myfood.room.FoodRepository
import com.example.myfood.room.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val editor = sharedPreferences.edit()
    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    private suspend fun performSignUp(userName: String, password: String) {
        val userEntity = UserEntity(0L, userName, password)
        repository.signUpUser(userEntity)
        editor.putString(SHARED_PREF_USER_NAME, userEntity.username)
        editor.apply()
    }

    private fun performLogin(userName: String, password: String) = viewModelScope.launch {
        val user = repository.loginUser(userName, password)
        user?.let {
            _state.value = AuthState(isLoginSuccessful = true)
            Log.i("usercredentials", _state.value.isLoginSuccessful.toString())
        }
    }

    private fun performLogout() {
        editor.putString(SHARED_PREF_USER_NAME, null)
        editor.apply()
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.LoginEvent -> {
                viewModelScope.launch {
                    performLogin(event.userName, event.password)
                }
            }

            is AuthEvent.SignUpEvent -> {
                viewModelScope.launch {
                    performSignUp(event.userName, event.password)
                }
            }

            is AuthEvent.LogoutEvent -> {
                viewModelScope.launch {
                    performLogout()
                }
            }
        }
    }

}