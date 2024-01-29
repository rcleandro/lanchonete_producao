package br.com.fiap.postech.producao.dto

import br.com.fiap.postech.producao.module.Status
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class StatusDto {
    var status: Status = Status.AGUARDANDO
}
