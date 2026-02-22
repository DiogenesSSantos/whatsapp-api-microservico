package com.git.devdioge.whatsapp_api.exception

class RabbitMQErrorServceException(
    private val mensagem:
    String = "Error na requisição a api-Rabbitmq,verifique os logs do serviço para mais informações."
) :
    RuntimeException(mensagem) {
}