package com.example.ucp2.ui.viewmodel.suplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Suplier
import com.example.ucp2.data.repository.RepositorySpl
import kotlinx.coroutines.launch

class SuplierViewModel(private val repositorySpl: RepositorySpl) : ViewModel() {

    var uiStateSpl by mutableStateOf(SplUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(suplierEvent: SuplierEvent) {
        uiStateSpl = uiStateSpl.copy(
            suplierEvent = suplierEvent,
        )
    }

    //Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiStateSpl.suplierEvent
        val errorState = FormErrorState(
            id_s = if (event.id_s.isNotEmpty()) null else "ID tidak boleh kosong",
            nama_s = if (event.nama_s.isNotEmpty()) null else "Nama tidak boleh kosong",
            kontak = if (event.kontak.isNotEmpty()) null else "Kontak tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong"
        )

        uiStateSpl = uiStateSpl.copy(isEntryValidS = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveData_spl() {
        val currentEvent = uiStateSpl.suplierEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositorySpl.insertSpl(currentEvent.toSuplierEntity())
                    uiStateSpl = uiStateSpl.copy(
                        snackbarMessage = "Data Berhasil disimpan",
                        suplierEvent = SuplierEvent(), // Reset input form
                        isEntryValidS = FormErrorState() //Reset input state
                    )
                } catch (e: Exception) {
                    uiStateSpl = uiStateSpl.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateSpl = uiStateSpl.copy(
                snackbarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    // Reset pesan SnackBar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiStateSpl = uiStateSpl.copy(snackbarMessage = null)
    }
}

data class SplUIState(
    val suplierEvent: SuplierEvent = SuplierEvent(),
    val isEntryValidS: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null,
)

data class FormErrorState(
    val id_s: String? = null,
    val nama_s: String? = null,
    val kontak: String? = null,
    val alamat: String? = null

) {
    fun isValid(): Boolean {
        return id_s == null && nama_s == null && kontak == null &&
                alamat == null
    }
}

// Menyimpan input form dalam entity
fun SuplierEvent.toSuplierEntity(): Suplier = Suplier(
    id_s = id_s,
    nama_s = nama_s,
    kontak = kontak,
    alamat = alamat
)

//data class Variabel yang menyimpan data input form
data class SuplierEvent(
    val id_s:String = "",
    val nama_s:String = "",
    val kontak:String = "",
    val alamat:String = ""
)