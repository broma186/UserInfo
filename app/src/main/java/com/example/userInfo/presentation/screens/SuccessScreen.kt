package com.example.userInfo.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.userInfo.domain.model.User
import com.example.userInfo.presentation.UserRow
import com.example.userInfo.presentation.components.AddButton
import com.example.userInfo.presentation.components.AddUserDialog

@Composable
fun SuccessScreen(
    users: List<User>,
    addUser: (name: String, email: String) -> Unit
    //showAddUserDialog: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            itemsIndexed(users) { index, user ->
                if (index > 0 && index < users.lastIndex) {
                    HorizontalDivider(
                        Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
                UserRow(name = user.name, email = user.email, createdOn = user.createdOn)
            }
        }
        val showPopup = remember { mutableStateOf(false) }
        AddButton(
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 8.dp),
            showPopup = showPopup
        )
        if (showPopup.value) {
            AddUserDialog(onDismiss = {
                showPopup.value = false
            },
                onConfirm = { name, email ->
                    addUser(name, email)
                    showPopup.value = false
                })
        }
    }
}