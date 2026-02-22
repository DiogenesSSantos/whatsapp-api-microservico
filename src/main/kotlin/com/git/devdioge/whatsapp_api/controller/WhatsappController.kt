package com.git.devdioge.whatsapp_api.controller

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.service.WhatsappService
import com.git.devdioge.whatsapp_api.swagger.OpenApiWhatsappController
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["/whatsapp"])
class WhatsappController (private val whatsappService: WhatsappService) : OpenApiWhatsappController {


    @PostMapping
    override fun salvar(@RequestBody @Valid pacienteRequestPostDTO: PacienteRequestPostDTO) : ResponseEntity<PacienteResponseDTO>? {
        val pacienteResponseDTO = whatsappService.enviarMensagem(pacienteRequestPostDTO)

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDTO)

    }

}