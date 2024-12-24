package com.example.ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.suplier.HomeSplViewModel

object DataNama {
    @Composable
    fun DataSupplier(
        HomeSpl: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val namaData by HomeSpl.homeUiState_spl.collectAsState()
        val namaSupplier = namaData.listSpl.map { it.nama_s }
        return namaSupplier
    }
}