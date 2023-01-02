package com.example.myweather.core.presentation.permissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun Rationale(onDismiss : () -> Unit, onContinue : () -> Unit) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties()) {
        Surface(
            shape = RoundedCornerShape(CornerSize(8.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location", modifier = Modifier.size(40.dp).padding(top = 8.dp))
                Text(
                    text = LocalContext.current.resources.getString(com.example.myweather.R.string.permission_requested_text),
                    modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = LocalContext.current.resources.getString(com.example.myweather.R.string.permission_requested_text_part_2),
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
                )
                Button(onClick = { onContinue() }, modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(text = LocalContext.current.resources.getString(com.example.myweather.R.string._continue))
                }
            }
        }
    }
}