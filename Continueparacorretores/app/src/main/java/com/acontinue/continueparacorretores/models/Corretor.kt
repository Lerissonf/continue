package com.acontinue.continueparacorretores.models

data class Corretor(val comissao: Float,
                    val endereco: String,
                    val inscricao_susep: String,
                    val nome: String,
                    val seguradoras_trabalho: String) {
    constructor(): this(0.0f, "", "", "", "")
}