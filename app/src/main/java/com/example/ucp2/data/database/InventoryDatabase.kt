package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.BarangDao
import com.example.ucp2.data.dao.SuplierDao
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.data.entity.Suplier

@Database(entities = [Barang::class, Suplier::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun BarangDao(): BarangDao
    abstract fun SuplierDao(): SuplierDao

    companion object{
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase (content: Context): InventoryDatabase{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    content,
                    InventoryDatabase::class.java,
                    "InventoryDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }


}