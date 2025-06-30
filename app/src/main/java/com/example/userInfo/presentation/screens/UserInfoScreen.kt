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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.UserInfo.R
import com.example.userInfo.presentation.viewmodel.UserInfoViewModel
import com.example.userInfo.presentation.viewmodel.UserInfoState

@Composable
fun UserInfoScreen() {
    val viewModel: UserInfoViewModel = viewModel()
    LaunchedEffect(Unit) {
        if (viewModel.uiState.value is UserInfoState.StartingState) {
            viewModel.fetchUsers()
        }
    }
    UserInfoScreenContent(
        viewModel.uiState.collectAsState().value,
        viewModel::addUser,
        viewModel::removeUser
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreenContent(
    userInfoState: UserInfoState,
    addUser: suspend (String, String) -> Boolean,
    removeUser: suspend (Int) -> Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.toolbar_users)) })
        },
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
            ) {
                when (userInfoState) {
                    is UserInfoState.StartingState -> {
                        // no op
                    }
                    is UserInfoState.Loading -> {
                        LoadingScreen()
                    }
                    is UserInfoState.Success -> {
                        SuccessScreen(userInfoState.content, addUser, removeUser)
                    }
                    is UserInfoState.Error -> {
                        ErrorScreen(errorMessage = userInfoState.errorMessage ?: stringResource(id = R.string.data_error_message))
                    }
                }
            }
        })
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}