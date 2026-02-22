package com.git.devdioge.whatsapp_api.exception


class WhatsappNaoExisteException(numero : String) :
    RuntimeException("Esse numero ${numero} de whatsapp não existe ou invalido.") {
}