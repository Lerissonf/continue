package com.acontinue.continueparacorretores.models

data class Request (val idade_titular: Int,
                    val tipo_seguro: String,
                    val status_cliente: Boolean,
                    val status_requisicao: Int,
                    val cliente: String,
                    val descricao: String,
                    val risco: Float) {
    constructor(): this(0, "", false, 0, "", "", 0f)
}