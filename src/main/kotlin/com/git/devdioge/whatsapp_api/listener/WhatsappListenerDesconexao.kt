package com.git.devdioge.whatsapp_api.listener

import com.git.devdioge.whatsapp_api.service.WhatsappService
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


/**
 * @author Diogenes Santos
 * Classe é responsável por monitorar a desconexão do whatsapp, caso esteja desconectado reiniciaremos a instância
 * Do completableFuture<Whatsapp> geraremos um novo qrCode e assim uma nova conexão.
 */
@Component
class WhatsappListenerDesconexao(private val whatsappService: WhatsappService) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())


    /**
     * Com scope courotines criamos uma thread paralela para ficar monitorando se o whatsapp está conectado
     * caso não esteja e chamada o métod0 isConectado() aonde possui sua validação e se desconectado reinciaremos a
     * instância.
     */
    @PostConstruct
    private fun iniciandoVerificacaoWhatsappService() {
        scope.launch {
            while (isActive) {
            delay(30_000)
            log.warn("${Thread.currentThread()} || verificando se está conectado...")
            whatsappService.isConnectado() } }
    }


}