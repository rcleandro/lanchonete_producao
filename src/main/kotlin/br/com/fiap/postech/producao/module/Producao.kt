package br.com.fiap.postech.producao.module

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime

@Entity
@Table(name = "producao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
data class Producao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotNull
    var dataHora: LocalDateTime = LocalDateTime.now(),

    @NotNull
    @Enumerated(EnumType.STRING)
    var status: Status = Status.AGUARDANDO,
)