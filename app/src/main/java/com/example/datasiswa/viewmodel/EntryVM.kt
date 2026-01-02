package com.example.datasiswa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.datasiswa.model.DetailSiswa
import com.example.datasiswa.model.UIStateSiswa
import com.example.datasiswa.model.toDataSiswa
import com.example.datasiswa.repository.RepositorySiswa

class EntryVM(private val repositorySiswa: RepositorySiswa): ViewModel(){
    var uiStateSiswa by mutableStateOf(UIStateSiswa())

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean
    {
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    fun updateUIState(detailSiswa: DetailSiswa){
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }
    suspend fun addSiswa(){
        if (validasiInput()){
            repositorySiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
        }
    }
}