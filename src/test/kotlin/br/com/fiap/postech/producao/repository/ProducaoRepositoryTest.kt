package br.com.fiap.postech.producao.repository

import br.com.fiap.postech.producao.module.Producao
import br.com.fiap.postech.producao.module.Status
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.random.Random

class ProducaoRepositoryTest {

    @Mock
    private lateinit var producaoRepository:  ProducaoRepository
    private lateinit var openMock: AutoCloseable

    @BeforeEach
    fun setup() {
        openMock = MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    fun tearDown() = openMock.close()

    @Test
    fun devePermitirCadastrarProducao() {
        // Arrange
        val producao = gerarProducao()

        Mockito.`when`(producaoRepository.save(Mockito.any(Producao::class.java))).thenReturn(producao)

        // Act
        val producaoRegistrado = producaoRepository.save(producao)

        // Assert
        Assertions.assertThat(producaoRegistrado)
            .isInstanceOf(Producao::class.java)
            .isNotNull
            .isEqualTo(producao)

        Mockito.verify(producaoRepository, Mockito.times(1))
            .save(Mockito.any(Producao::class.java))
    }

    @Test
    fun devePermitirBuscarProducaosPorId() {
        // Arrange
        val producao = gerarProducao()

        Mockito.`when`(producaoRepository.findById(producao.id)).thenReturn(Optional.of(producao))

        // Act
        val producaoRecebidoOpcional = producao.id.let { producaoRepository.findById(it) }

        // Assert
        Assertions.assertThat(producaoRecebidoOpcional).isPresent

        producaoRecebidoOpcional.ifPresent { producaoRecebido ->
            Assertions.assertThat(producaoRecebido)
                .isInstanceOf(Producao::class.java)
                .isEqualTo(producao)
        }

        producao.id.let {
            Mockito.verify(producaoRepository, Mockito.times(1))
                .findById(it)
        }
    }

    @Test
    fun devePermitirListarProducoes() {
        // Arrange
        val producao1 = gerarProducao()
        val producao2 = gerarProducao()
        val listaProducoes = listOf(producao1, producao2)

        Mockito.`when`(producaoRepository.findAll()).thenReturn(listaProducoes)

        // Act
        val producoesRecebidoOpcional = producaoRepository.findAll()

        // Assert
        Assertions.assertThat(producoesRecebidoOpcional)
            .hasSize(2)
            .containsExactlyInAnyOrder(producao1, producao2)

        Mockito.verify(producaoRepository, Mockito.times(1))
            .findAll()
    }

    @Test
    fun devePermitirAtualizarProducao() {
        // Arrange
        val producao = gerarProducao()

        val novoStatus = Status.PRONTO
        producao.status = novoStatus

        Mockito.`when`(producaoRepository.findById(producao.id)).thenReturn(Optional.of(producao))

        // Act
        val producaoRecebidoOpcional = producao.id.let { producaoRepository.findById(it) }

        // Assert
        Assertions.assertThat(producaoRecebidoOpcional).isPresent

        producaoRecebidoOpcional.ifPresent { producaoRecebido ->
            Assertions.assertThat(producaoRecebido)
                .isInstanceOf(Producao::class.java)
                .isEqualTo(producao)

            Assertions.assertThat(producaoRecebido.status)
                .isEqualTo(novoStatus)
        }

        producao.id.let {
            Mockito.verify(producaoRepository, Mockito.times(1))
                .findById(it)
        }
    }

    @Test
    fun devePermitirDeletarProducao() {
        // Arrange
        val producao = gerarProducao()

        // Act
        producaoRepository.delete(producao)
        val producaoRecebidoOpcional = producao.id.let { producaoRepository.findById(it) }

        // Assert
        Assertions.assertThat(producaoRecebidoOpcional).isEmpty

        Mockito.verify(producaoRepository, Mockito.times(1))
            .delete(producao)
    }

    private fun gerarProducao(): Producao {
        return Producao(
            id = Random.nextLong(1000000)
        )
    }
}