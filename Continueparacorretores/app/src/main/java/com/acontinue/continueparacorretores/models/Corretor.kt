package com.acontinue.continueparacorretores.models

data class Corretor(val comissao: Float,
                    val endereco: String,
                    val inscricao_susep: String,
                    val uid: String,
                    val nome: String,
                    val seguradoras_trabalho: String)