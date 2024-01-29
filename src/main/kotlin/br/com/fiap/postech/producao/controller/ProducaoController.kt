package br.com.fiap.postech.producao.controller

import br.com.fiap.postech.producao.dto.ProducaoDto
import br.com.fiap.postech.producao.dto.StatusDto
import br.com.fiap.postech.producao.service.ProducaoService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/producao")
class ProducaoController {
    @Autowired
    private val service: ProducaoService? = null

    @GetMapping
    fun listarTodos(): MutableList<ProducaoDto>? {
        return service?.obterTodos()
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable @NotNull id: Long?): ResponseEntity<ProducaoDto> {
        val dto: ProducaoDto? = service?.obterPorId(id)

        return ResponseEntity.ok(dto)
    }

    @GetMapping("/porta")
    fun retornaPorta(@Value("\${local.server.port}") porta: String?): String {
        return String.format("Requisição respondida pela instância executando na porta %s", porta)
    }

    @PostMapping
    fun criaProducao(
        @RequestBody @Valid dto: ProducaoDto?,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<ProducaoDto> {
        val producaoRealizado: ProducaoDto? = service?.criarProducao(dto)

        val endereco = uriBuilder.path("/producao/{id}").buildAndExpand(producaoRealizado?.id).toUri()

        return ResponseEntity.created(endereco).body(producaoRealizado)
    }

    @PutMapping("/{id}/status")
    fun atualizaStatus(@PathVariable id: Long?, @RequestBody status: StatusDto?): ResponseEntity<ProducaoDto> {
        val dto: ProducaoDto? = status?.let { service?.atualizaStatus(id, it) }

        return ResponseEntity.ok(dto)
    }
}
