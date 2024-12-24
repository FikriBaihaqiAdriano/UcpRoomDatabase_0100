package com.example.ucp2.ui.view.barang

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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.HomeBrgViewModel
import com.example.ucp2.ui.viewmodel.barang.HomeUiState


@Composable
fun HomeBrgView(
    viewModel: HomeBrgViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddBrg: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold (
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 30.dp),
        topBar = {
            TopAppBar(
                judul = "Daftar Barang",
                showBackButton = true,
                onBack = onBack
            )
        },
    ){ innerPadding -> val homeUiState by viewModel.homeUiState.collectAsState()

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
    homeUiState: HomeUiState,
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

        homeUiState.listBrg.isEmpty() -> {
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
            ListBrgView(
                listBrg = homeUiState.listBrg,
                onClick = { onClick(it)
                          println(it) },
                modifier = modifier
            )
        }

    }
}

@Composable
fun ListBrgView(
    listBrg: List<Barang>,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = listBrg,
            itemContent = { barang ->
                CardBrgView(
                    barang = barang,
                    onClick = { onClick(barang.id_b) }
                )
            }
        )
    }
}

@Composable
fun CardBrgView(
    barang: Barang,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val stok = barang.stok.toIntOrNull() ?: 0

    val cardColor = when {
        stok == 0 -> Color.Gray
        stok in 1..10 -> Color.Red
        else -> Color.Green
    }

    Card (
        onClick = onClick,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.id),
                    contentDescription = "ID barang",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = barang.id_b,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }


            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.nama),
                    contentDescription = "nama barang",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = barang.nama_b,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.deskripsi),
                    contentDescription = "deskripsi",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = barang.deskripsi,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.harga),
                    contentDescription = "harga",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = barang.harga,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
       }
    }
}
