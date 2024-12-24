package com.example.ucp2.ui.viewmodel.suplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Suplier
import com.example.ucp2.data.repository.RepositorySpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeSplViewModel(
    private val repositorySpl: RepositorySpl
) : ViewModel() {
    val homeUiState_spl: StateFlow<HomeUiState_spl> = repositorySpl.getAllSuplier()
        .filterNotNull()
        .map {
            HomeUiState_spl(
                listSpl = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiState_spl(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState_spl(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState_spl(
                isLoading = true,
            )
        )
}

data class HomeUiState_spl(
    val listSpl: List<Suplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = " "
)