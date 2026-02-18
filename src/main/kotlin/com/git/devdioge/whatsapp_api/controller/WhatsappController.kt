package com.git.devdioge.whatsapp_api.controller

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.service.PacienteRestService
import com.git.devdioge.whatsapp_api.service.WhatsappService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["/whatsapp"])
class WhatsappController (private val whatsappService: WhatsappService) {


    @PostMapping
    fun salvar(@RequestBody pacienteRequestPostDTO: PacienteRequestPostDTO) : ResponseEntity<PacienteResponseDTO> {
        val pacienteSalvo = whatsappService.enviarMensagem(pacienteRequestPostDTO)

        return ResponseEntity.status(HttpStatus.OK)
            .body(pacienteSalvo)
    }

}