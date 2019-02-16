package com.acontinue.continueparacorretores.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Corretor(val comissao: Float,
                    val endereco: String,
                    val inscricao_susep: String,
                    val nome: String,
                    val seguradoras_trabalho: String): Parcelable    {
    constructor(): this(0.0f, "", "", "", "")
}