package com.git.devdioge.whatsapp_api.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDate

@ControllerAdvice
class ExceptionGlobalCapture {


    @ExceptionHandler(WhatsappNaoConectadoException::class)
    fun WhatsappNaoConectadoException(
        ex: WhatsappNaoConectadoException,
        req: HttpServletRequest
    ): ResponseEntity<Any> {

        val any = object {
            val statusCode = 404
            val mensagem = ex.message
            val timeStamp = LocalDate.now()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(any)
    }

}