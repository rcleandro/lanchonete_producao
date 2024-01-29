package br.com.fiap.postech.producao.repository

import br.com.fiap.postech.producao.module.Producao
import br.com.fiap.postech.producao.module.Status
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProducaoRepositoryIT {

    @Autowired
    private lateinit var producaoRepository: ProducaoRepository

    @Test
    fun devePermitirCriarTabela() {
        val totalDeRegistros = producaoRepository.count()
        Assertions.assertThat(totalDeRegistros).isNotNegative()
    }

    @Test
    fun devePermitirCadastrarProducao() {
        // Arrange
        val producao = this.gerarProducao()

        // Act
        val producaoRegistrado = producaoRepository.save(producao)

        // Assert
        Assertions.assertThat(producaoRegistrado)
            .isInstanceOf(Producao::class.java)
            .isNotNull
            .isEqualTo(producao)
    }

    @Test
    fun devePermitirBuscarProducaoPorId() {
        // Arrange
        val producao = this.gerarProducao()
        producaoRepository.save(producao)

        // Act
        val producaoRecebidoOpcional = producaoRepository.findById(producao.id)

        // Assert
        Assertions.assertThat(producaoRecebidoOpcional).isPresent

        producaoRecebidoOpcional.ifPresent { clienteRecebido ->
            Assertions.assertThat(clienteRecebido)
                .isInstanceOf(Producao::class.java)
                .isEqualTo(producao)
        }
    }

    @Test
    fun devePermitirListarProducoes() {
        // Arrange
        val producao1 = this.gerarProducao()
        producaoRepository.save(producao1)

        val producao2 = this.gerarProducao()
        producaoRepository.save(producao2)

        // Act
        val producoesRecebidoOpcional = producaoRepository.findAll()

        // Assert
        Assertions.assertThat(producoesRecebidoOpcional)
            .hasSize(2)
            .containsExactlyInAnyOrder(producao1, producao2)
    }

    @Test
    fun devePermitirAtualizarProducao() {
        // Arrange
        val producao = this.gerarProducao()
        producaoRepository.save(producao)

        val novoStatus = Status.PRONTO
        producao.status = novoStatus
        producaoRepository.save(producao)

        // Act
        val producaoRecebidoOpcional = producaoRepository.findById(producao.id)

        // Assert
        Assertions.assertThat(producaoRecebidoOpcional).isPresent

        producaoRecebidoOpcional.ifPresent { clienteRecebido ->
            Assertions.assertThat(clienteRecebido)
                .isInstanceOf(Producao::class.java)
                .isEqualTo(producao)

            Assertions.assertThat(clienteRecebido.status)
                .isEqualTo(novoStatus)
        }
    }

    @Test
    fun devePermitirDeletarProducao() {
        // Arrange
        val producao = this.gerarProducao()
        producaoRepository.save(producao)

        // Act
        producaoRepository.delete(producao)
        val producaoOpcional = producaoRepository.findById(producao.id)

        // Assert
        Assertions.assertThat(producaoOpcional)
            .isEmpty
    }

    private fun gerarProducao(): Producao {
        return Producao(
            id = Random.nextLong(1000000)
        )
    }
}