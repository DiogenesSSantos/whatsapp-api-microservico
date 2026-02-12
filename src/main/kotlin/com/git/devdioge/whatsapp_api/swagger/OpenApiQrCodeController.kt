package com.git.devdioge.whatsapp_api.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity

@Tag(name = "QrCodeController ", description = "QrCodeController")
interface OpenApiQrCodeController {


    @Operation(
        summary = "Teste",
        description = "Busca o qrCode de Conexão",
        responses = [ApiResponse(
            description = "Retorna o qrCode para conexão", responseCode = "200",
            content = [Content(mediaType = "", schema = Schema())]
        ),
            ApiResponse(
                description = "Após a conexão caso necessite novamente o qrCode vai retornar no_content",
                responseCode = "204",
                content = [Content(mediaType = "", schema = Schema())]
            ), ApiResponse(
                description = "Internal error",
                responseCode = "500",
                content = [Content(mediaType = "", schema = Schema())]
            )
        ]
    )
    fun getQrCodeImage(): ResponseEntity<InputStreamResource>?
}