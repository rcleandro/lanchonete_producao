package br.com.fiap.postech.producao.dto

import br.com.fiap.postech.producao.module.Status
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ProducaoDto {
    var id: Long? = null
    var dataHora: LocalDateTime = LocalDateTime.now()
    var status: Status = Status.AGUARDANDO
}