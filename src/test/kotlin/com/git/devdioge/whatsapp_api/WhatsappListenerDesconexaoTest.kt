package com.git.devdioge.whatsapp_api

import com.git.devdioge.whatsapp_api.listener.WhatsappListenerDesconexao
import com.git.devdioge.whatsapp_api.service.WhatsappService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.then
import org.mockito.kotlin.whenever
import org.springframework.test.util.ReflectionTestUtils
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class WhatsappListenerDesconexaoTest {

    @Mock
    private lateinit var whatsappService : WhatsappService

    @InjectMocks
    private lateinit var whatsappListenerDesconexao: WhatsappListenerDesconexao


    @Test
    fun `test QUANDO a aplicacao iniciar, DEVE checar se whatsapp esta conectado`() {
        doNothing().whenever(whatsappService).isConnectado()

        ReflectionTestUtils.invokeMethod<Any>(whatsappListenerDesconexao,
            "iniciandoVerificacaoWhatsappService")

        TimeUnit.SECONDS.sleep(31)

        then(whatsappService)
            .should()
            .isConnectado()
    }

}