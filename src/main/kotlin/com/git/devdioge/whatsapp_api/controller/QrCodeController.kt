package com.git.devdioge.whatsapp_api.controller

import com.git.devdioge.whatsapp_api.infraestrutura.GeradorDeQrCode
import com.git.devdioge.whatsapp_api.service.WhatsappService
import com.git.devdioge.whatsapp_api.swagger.OpenApiQrCodeController
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO


/**
 * @author DiogenesSantos
 * leia documentação do controller no swaggerOpenApi da aplicação
 */

@RestController
@RequestMapping(path = ["/whatsapp/conexao"])
class QrCodeController(private val whatsappService: WhatsappService,
                       private val geradorDeQrCode: GeradorDeQrCode) : OpenApiQrCodeController {


    @GetMapping(
        value = ["/qrcode"],
        produces = ["image/png"]
    )
    override fun getQrCodeImage(): ResponseEntity<InputStreamResource>? {
        val qrCodeWhatsappService = whatsappService.qrCode
        if (qrCodeWhatsappService!!.isBlank()) return ResponseEntity.noContent().build()

        val qrCodeImage = geradorDeQrCode.gerarQrCodeDeStringParaImage(qrCodeWhatsappService)
        val byteArrayOutPutStream = ByteArrayOutputStream()
        ImageIO.write(qrCodeImage, "png",byteArrayOutPutStream)
        val byteArrayInputStream = ByteArrayInputStream(byteArrayOutPutStream.toByteArray())

        val inputStreamResource = InputStreamResource(byteArrayInputStream)

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(inputStreamResource)
    }

}