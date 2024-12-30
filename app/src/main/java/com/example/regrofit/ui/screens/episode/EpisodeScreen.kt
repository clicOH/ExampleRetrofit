package com.example.regrofit.ui.screens.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.regrofit.R
import com.example.regrofit.model.episode.ModelEpisode
import com.example.regrofit.model.state.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeScreen(
    onBackPress: () -> Unit,
    episodeId: Int,
    viewModel: EpisodeViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(episodeId) {
        viewModel.getEpisode(episodeId)
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Episodios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBackPress

                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "go_back_icon",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.getEpisode(episodeId)
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

                    val data = uiState.data ?: ModelEpisode()

                    Body(onBackPress, data)

                }

                Status.FAILED -> {
                    Error(uiState.message ?: "")
                }
            }
        }
    }
}

@Composable
fun Body(onBackPress: () -> Unit, data: ModelEpisode) {
    return Column() {
        Text(data.name ?: "")
        Text(data.air_date ?: "")
        Text(data.episode ?: "")
        Text(data.created ?: "")
        Text(data.url ?: "")
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
            Button(onClick = onBackPress) {
                Text(stringResource(R.string.return_page))
            }
        }
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