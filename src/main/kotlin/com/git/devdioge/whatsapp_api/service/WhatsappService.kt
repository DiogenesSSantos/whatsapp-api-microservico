package com.git.devdioge.whatsapp_api.service

import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.api.Whatsapp.webBuilder
import it.auties.whatsapp.model.jid.Jid
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class WhatsappService(private var whatsapp: CompletableFuture<Whatsapp> = CompletableFuture<Whatsapp>()) {
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

     fun whatsappExiste(numero : String) : Boolean {
        val jidProvide = Jid.of(numero)
        val hasWhatsapp = whatsapp.get().hasWhatsapp { jidProvide }.get()
        return hasWhatsapp
    }

}