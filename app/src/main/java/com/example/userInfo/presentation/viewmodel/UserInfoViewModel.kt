package com.example.userInfo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userInfo.data.model.UserData
import com.example.userInfo.data.model.mapToUI
import com.example.userInfo.domain.model.User
import com.example.userInfo.domain.usecase.AddUserInfoUseCase
import com.example.userInfo.domain.usecase.GetUserInfoUseCase
import com.example.userInfo.domain.usecase.RefreshUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val refreshUserInfoUseCase: RefreshUserInfoUseCase,
    private val addUserInfoUseCase: AddUserInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserInfoState>(UserInfoState.StartingState)
    val uiState: StateFlow<UserInfoState> = _uiState

    private val _messageAddOrRemoveUser = MutableSharedFlow<String>()
    val errorAddOrRemoveUser = _messageAddOrRemoveUser.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                _uiState.value = UserInfoState.Loading
                val userList = getUserInfoUseCase.invoke()
                if (userList.isNotEmpty()) {
                    _uiState.value = UserInfoState.Success(userList.map { it.mapToUI() })
                } else {
                    _uiState.value = UserInfoState.Error("No content to display")
                }
            } catch (exception: Exception) {
                _uiState.value = UserInfoState.Error(exception.cause?.message)
            }
        }
    }

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
                try {
                    addUserInfoUseCase.invoke(name, email)
                    val updatedUsers = refreshUserInfoUseCase.invoke().map { it.mapToUI() }
                    _uiState.value = UserInfoState.Success(updatedUsers)
                    _messageAddOrRemoveUser.emit("Successfully added user")
                } catch (exception: Exception) {
                    _messageAddOrRemoveUser.emit("Failed to add user")
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