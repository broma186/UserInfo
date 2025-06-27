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

    private val _uiState = MutableStateFlow<UserInfoState>(UserInfoState.StartingState)
    val uiState: StateFlow<UserInfoState> = _uiState

    init {
        viewModelScope.launch {
            try {
                _uiState.value = UserInfoState.Loading
                val userList = userInfoUseCase.invoke()
                if (userList.isNotEmpty()) {
                    _uiState.value = UserInfoState.Success(userList)
                } else {
                    _uiState.value = UserInfoState.Error("No content to display")
                }
            } catch (exception: Exception) {
                _uiState.value = UserInfoState.Error(exception.cause?.message)
            }
        }
    }
}

sealed class UserInfoState {
    data object StartingState : UserInfoState()
    data object Loading : UserInfoState()
    data class Success(val content: List<User>) : UserInfoState()
    data class Error(val errorMessage: String?) : UserInfoState()
}