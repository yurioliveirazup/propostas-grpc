package br.com.zup.edu.propostas

import br.com.zup.edu.NovaPropostaGrpcRequest
import br.com.zup.edu.NovaPropostaGrpcResponse
import br.com.zup.edu.NovaPropostaGrpcServiceGrpc
import br.com.zup.edu.propostas.shared.handlers.ErrorAroundHandler
import io.grpc.stub.StreamObserver
import java.math.BigDecimal
import javax.inject.Singleton
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Singleton
@ErrorAroundHandler
class NovaPropostaEndpoint(val propostaRepository: PropostaRepository,
                           val validador: Validator,
) : NovaPropostaGrpcServiceGrpc.NovaPropostaGrpcServiceImplBase() {

    override fun cadastra(request: NovaPropostaGrpcRequest,
                          responseObserver: StreamObserver<NovaPropostaGrpcResponse>) {
       //  Coloca o código do nosso controller

        // converter request para Modelo de Dominio
        val proposta = request.paraPropostaValida(validador)

        // salvar no banco
        val salvo = propostaRepository.save(proposta)

        val response = NovaPropostaGrpcResponse.newBuilder()
                .setId(salvo.id ?: throw IllegalStateException("id nao pode estar nulo"))
                .build()

        // dar uma resposta
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}


fun NovaPropostaGrpcRequest.paraPropostaValida(validador: Validator) : Proposta {
    val proposta =  Proposta(nome = this.nome,
            email = email,
            documento = documento,
            salario = BigDecimal(salario),
            endereco = endereco,
    )

    val erros = validador.validate(proposta)
    if (! erros.isEmpty()) { // ha erros
        throw ConstraintViolationException(erros)
    }

    return proposta
}
