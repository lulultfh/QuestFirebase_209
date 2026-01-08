package com.example.datasiswa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datasiswa.model.DetailSiswa
import com.example.datasiswa.model.UIStateSiswa
import com.example.datasiswa.model.toDataSiswa
import com.example.datasiswa.model.toUiStateSiswa
import com.example.datasiswa.repository.RepositorySiswa
import com.example.datasiswa.ui.route.DestinasiDetail
import kotlinx.coroutines.launch

class EditVM (savedStateHandle: SavedStateHandle, private val repositorySiswa: RepositorySiswa): ViewModel(){
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set
    private val idSiswa: Long =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)?.toLong()
            ?: error("idSiswa tidak ditemukan di SavedStatedHandle ")
    init {
        viewModelScope.launch {
            uiStateSiswa = repositorySiswa.getDetailSiswa(idSiswa)!!
                .toUiStateSiswa(true)
        }
    }
    fun updateUiState(detailSiswa: DetailSiswa){
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput
                (detailSiswa))
    }
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean{
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    suspend fun editSatuSiswa(){
        if (validasiInput(uiStateSiswa.detailSiswa)){
            try {
                repositorySiswa.editSatuSiswa(idSiswa, uiStateSiswa.detailSiswa.toDataSiswa())
                println("Update sukses: $idSiswa")
            }catch (e: Exception){
                println("Update error: ${e.message}")
            }
        }
    }
}