package br.com.zup.edu.propostas

import br.com.zup.edu.CadastraPropostaRequest
import br.com.zup.edu.CadastraPropostaResponse
import br.com.zup.edu.PropostasGrpcServiceGrpc
import br.com.zup.edu.shared.interceptors.ErrorAdvice
import io.grpc.stub.StreamObserver
import java.math.BigDecimal
import javax.inject.Singleton
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Singleton
@ErrorAdvice
class PropostaGrpcEndpoint(val propostaRepository: PropostaRepository,
                           val validator: Validator,
) : PropostasGrpcServiceGrpc.PropostasGrpcServiceImplBase() {

    override fun cadastra(request: CadastraPropostaRequest,
                          responseObserver: StreamObserver<CadastraPropostaResponse>) {

        println("Recebendo a request da pessoa ${request.nome}")

        val novaProposta = request.paraPropostaValida(validator)

        val propostaSalva = propostaRepository.save(novaProposta)

        val response = CadastraPropostaResponse.newBuilder()
                .setId(propostaSalva.id ?: throw IllegalStateException("valor do id nulo"))
                .build()

//        val response = CadastraPropostaResponse.newBuilder()
//                .setId(propostaSalva.id!!)
//                .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()

    }
}


/**
 * @throws ConstraintViolationException caso o modelo esteja em estado invalido
 */
fun CadastraPropostaRequest.paraPropostaValida(validator: Validator) : Proposta {
    val novaProposta = Proposta(
            nome = this.nome,
            email = this.email,
            documento = this.documento,
            endereco = this.endereco,
            salario = BigDecimal(this.salario)
    )

    val erros = validator.validate(novaProposta)

    if (erros.isNotEmpty()) { // ha erros

        throw ConstraintViolationException(erros)
    }

    return novaProposta
}