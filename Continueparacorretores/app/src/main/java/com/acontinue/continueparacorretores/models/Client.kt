package com.acontinue.continueparacorretores.models

data class Client(val idade: Int,
                  val endereco: String,
                  val sexo: String,
                  val nome: String,
                  val cpf: String,
                  val apolice: String?,
                  val tipoSeguro: String?,
                  val vigencia: Int?,
                  val statusPagamento: Boolean?,
                  val dataPagamento: String?,
                  var email: String) {
    constructor(): this(
        0, "", "", "", "", "", "",
        1, true, "", "")
}