package br.com.fiap.postech.producao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ProducaoApplication

fun main(args: Array<String>) {
    runApplication<ProducaoApplication>(*args)
}
