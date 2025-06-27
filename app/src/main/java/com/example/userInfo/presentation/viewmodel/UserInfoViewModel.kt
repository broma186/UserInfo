package com.example.userInfo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.usecase.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    userInfoUseCase: UserInfoUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow<UserState>(UserState.StartingState)
    val uiState: StateFlow<UserState> = _uiState

    init {
        viewModelScope.launch {
            try {
                _uiState.value = UserState.Loading
                val userList = userInfoUseCase.invoke()
                if (userList.isNotEmpty()) {
                    _uiState.value = UserState.Success(userList)
                } else {
                    _uiState.value = UserState.Error("No content to display")
                }
            } catch (exception: Exception) {
                _uiState.value = UserState.Error(exception.cause?.message)
            }
        }
    }
}

sealed class UserState {
    data object StartingState : UserState()
    data object Loading : UserState()
    data class Success(val content: List<User>) : UserState()
    data class Error(val errorMessage: String?) : UserState()
}