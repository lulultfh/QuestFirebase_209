package com.example.datasiswa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datasiswa.model.Siswa
import com.example.datasiswa.repository.RepositorySiswa
import com.example.datasiswa.ui.route.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StatusUIDetail{
    data class Success(val satuSiswa: Siswa?) : StatusUIDetail
    object Error: StatusUIDetail
    object Loading: StatusUIDetail
}

class DetailVM (savedStateHandle: SavedStateHandle,private val repositorySiswa: RepositorySiswa): ViewModel(){
    private val idSiswa: Long =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)?.toLong()
            ?: error("idSiswa tidak ditemukan di SavedStateHandle")
    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set
    init {
        getSatuSiswa()
    }
    fun getSatuSiswa(){
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                StatusUIDetail.Success(satuSiswa = repositorySiswa.getDetailSiswa(idSiswa))
            }
            catch (e: IOException){
                StatusUIDetail.Error
            }
            catch (e: Exception){
                StatusUIDetail.Error
            }
        }
    }

    suspend fun hapusSatuSiswa(){
        try {
            repositorySiswa.hapusSatuSiswa(idSiswa)
            println("Sukses  Hapus Data: $idSiswa")
        } catch (e: Exception){
            println("Gagal hapus data: ${e.message}")
        }
    }
}