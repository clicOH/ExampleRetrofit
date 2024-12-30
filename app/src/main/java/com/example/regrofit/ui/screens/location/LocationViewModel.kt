package com.example.regrofit.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regrofit.model.location.ModelLocations
import com.example.regrofit.model.state.ModelState
import com.example.regrofit.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val personRepository: AppRepository): ViewModel() {

    private val _locationId = MutableStateFlow<ModelState<ModelLocations>>(ModelState.init())

    val uiState: StateFlow<ModelState<ModelLocations>> = _locationId


    fun getPerson(personId: Int) {
        viewModelScope.launch {
            personRepository.getLocation(personId).collect {
                _locationId.value = it
            }
        }
    }
}