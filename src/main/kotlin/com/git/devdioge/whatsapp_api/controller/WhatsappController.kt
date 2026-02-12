package com.git.devdioge.whatsapp_api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(path = ["/whatsapp"])
class WhatsappController {


    @GetMapping
    fun test() : ResponseEntity<Any> {
        return ResponseEntity.status(200).body("Olá mundo.")
    }

}