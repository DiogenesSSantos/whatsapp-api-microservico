package com.git.devdioge.whatsapp_api.service

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.exception.RabbitMQErrorServceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Service
class RabbitMQRestService() {
    private lateinit var restTemplate: RestTemplate
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    init {
        restTemplate = RestTemplateBuilder()
            .rootUri("http://localhost:8080/rabbitmq")
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(10))
            .build()
    }


    fun enviarMensagem(pacienteRequestPostDTO: PacienteRequestPostDTO): PacienteResponseDTO? {
        val request : HttpEntity<PacienteRequestPostDTO> = HttpEntity(pacienteRequestPostDTO)
        var pacienteResponseDTO : PacienteResponseDTO? = null

        val response = restTemplate.exchange(
            "/enviar",
            HttpMethod.POST,
            request,
            PacienteResponseDTO::class.java
        )

        if (response.statusCode.is2xxSuccessful){
            pacienteResponseDTO = response.body
            return pacienteResponseDTO
        }

        if (response.statusCode.is4xxClientError) {
            throw RuntimeException("Erro por parte do cliente")
        }

        if (response.statusCode.is5xxServerError) {
            throw RabbitMQErrorServceException()
        }
        return pacienteResponseDTO
    }

}