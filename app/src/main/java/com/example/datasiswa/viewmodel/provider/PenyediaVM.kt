package com.example.datasiswa.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.datasiswa.repository.AplikasiDataSiswa
import com.example.datasiswa.viewmodel.EntryVM
import com.example.datasiswa.viewmodel.HomeVM

fun CreationExtras.aplikasiDataSiswa(): AplikasiDataSiswa = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
        AplikasiDataSiswa
        )
object PenyediaVM{
    val Factory = viewModelFactory {
        initializer { HomeVM(aplikasiDataSiswa().container.repositorySiswa) }
        initializer { EntryVM(aplikasiDataSiswa().container.repositorySiswa) }
    }
}