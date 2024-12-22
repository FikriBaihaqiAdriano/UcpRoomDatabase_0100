package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Barang")
data class Barang(
    @PrimaryKey
    val id_b:String,
    val nama_b:String,
    val deskripsi:String,
    val harga:String,
    val stok:String,
    val namaSuplier:String
)
