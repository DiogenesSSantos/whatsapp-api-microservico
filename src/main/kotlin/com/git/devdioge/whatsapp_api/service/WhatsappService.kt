package com.git.devdioge.whatsapp_api.service

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.exception.WhatsappNaoConectadoException
import com.git.devdioge.whatsapp_api.exception.WhatsappNaoExisteException
import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.api.Whatsapp.webBuilder
import it.auties.whatsapp.model.jid.Jid
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class WhatsappService(
    private var whatsapp: CompletableFuture<Whatsapp> = CompletableFuture<Whatsapp>(),
    private val rabbitMQRestService: RabbitMQRestService,
) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    var qrCode: String? = null


    @PostConstruct
    fun init() {
        val completableFuture = webBuilder()
            .newConnection()
            .unregistered { qrCode = it }
            .addLoggedInListener { whatsapp ->
                run {
                    println("conectado ${whatsapp.isConnected}")
                    qrCode = ""
                }
            }
            .connect()

        whatsapp = completableFuture
    }


    fun isConnectado() {
        if (whatsapp.get().isConnected) return
        log.warn("Reconectando")
        whatsapp.newIncompleteFuture<Whatsapp>()
        this.init()
    }


    fun enviarMensagem(pacientePacienteRequestPostDTO: PacienteRequestPostDTO): PacienteResponseDTO? {
        whatsappConectado()
        whatsappExiste(pacientePacienteRequestPostDTO.numero)
        val pacienteResponseDTO = rabbitMQRestService.enviarMensagem(pacientePacienteRequestPostDTO)
        return pacienteResponseDTO
    }


    private fun whatsappConectado() {
        if (whatsapp.getNow(null) == null) throw WhatsappNaoConectadoException()
    }


    private fun whatsappExiste(numero: String) {
        val jid = Jid.of(numero)
        if (whatsapp.get().hasWhatsapp(jid).getNow(false)) return
        throw WhatsappNaoExisteException(numero)
    }

}