package br.com.zup.edu.propostas

import br.com.zup.edu.CadastraPropostaServiceGrpc
import br.com.zup.edu.PropostaRequest
import br.com.zup.edu.PropostaResponse
import br.com.zup.edu.propostas.grpc.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
open class CadastraNovaPropostaEndpoint(val repository: PropostaRepository) : CadastraPropostaServiceGrpc.CadastraPropostaServiceImplBase() {

    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    @ErrorHandler
    open override fun cadastra(request: PropostaRequest,
                               responseObserver: StreamObserver<PropostaResponse>) {

        if (repository.existsByDocumento(request.documento)) {
            throw PropostaJaExistenteException("proposta ja existente para este documento")
        }

        val proposta = repository.save(request.toModel()) // pode lançar uma ConstraintViolationException

        responseObserver.onNext(PropostaResponse.newBuilder()
                                            .setId(proposta.id!!)
                                            .build())
        responseObserver.onCompleted()
    }
}

/**
 * Extension Functions
 */
fun PropostaRequest.toModel() : Proposta {

    return Proposta(this.nome,
        this.email,
        this.documento,
        this.endereco,
        BigDecimal(this.salario))
}
