package com.example.myweather.core.presentation.permissions


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myweather.ui.theme.MyWeatherTheme



@Composable
fun Rationale(onDismiss : () -> Unit, onContinue : () -> Unit) {
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties()) {
        Surface(
            shape = RoundedCornerShape(CornerSize(8.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location", modifier = Modifier.size(40.dp))
                Text(
                    text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { onContinue() }) {
                    Text(text = "Continue")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RationalePreview(){
    fun onDismiss() {
        println("dismiss request")
    }

    fun onContinue() {
        onDismiss()
        println("continue request")
    }

    MyWeatherTheme {
        Rationale(onDismiss = { onDismiss() }, onContinue = { onContinue() })
    }
}