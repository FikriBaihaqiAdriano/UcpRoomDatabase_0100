package com.example.ucp2.ui.view.suplier

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.entity.Suplier
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.suplier.HomeSplViewModel
import com.example.ucp2.ui.viewmodel.suplier.HomeUiState_spl


@Composable
fun HomeSplView(
    viewModel: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddSpl: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold (
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 18.dp),
        topBar = {
            TopAppBar(
                judul = "Daftar Barang",
                showBackButton = true,
                onBack = onBack
            )
        },
    ){ innerPadding -> val homeUiState by viewModel.homeUiState_spl.collectAsState()

        BodyHomeBrgView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = modifier.padding(innerPadding)
        )

    }
}

@Composable
fun BodyHomeBrgView(
    homeUiState: HomeUiState_spl,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when {
        homeUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiState.isError -> {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = homeUiState.errorMessage,
                    withDismissAction = true,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        homeUiState.listSpl.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListSplView(
                listSpl = homeUiState.listSpl,
                onClick = { onClick(it)
                    println(it) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListSplView(
    listSpl: List<Suplier>,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = listSpl,
            itemContent = { suplier ->
                CardSplView(
                    suplier = suplier,
                    onClick = { onClick(suplier.id_s) }
                )
            }
        )
    }
}

@Composable
fun CardSplView(
    suplier: Suplier,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card (
        onClick = onClick,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.id),
                    contentDescription = "id suplier",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = suplier.id_s,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.nama_s),
                    contentDescription = "nama suplier",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = suplier.nama_s,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.kontak),
                    contentDescription = "kontak",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = suplier.kontak,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                painter = painterResource(id = R.drawable.alamat),
                contentDescription = "alamat",
                modifier = Modifier.size(40.dp)
            )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = suplier.alamat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}
