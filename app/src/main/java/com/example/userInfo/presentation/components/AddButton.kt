package com.example.userInfo.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    showPopup: MutableState<Boolean>
) {
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                showPopup.value = !showPopup.value
            },
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.White,
            contentColor = Color.Black,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}