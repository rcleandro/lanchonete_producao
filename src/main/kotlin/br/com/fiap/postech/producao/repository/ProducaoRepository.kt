package br.com.fiap.postech.producao.repository

import br.com.fiap.postech.producao.module.Producao
import br.com.fiap.postech.producao.module.Status
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ProducaoRepository : JpaRepository<Producao, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Producao p set p.status = :status where p = :pedido")
    fun atualizaStatus(status: Status, pedido: Producao?)
}