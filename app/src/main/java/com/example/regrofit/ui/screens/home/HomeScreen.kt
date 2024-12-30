package com.example.regrofit.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.regrofit.model.home.ModelPerson
import com.example.regrofit.model.state.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPersonClick: (Int) -> Unit,

    viewModel: HomeViewModel = hiltViewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Personajes") },
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

                    Body(onPersonClick, data)

                }

                Status.FAILED -> {
                    Error(uiState.message ?: "")
                }
            }
        }
    }
}

@Composable
fun Body(onPersonClick: (Int) -> Unit, data: List<ModelPerson.Person>) {
    LazyColumn {
        items(data.size) { index ->
            ItemCard(onPersonClick, data[index])
        }
    }
}

@Composable
fun ItemCard(onPersonClick: (Int) -> Unit, person: ModelPerson.Person) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                println("${person.name}")
                onPersonClick(person.id ?: 0)
            }

    ) {

        Row(Modifier.padding(5.dp)) {
            AsyncImage(
                model = person.image ?: "",
                contentDescription = "Personaje",
            )
            Column {
                Text(text = person.name ?: "")
                Text(text = person.species ?: "")
                Text(text = person.status ?: "")
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

fun openDetail(onPersonClick: (Int) -> Unit, id: Int) {

}

fun openDetail(navController: NavHostController, url: String) {
    navController.navigate("detail/$url")
}