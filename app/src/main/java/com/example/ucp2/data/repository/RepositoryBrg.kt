package com.example.ucp2.data.repository

import com.example.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

interface RepositoryBrg {
    suspend fun insertBrg(barang: Barang)
    fun getAllBarang(): Flow<List<Barang>>
    fun getBrg(id: String): Flow<Barang>
    suspend fun deleteBrg(barang: Barang)
    suspend  fun updateBrg(barang: Barang)
}