package com.example.ucp2.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.data.repository.RepositoryBrg
import kotlinx.coroutines.launch

class BarangViewModel(private val repositoryBrg: RepositoryBrg) : ViewModel() {

    var uiState_B by mutableStateOf(BrgUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(barangEvent: BarangEvent) {
        uiState_B = uiState_B.copy(
            barangEvent = barangEvent,
        )
    }

    //Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiState_B.barangEvent
        val errorState = FormErrorState(
            id_b = if (event.id_b.isNotEmpty()) null else "ID tidak boleh kosong",
            nama_b = if (event.nama_b.isNotEmpty()) null else "Nama tidak boleh kosong",
            deskrpsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga tidak boleh kosong",
            stok = if (event.stok.isNotEmpty()) null else "Stok tidak boleh kosong",
            namaSuplier = if (event.namaSuplier.isNotEmpty()) null else "Nama Suplier tidak boleh kosong"
        )

        uiState_B = uiState_B.copy(isEntryValid = errorState)
        return errorState.isValid()
    }



    // Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState_B.barangEvent


        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryBrg.insertBrg(currentEvent.toBarangEntity())
                    uiState_B = uiState_B.copy(
                        snackbarMessage = "Data Berhasil disimpan",
                        barangEvent = BarangEvent(), // Reset input form
                        isEntryValid = FormErrorState() //Reset input state
                    )
                } catch (e: Exception) {
                    uiState_B = uiState_B.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState_B = uiState_B.copy(
                snackbarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    // Reset pesan SnackBar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState_B = uiState_B.copy(snackbarMessage = null)
    }
}

data class BrgUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null,
)

data class FormErrorState(
    val id_b: String? = null,
    val nama_b: String? = null,
    val deskrpsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namaSuplier: String? = null
) {
    fun isValid(): Boolean {
        return id_b == null && nama_b == null && deskrpsi == null &&
                harga == null && stok == null && namaSuplier == null
    }
}

// Menyimpan input form dalam entity
fun BarangEvent.toBarangEntity(): Barang = Barang(
    id_b = id_b,
    nama_b = nama_b,
    deskripsi = deskripsi,
    harga = harga,
    stok = stok,
    namaSuplier = namaSuplier
)

//data class Variabel yang menyimpan data input form
data class BarangEvent(
    val id_b:String = "",
    val nama_b:String = "",
    val deskripsi:String = "",
    val harga:String = "",
    val stok:String = "",
    val namaSuplier:String = "",
)