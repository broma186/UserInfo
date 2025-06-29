package com.example.userInfo.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.userInfo.domain.model.User
import com.example.userInfo.presentation.UserRow

@Composable
fun SuccessScreen(
    users: List<User>
) {
    LazyColumn {
        itemsIndexed(users) { index , user ->
            if (index > 0 && index < users.lastIndex) {
                HorizontalDivider(Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
            }
            UserRow(name = user.name, email = user.email, createdOn = user.createdOn)
        }
    }
    //TODO: Add button here.
}