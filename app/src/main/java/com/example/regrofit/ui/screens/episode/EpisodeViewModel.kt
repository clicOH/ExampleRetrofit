package com.example.regrofit.ui.screens.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regrofit.model.episode.ModelEpisode
import com.example.regrofit.model.home.ModelPerson
import com.example.regrofit.model.state.ModelState
import com.example.regrofit.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val personRepository: AppRepository): ViewModel() {

    private val _episode = MutableStateFlow<ModelState<ModelEpisode>>(ModelState.init())

    val uiState: StateFlow<ModelState<ModelEpisode>> = _episode


    fun getEpisode(episodeId: Int) {
        viewModelScope.launch {
            personRepository.getEpisode(episodeId).collect {
                _episode.value = it
            }
        }
    }
}