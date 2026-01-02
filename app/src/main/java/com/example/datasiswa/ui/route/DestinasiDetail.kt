package com.example.datasiswa.ui.route

import com.example.datasiswa.R

object DestinasiDetail : DestinasiNavigasi{
    override val route = "detail_siswa"
    override val titleRes= R.string.detail_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}