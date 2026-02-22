package com.git.devdioge.whatsapp_api.swagger

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody


@Tag(name = "Whatsapp controller" , description = "Whatssap controller descrição.")
interface OpenApiWhatsappController {


    fun salvar(@RequestBody @Valid pacienteRequestPostDTO: PacienteRequestPostDTO) : ResponseEntity<PacienteResponseDTO>?

}