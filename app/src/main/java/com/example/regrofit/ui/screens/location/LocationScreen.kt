package com.example.regrofit.ui.screens.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.regrofit.model.location.ModelLocations
import com.example.regrofit.model.state.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    onLocationClick: () -> Unit,
    personId: Int,
    viewModel: LocationViewModel = hiltViewModel()

) {

    LaunchedEffect(personId) {
        viewModel.getPerson(personId)
    }


    val uiState by viewModel.uiState.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Ubicación") },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { onLocationClick() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.getPerson(personId)
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            when (uiState.status) {
                Status.INIT -> {

                }

                Status.LOADING -> {
                    Loading()

                }

                Status.SUCCESS -> {

                    val data = uiState.data ?: ModelLocations()

                    Body(data)

                }

                Status.FAILED -> {
                    Error(uiState.message ?: "")
                }
            }
        }
    }
}

@Composable
fun Body(data: ModelLocations?) {
    return Column {
        Text(text = data?.name ?: "")
        Text(text = data?.type ?: "")
        Text(text = data?.dimension ?: "")
        Text(text = data?.created ?: "")
        Text(text = data?.url ?: "")
    }
}

@Composable
fun Loading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun Error(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}