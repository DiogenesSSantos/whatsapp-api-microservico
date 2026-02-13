package com.git.devdioge.whatsapp_api

import com.git.devdioge.whatsapp_api.listener.WhatsappListenerDesconexao
import com.git.devdioge.whatsapp_api.service.WhatsappService
import it.auties.whatsapp.api.Whatsapp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class WhatsappServiceTest {


    @Mock
    private val completableFuture : CompletableFuture<Whatsapp>? = null

    @Mock
    private val whatsappService : WhatsappService ? = null

    @InjectMocks
    private val whatsappListenerDesconexao : WhatsappListenerDesconexao ? = null

    @Test
    fun `test QUANDO iniciar whatsappService, DEVE verificar se esta conectado a cada 30 segundos` () {
        willDoNothing().given(whatsappService)!!.isConnectado()

        ReflectionTestUtils.invokeMethod<WhatsappListenerDesconexao>(whatsappListenerDesconexao!!,
            "iniciandoVerificacaoWhatsappService")
        TimeUnit.SECONDS.sleep(35)

        then(whatsappService)
            .should()!!.isConnectado()
    }


    @Test
    fun `test QUANDO chamar o metodo whatsappExiste(numero) com whatsapp valido, DEVE retornar boolean true` () {
        val numeroValido = "558184768748"
        val numeroIsValidoTrue = true
        given(whatsappService!!.whatsappExiste(numeroValido)).willReturn(true)

        val returnValido = whatsappService.whatsappExiste(numeroValido)

        then(whatsappService)
            .should().whatsappExiste(numeroValido)
        Assertions.assertEquals(numeroIsValidoTrue, returnValido)
    }

}