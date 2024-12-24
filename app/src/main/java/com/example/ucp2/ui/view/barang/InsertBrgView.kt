package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.DataNama
import com.example.ucp2.ui.costumwidget.DropdownSpl
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.BarangEvent
import com.example.ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2.ui.viewmodel.barang.BrgUIState
import com.example.ucp2.ui.viewmodel.barang.FormErrorState
import kotlinx.coroutines.launch

@Composable
fun InsertBrgView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: BarangViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel
) {
    val uiState = viewModel.uiState_B // Ambil UI State dari viewmodel
    val snackbarHostState =  remember { SnackbarHostState() } // Snack
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackBarMessage
    LaunchedEffect(uiState.snackbarMessage)  {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Barang"
            )

            //Isi Body
            InsertBodyBrg(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    //Update state di viewmodel
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )

        }

    }
}


@Composable
fun InsertBodyBrg(
    modifier: Modifier = Modifier,
    onValueChange: (BarangEvent) -> Unit,
    uiState: BrgUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormBarang(
            barangEvent = uiState.barangEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),

            ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormBarang(
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.id_b,
            onValueChange = {
                onValueChange(barangEvent.copy(id_b = it))
            },
            label = { Text("ID")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorState.id_b != null,
            placeholder = { Text("Masukkan id")}
        )
        Text(
            text = errorState.id_b ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.nama_b,
            onValueChange = {
                onValueChange(barangEvent.copy(nama_b = it))
            },
            label = { Text("Nama")},
            isError = errorState.nama_b != null,
            placeholder = { Text("Masukkan nama")}
        )
        Text(
            text = errorState.nama_b ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = {
                onValueChange(barangEvent.copy(deskripsi = it))
            },
            label = { Text("Deskripsi")},
            isError = errorState.deskrpsi != null,
            placeholder = { Text("Masukkan Deskripsi")}
        )
        Text(
            text = errorState.deskrpsi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.harga,
            onValueChange = {
                onValueChange(barangEvent.copy(harga = it))
            },
            label = { Text("Harga")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan Harga")}
        )
        Text(
            text = errorState.harga ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.stok,
            onValueChange = {
                onValueChange(barangEvent.copy(stok = it))
            },
            label = { Text("Stok")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorState.stok != null,
            placeholder = { Text("Masukkan stok")}
        )
        Text(
            text = errorState.stok ?: "",
            color = Color.Red
        )

    }

}