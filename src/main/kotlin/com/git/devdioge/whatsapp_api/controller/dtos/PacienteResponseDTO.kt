package com.git.devdioge.whatsapp_api.controller.dtos

import java.time.LocalDateTime

data class PacienteResponseDTO(
    val codigo: String,
    val nome: String,
    val numero: String,
    val bairro: String,
    val tipoConsulta: String,
    val status: String,
    val dataConsulta : LocalDateTime,
    val dataMarcacao : LocalDateTime
)
