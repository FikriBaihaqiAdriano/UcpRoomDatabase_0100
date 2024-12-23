package com.example.ucp2.data.dependeciesinjection

import android.content.Context
import com.example.ucp2.data.database.InventoryDatabase
import com.example.ucp2.data.repository.LocalRepositoryBrg
import com.example.ucp2.data.repository.LocalRepositorySpl
import com.example.ucp2.data.repository.RepositoryBrg
import com.example.ucp2.data.repository.RepositorySpl

interface InterfaceContainerApp {
    val repositoryBrg: RepositoryBrg
    val repositorySpl: RepositorySpl
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryBrg: RepositoryBrg by lazy {
        LocalRepositoryBrg(InventoryDatabase.getDatabase(context).BarangDao())
    }

    override val repositorySpl: RepositorySpl by lazy {
        LocalRepositorySpl(InventoryDatabase.getDatabase(context).SuplierDao())
    }
}