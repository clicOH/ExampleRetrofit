package com.example.regrofit.repository

import com.example.regrofit.api.Api
import com.example.regrofit.model.episode.ModelEpisode
import com.example.regrofit.model.home.ModelPerson
import com.example.regrofit.model.location.ModelLocations
import com.example.regrofit.model.state.ModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(private val api: Api) {

    fun getPerson(): Flow<ModelState<ModelPerson>> {
        return flow {
            emit(ModelState.loading())

            val response = api.getPerson()

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(ModelState.success(data))
                } else {
                    emit(ModelState.failed(response.message()))
                }
            } else {
                emit(ModelState.failed(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getLocation(personId: Int): Flow<ModelState<ModelLocations>> {
        return flow {
            emit(ModelState.loading())

            val response = api.getLocation(personId)

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(ModelState.success(data))
                } else {
                    emit(ModelState.failed(response.message()))
                }
            } else {
                emit(ModelState.failed(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getEpisode(episodeId: Int): Flow<ModelState<ModelEpisode>> {
        return flow {
            emit(ModelState.loading())

            val response = api.getEpisode(episodeId)

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(ModelState.success(data))
                } else {
                    emit(ModelState.failed(response.message()))
                }
            } else {
                emit(ModelState.failed(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }
}