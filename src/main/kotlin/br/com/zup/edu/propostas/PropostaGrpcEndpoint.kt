package br.com.zup.edu.propostas

import br.com.zup.edu.CadastraPropostaRequest
import br.com.zup.edu.CadastraPropostaResponse
import br.com.zup.edu.PropostasGrpcServiceGrpc
import io.grpc.stub.StreamObserver
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class PropostaGrpcEndpoint(val propostaRepository: PropostaRepository,
) : PropostasGrpcServiceGrpc.PropostasGrpcServiceImplBase() {

    override fun cadastra(request: CadastraPropostaRequest,
                          responseObserver: StreamObserver<CadastraPropostaResponse>) {

        // faltam as validacoes na entrada

        println("Recebendo a request da pessoa ${request.nome}")

        val novaProposta = request.paraProposta()

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


fun CadastraPropostaRequest.paraProposta() = Proposta(
        nome = this.nome,
        email = this.email,
        documento = this.documento,
        endereco = this.endereco,
        salario = BigDecimal(this.salario)
)