package com.example.userInfo.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userInfo.presentation.viewmodel.UserInfoViewModel
import com.example.userInfo.presentation.viewmodel.UserInfoState

@Composable
fun UserInfoScreen() {
    val viewModel: UserInfoViewModel = viewModel()
    UserInfoScreenContent(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreenContent(viewModel: UserInfoViewModel) {
    val userInfoState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Users") })
        },
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
            ) {
                when (userInfoState) {
                    is UserInfoState.StartingState -> {
                        // TODO: Starting screen
                    }
                    is UserInfoState.Loading -> {
                        LoadingWheel()
                    }
                    is UserInfoState.Success -> {
                        SuccessScreen(userInfoState.content)
                    }
                    is UserInfoState.Error -> {
                        ErrorScreen(errorMessage = userInfoState.errorMessage ?: "Sorry, no users")
                    }
                }
            }
        })
}

@Composable
fun LoadingWheel() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.5f))
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}