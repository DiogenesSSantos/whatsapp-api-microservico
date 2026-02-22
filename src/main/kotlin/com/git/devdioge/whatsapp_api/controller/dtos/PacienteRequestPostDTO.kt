package com.git.devdioge.whatsapp_api.controller.dtos

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class PacienteRequestPostDTO(
    @NotBlank
    var nome: String,
    @NotBlank
    var numero: String,
    @NotBlank
    var bairro: String,
    @NotBlank
    var tipoConsulta: String,
    @NotBlank
    var status: String,
    @NotBlank
    var dataConsulta : LocalDateTime

)
