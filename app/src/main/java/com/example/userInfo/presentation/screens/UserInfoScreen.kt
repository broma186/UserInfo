package com.example.userInfo.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userInfo.presentation.viewmodel.UserInfoViewModel

@Composable
fun UserInfoScreen() {
    val viewModel: UserInfoViewModel = viewModel()
    UserInfoScreenContent(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreenContent(viewModel: UserInfoViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Users") })
        },
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
            ) {

            }
        })
}