package com.example.regrofit.ui.screens.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.regrofit.model.home.ModelPerson
import com.example.regrofit.model.state.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeScreen(
    navController: NavHostController,
    viewModel: EpisodeViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Episodios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        viewModel.getPerson()
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

                    val data = uiState.data?.results ?: emptyList()

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
fun Body(data: List<ModelPerson.Person>) {
    return LazyColumn {
        items(data.size) { index ->
            Text(text = data[index].name ?: "")
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