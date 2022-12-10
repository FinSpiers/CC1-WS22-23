package com.example.myweather.core.presentation.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myweather.R
import com.example.myweather.core.domain.model.Settings

@Composable
fun SettingsScreen(
    viewModel : SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(CornerSize(5.dp)),
            border = BorderStroke(5.dp, MaterialTheme.colorScheme.primaryContainer)

        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.unit),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.celsius),
                        style = MaterialTheme.typography.titleMedium
                    )
                    RadioButton(
                        selected = state.isCelsius,
                        onClick = { viewModel.setIsCelsius(true) })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.fahrenheit),
                        style = MaterialTheme.typography.titleMedium
                    )
                    RadioButton(
                        selected = !state.isCelsius,
                        onClick = { viewModel.setIsCelsius(false) })
                }
            }
        }
    }
}

