package com.git.devdioge.whatsapp_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
class WhatsappApiApplication

fun main(args: Array<String>) {
	runApplication<WhatsappApiApplication>(*args)
}
