package com.example.ucp2.ui.viewmodel

import com.example.ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2.ui.viewmodel.barang.DetailBrgViewModel
import com.example.ucp2.ui.viewmodel.barang.HomeBrgViewModel
import com.example.ucp2.ui.viewmodel.barang.UpdateBrgViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.InventoryApp
import com.example.ucp2.ui.viewmodel.suplier.HomeSplViewModel
import com.example.ucp2.ui.viewmodel.suplier.SuplierViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            BarangViewModel(
                InventoryApp().containerApp.repositoryBrg
            )
        }

        initializer{
            val homeBrgViewModel = HomeBrgViewModel(
                InventoryApp().containerApp.repositoryBrg
            )
            homeBrgViewModel
        }

        initializer{
            DetailBrgViewModel(
                createSavedStateHandle(),
                InventoryApp().containerApp.repositoryBrg,
            )
        }

        initializer{
            UpdateBrgViewModel(
                createSavedStateHandle(),
                InventoryApp().containerApp.repositoryBrg,
            )
        }

        initializer {
            val homeSplViewModel = HomeSplViewModel(
                InventoryApp().containerApp.repositorySpl
            )
            homeSplViewModel
        }

        initializer {
            SuplierViewModel(
                InventoryApp().containerApp.repositorySpl
            )
        }
    }
}

fun CreationExtras.InventoryApp(): InventoryApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as InventoryApp)