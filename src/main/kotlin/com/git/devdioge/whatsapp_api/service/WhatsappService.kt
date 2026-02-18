package com.git.devdioge.whatsapp_api.service

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.exception.WhatsappNaoConectadoException
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
    private val pacienteRestService: PacienteRestService,
    private val n8NRestService: N8NRestService
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

    private fun salvar(pacientePacienteRequestPostDTO: PacienteRequestPostDTO): PacienteResponseDTO {
        val pacienteSalvo = when(whatsappExiste(pacientePacienteRequestPostDTO.numero)){
            true -> pacienteRestService.salvar(pacientePacienteRequestPostDTO)
            false -> { numeroNaoExite(pacientePacienteRequestPostDTO)
                pacienteRestService.salvar(pacientePacienteRequestPostDTO)}
        }
        return pacienteSalvo
    }



    fun enviarMensagem(pacientePacienteRequestPostDTO: PacienteRequestPostDTO) : PacienteResponseDTO {
        whatsappConectado()
        val pacienteSalvo = salvar(pacientePacienteRequestPostDTO)
        if (!pacienteSalvo.numero.equals("NUMERO_NAO_EXISTE")) {
            n8NRestService.enviarMensagem(pacientePacienteRequestPostDTO)
        }
        return pacienteSalvo
    }


    private fun whatsappExiste(numero: String): Boolean {
        val jid = Jid.of(numero)
        return whatsapp.get().hasWhatsapp(jid).get()
    }

    private fun numeroNaoExite(pacientePacienteRequestPostDTO: PacienteRequestPostDTO) {
        pacientePacienteRequestPostDTO.numero = "NUMERO_NAO_EXISTE"
    }

    private fun whatsappConectado() {
        if (whatsapp.getNow(null) == null) throw WhatsappNaoConectadoException()
    }

}