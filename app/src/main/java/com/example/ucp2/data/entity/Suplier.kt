package com.example.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Suplier")
data class Suplier(
    @PrimaryKey
    val id_s:String,
    val nama_s:String,
    val kontak:String,
    val alamat:String
)
