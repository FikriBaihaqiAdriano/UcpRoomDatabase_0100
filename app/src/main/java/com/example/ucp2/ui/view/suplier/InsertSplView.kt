package com.example.ucp2.ui.view.suplier

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
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.suplier.FormErrorState
import com.example.ucp2.ui.viewmodel.suplier.SplUIState

import com.example.ucp2.ui.viewmodel.suplier.SuplierEvent
import com.example.ucp2.ui.viewmodel.suplier.SuplierViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertSplView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: SuplierViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel
) {
    val uiState = viewModel.uiStateSpl // Ambil UI State dari viewmodel
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
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 18.dp),
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
                judul = "Tambah Suplier"
            )

            //Isi Body
            InsertBodySpl(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    //Update state di viewmodel
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveData_spl()
                    onNavigate()
                }
            )

        }

    }
}


@Composable
fun InsertBodySpl(
    modifier: Modifier = Modifier,
    onValueChange: (SuplierEvent) -> Unit,
    uiState: SplUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormSuplier(
            suplierEvent = uiState.suplierEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValidS,
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
fun FormSuplier(
    suplierEvent: SuplierEvent = SuplierEvent(),
    onValueChange: (SuplierEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.id_s,
            onValueChange = {
                onValueChange(suplierEvent.copy(id_s = it))
            },
            label = { Text("ID")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorState.id_s != null,
            placeholder = { Text("Masukkan id")}
        )
        Text(
            text = errorState.id_s ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.nama_s,
            onValueChange = {
                onValueChange(suplierEvent.copy(nama_s = it))
            },
            label = { Text("Nama")},
            isError = errorState.nama_s != null,
            placeholder = { Text("Masukkan nama")}
        )
        Text(
            text = errorState.nama_s ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.kontak,
            onValueChange = {
                onValueChange(suplierEvent.copy(kontak = it))
            },
            label = { Text("Kontak")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorState.kontak != null,
            placeholder = { Text("Masukkan Kontak")}
        )
        Text(
            text = errorState.kontak ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.alamat,
            onValueChange = {
                onValueChange(suplierEvent.copy(alamat = it))
            },
            label = { Text("Alamat")},
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat")}
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )


    }

}