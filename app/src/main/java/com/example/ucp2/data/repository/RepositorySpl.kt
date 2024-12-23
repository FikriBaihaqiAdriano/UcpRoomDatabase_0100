package com.example.ucp2.data.repository

import com.example.ucp2.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

interface RepositorySpl {
    suspend fun insertSpl(suplier: Suplier)
    fun getAllSuplier(): Flow<List<Suplier>>
    fun getSpl(id: String): Flow<Suplier>
}