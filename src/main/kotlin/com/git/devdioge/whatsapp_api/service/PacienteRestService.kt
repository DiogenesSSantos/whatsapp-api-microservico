package com.git.devdioge.whatsapp_api.service

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.stereotype.Service

@Service
class PacienteRestService(private val restTemplate: RestTemplate) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun salvar(pacienteRequestPostDTO: PacienteRequestPostDTO) : PacienteResponseDTO {
        val request : HttpEntity<PacienteRequestPostDTO> = HttpEntity(pacienteRequestPostDTO)

        val response = restTemplate.exchange(
            "/pacientes",
            HttpMethod.POST,
            request,
            PacienteResponseDTO::class.java
        )
        //Corrigir
        return response.body!!
    }

}