package com.git.devdioge.whatsapp_api

import com.git.devdioge.whatsapp_api.controller.dtos.PacienteRequestPostDTO
import com.git.devdioge.whatsapp_api.controller.dtos.PacienteResponseDTO
import com.git.devdioge.whatsapp_api.service.N8NRestService
import com.git.devdioge.whatsapp_api.service.PacienteRestService
import com.git.devdioge.whatsapp_api.service.WhatsappService
import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.model.jid.Jid
import it.auties.whatsapp.model.jid.JidProvider
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.commons.util.ReflectionUtils
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.mockito.kotlin.whenever
import org.springframework.test.util.ReflectionTestUtils
import java.lang.reflect.Method
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
    private lateinit var mockPacienteRestService: PacienteRestService

    @Mock
    private lateinit var mockWhatsapp: Whatsapp

    @Mock
    private lateinit var mockN8nService : N8NRestService

    lateinit var whatsappService: WhatsappService

    @BeforeEach
    fun setup() {
        val future = CompletableFuture.completedFuture(mockWhatsapp)
        whatsappService = WhatsappService(future, mockPacienteRestService, mockN8nService )
    }


    @Test
    fun `test Quando salvar um paciente valido, Deve retornar o paciente salvo`() {
        whenever(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .thenReturn(CompletableFuture.completedFuture(true))

        given(mockPacienteRestService.salvar(any()))
            .willReturn(pacientePacienteResponseDTO)


        val respota = whatsappService.enviarMensagem(pacientePacienteRequestPostDTO)

        assertNotNull(respota)
        then(mockPacienteRestService)
            .should()
            .salvar(any())
    }


    @Test
    fun `test Quando salvar Paciente com numero nao valido, Deve salvar com campo numero com valor NUMERO_NAO_EXISTE`() {
        given(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .willReturn(CompletableFuture.completedFuture(false))
        given(mockPacienteRestService.salvar(any()))
            .willReturn(pacientePacienteResponseDTONumeroNaoExiste)

        val resposta = whatsappService.enviarMensagem(pacientePacienteRequestPostDTO)

        assertNotNull(resposta)
        assertEquals("NUMERO_NAO_EXISTE", resposta.numero)
        then(mockWhatsapp).should().hasWhatsapp(any<JidProvider>())
        then(mockPacienteRestService).should().salvar(any())
    }


    @Test
    fun `test Quando chamar whatsappExiste e enviar um numero valido, Deve retornar true`() {
        whenever(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .thenReturn(CompletableFuture.completedFuture(true))



        val kfun = whatsappService::class.declaredFunctions.first { it.name == "whatsappExiste" }
        kfun.isAccessible = true
        val resultado = kfun.call(whatsappService,"81984768748") as Boolean

        assertTrue(resultado)
        then(mockWhatsapp)
            .should()
            .hasWhatsapp(any<JidProvider>())
    }

    @Test
    fun `test Quando chamar whatsappExiste e enviar um numero nao valido, Deve retornar false`() {
        whenever(mockWhatsapp.hasWhatsapp(any<JidProvider>()))
            .thenReturn(CompletableFuture.completedFuture(false))
        val kfun = WhatsappService::class.declaredFunctions.first { it.name == "whatsappExiste" }
        kfun.isAccessible = true

        val resultado = kfun.call(whatsappService, "8199999999" ) as Boolean

        assertFalse (resultado)
        then(mockWhatsapp)
            .should()
            .hasWhatsapp(any<JidProvider>())
    }


    @Test
    fun `test Quando EnviarMensagem, Deve ` () {


    }


}