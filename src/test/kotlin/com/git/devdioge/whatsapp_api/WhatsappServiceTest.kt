package com.git.devdioge.whatsapp_api

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.exception.WhatsappNaoExisteException
import com.git.devdioge.whatsapp_api.service.RabbitMQRestService
import com.git.devdioge.whatsapp_api.service.WhatsappService
import com.sun.org.apache.xml.internal.security.Init
import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.model.jid.JidProvider
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.mockito.kotlin.whenever
import java.lang.reflect.InvocationTargetException
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class WhatsappServiceTest {

    private lateinit var pacientePacienteRequestPostDTO: PacienteRequestPostDTO
    private lateinit var pacientePacienteRequestPostDTONumeroNaoExiste: PacienteRequestPostDTO
    private lateinit var pacientePacienteResponseDTO: PacienteResponseDTO
    private lateinit var pacientePacienteResponseDTONumeroNaoExiste: PacienteResponseDTO


    @BeforeAll
    fun setur() {
        pacientePacienteRequestPostDTO = PacienteRequestPostDTO(
            "Diogenes Santos",
            "81984768748", "Alto da balança", "Ortopedista", "Marcado",
            LocalDateTime.of(2026, 5, 3, 12, 22)
        )

        pacientePacienteRequestPostDTONumeroNaoExiste = PacienteRequestPostDTO(
            "Diogenes Santos",
            "Numero_não_existe", "Alto da balança", "Ortopedista", "Marcado",
            LocalDateTime.of(2026, 5, 3, 12, 22)
        )

        pacientePacienteResponseDTO = PacienteResponseDTO(
            "1", "Diogenes Santos",
            "81984768748", "Alto da balança", "Ortopedista", "Marcado",
            LocalDateTime.of(2026, 5, 3, 12, 22),
            LocalDateTime.now()
        )

        pacientePacienteResponseDTONumeroNaoExiste = pacientePacienteResponseDTO.copy(numero = "NUMERO_NAO_EXISTE")
    }



    @Mock
    private lateinit var mockWhatsapp: Whatsapp

    @Mock
    private lateinit var mockRabbitMQRestService : RabbitMQRestService

    lateinit var whatsappService: WhatsappService

    @BeforeEach
    fun setup() {
        val future = CompletableFuture.completedFuture(mockWhatsapp)
        whatsappService = WhatsappService(future,mockRabbitMQRestService )
    }


    @Test
    fun `test Quando salvar um paciente valido, Deve retornar o paciente salvo`() {
        whenever(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .thenReturn(CompletableFuture.completedFuture(true))
        whenever(mockRabbitMQRestService.enviarMensagem(any()))
            .thenReturn(pacientePacienteResponseDTO)

        val responseDTO = whatsappService.enviarMensagem(pacientePacienteRequestPostDTO)

        assertNotNull(responseDTO)
        then(mockRabbitMQRestService)
            .should()
            .enviarMensagem(any())
    }



    @Test
    fun `test Quando chamar whatsappExiste e enviar um numero valido, metodo executara ate o fim`() {
        whenever(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .thenReturn(CompletableFuture.completedFuture(true))

        val kfun = whatsappService::class.declaredFunctions.first { it.name == "whatsappExiste" }
        kfun.isAccessible = true
        kfun.call(whatsappService,"81984768748")


        then(mockWhatsapp)
            .should()
            .hasWhatsapp(any<JidProvider>())
    }

    @Test fun `test Quando chamar whatssappExiste com um numero invalid lanca um exceptio WhatsappNaoExisteException`() {
        whenever(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .thenReturn(CompletableFuture.completedFuture(false))
        val kfun = WhatsappService::class.declaredFunctions.first { it.name == "whatsappExiste" }
        kfun.isAccessible = true


        val ite = assertThrows(InvocationTargetException::class.java) {
            kfun.call(whatsappService, "8199999999") }


        assertTrue(ite.cause is WhatsappNaoExisteException)
        then(mockWhatsapp).should().hasWhatsapp(any<JidProvider>())
    }

}