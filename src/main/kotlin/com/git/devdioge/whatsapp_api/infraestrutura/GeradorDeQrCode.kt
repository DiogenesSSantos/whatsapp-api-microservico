package com.git.devdioge.whatsapp_api.infraestrutura

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

/**
 * @author DiogenesSantos
 * Classe que gera o nosso QrCode aparti de uma String capturada na classe WhatsappService.
 */

@Component
class GeradorDeQrCode {

    /**
     * @param string nosso qrCode em String.
     * @return BufferedImage retorno da imagem
     */
    fun gerarQrCodeDeStringParaImage(string : String) : BufferedImage {
        val qrCodeWriter = QRCodeWriter()

        val hints = HashMap<EncodeHintType , Any>()
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8")

        val bitMatrix = qrCodeWriter.encode(string,
            BarcodeFormat.QR_CODE,250,250,hints)

        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }

}