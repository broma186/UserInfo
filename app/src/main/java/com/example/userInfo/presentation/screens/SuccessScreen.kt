package com.example.userInfo.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.UserInfo.R
import com.example.userInfo.domain.model.User
import com.example.userInfo.presentation.components.UserRow
import com.example.userInfo.presentation.components.AddButton
import com.example.userInfo.presentation.components.AddUserDialog
import com.example.userInfo.presentation.components.RemoveUserDialog
import kotlinx.coroutines.launch

@Composable
fun SuccessScreen(
    users: List<User>,
    addUser: suspend (String, String) -> Boolean,
    removeUser: suspend (Int) -> Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val userToRemove = rememberSaveable { mutableStateOf<User?>(null) }
        LazyColumn {
            itemsIndexed(users) { index, user ->
                if (index > 0 && index <= users.lastIndex) {
                    HorizontalDivider(
                        Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
                UserRow(
                    name = user.name,
                    email = user.email,
                    createdOn = user.createdOn,
                    onLongPress = {
                        userToRemove.value = user
                    })
            }
        }
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        userToRemove.value?.run {
            RemoveUserDialog(name = name, onDismiss = {
                userToRemove.value = null
            }, onConfirm = {
                coroutineScope.launch {
                    val success = removeUser(id)
                    val message = if (success) context.getText(R.string.remove_user_result_success) else context.getText(R.string.remove_user_result_failure)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    userToRemove.value = null
                }
            })
        }
        val showAddDialog = rememberSaveable { mutableStateOf(false) }
        AddButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp, end = 8.dp),
            showPopup = showAddDialog
        )
        if (showAddDialog.value) {
            AddUserDialog(onDismiss = {
                showAddDialog.value = false
            },
                onConfirm = { name, email ->
                    coroutineScope.launch {
                        val success = addUser(name, email)
                        val message = if (success) context.getText(R.string.add_user_result_success) else context.getText(R.string.add_user_result_failure)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        showAddDialog.value = false
                    }
                })
        }
    }
}