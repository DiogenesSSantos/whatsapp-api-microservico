package com.git.devdioge.whatsapp_api.controller.dtos

import java.time.LocalDateTime

data class PacienteRequestPostDTO(
    var nome: String,
    var numero: String,
    var bairro: String,
    var tipoConsulta: String,
    var status: String,
    var dataConsulta : LocalDateTime

)
