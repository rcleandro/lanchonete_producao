package br.com.fiap.postech.producao.service

import br.com.fiap.postech.producao.dto.ProducaoDto
import br.com.fiap.postech.producao.dto.StatusDto
import br.com.fiap.postech.producao.module.Producao
import br.com.fiap.postech.producao.repository.ProducaoRepository
import jakarta.persistence.EntityNotFoundException
import lombok.RequiredArgsConstructor
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
class ProducaoService {
    @Autowired
    private val repository: ProducaoRepository? = null

    @Autowired
    private val modelMapper: ModelMapper? = null


    fun obterTodos(): MutableList<ProducaoDto>? {
        return repository?.findAll()?.stream()
            ?.map { p -> modelMapper!!.map(p, ProducaoDto::class.java) }
            ?.collect(Collectors.toList())
    }

    fun obterPorId(id: Long?): ProducaoDto {
        val producao: Producao? = id?.let {
            repository?.findById(it)
                ?.orElseThrow { EntityNotFoundException() }
        }

        return modelMapper!!.map(producao, ProducaoDto::class.java)
    }

    fun criarProducao(dto: ProducaoDto?): ProducaoDto {
        val producao: Producao = modelMapper!!.map(dto, Producao::class.java)
        repository?.save(producao)

        return modelMapper.map(producao, ProducaoDto::class.java)
    }

    fun atualizaStatus(id: Long?, dto: StatusDto): ProducaoDto {
        val producao: Producao? = id?.let { repository?.findById(it)
            ?.orElseThrow { EntityNotFoundException() } }

        producao?.status = dto.status
        repository?.atualizaStatus(dto.status, producao)
        return modelMapper!!.map(producao, ProducaoDto::class.java)
    }
}