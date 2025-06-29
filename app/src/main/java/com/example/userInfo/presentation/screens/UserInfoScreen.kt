package com.example.userInfo.presentation.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userInfo.presentation.viewmodel.UserInfoViewModel
import com.example.userInfo.presentation.viewmodel.UserInfoState
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun UserInfoScreen() {
    val viewModel: UserInfoViewModel = viewModel()

    UserInfoScreenContent(
        viewModel.uiState.collectAsState().value,
        viewModel.errorAddOrRemoveUser,
        viewModel::addUser
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreenContent(
    userInfoState: UserInfoState,
    errorToast: SharedFlow<String>,
    addUser: (name: String, email: String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        errorToast.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
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
                        // no op
                    }
                    is UserInfoState.Loading -> {
                        LoadingScreen()
                    }
                    is UserInfoState.Success -> {
                        SuccessScreen(userInfoState.content, addUser)
                    }
                    is UserInfoState.Error -> {
                        ErrorScreen(errorMessage = userInfoState.errorMessage ?: "Sorry, no users")
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