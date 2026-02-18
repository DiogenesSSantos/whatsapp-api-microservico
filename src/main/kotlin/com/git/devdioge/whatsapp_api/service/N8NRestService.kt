package com.git.devdioge.whatsapp_api.service

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Service
class N8NRestService() {
    private lateinit var restTemplate: RestTemplate
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    init {
        restTemplate = RestTemplateBuilder()
            .rootUri("http://localhost:8080/n8b-api/n8n")
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(10))
            .build()
    }


    fun enviarMensagem(pacienteRequestPostDTO: PacienteRequestPostDTO){
        val request : HttpEntity<PacienteRequestPostDTO> = HttpEntity(pacienteRequestPostDTO)

        val response = restTemplate.exchange(
            "/enviar",
            HttpMethod.GET,
            request,
            String::class.java
        )

        log.warn(response.body.toString())
    }

}