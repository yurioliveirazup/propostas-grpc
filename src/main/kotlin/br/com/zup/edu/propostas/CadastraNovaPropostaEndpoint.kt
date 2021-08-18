package br.com.zup.edu.propostas

import br.com.zup.edu.CadastraPropostaServiceGrpc
import br.com.zup.edu.PropostaRequest
import br.com.zup.edu.PropostaResponse
import io.grpc.stub.StreamObserver
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class CadastraNovaPropostaEndpoint(val propostaRepository: PropostaRepository) : CadastraPropostaServiceGrpc.CadastraPropostaServiceImplBase() {


    override fun cadastra(request: PropostaRequest,
                          responseObserver: StreamObserver<PropostaResponse>) {

        // validar os dados
        val proposta = request.toModel()

        propostaRepository.save(proposta)

        // devolver o id da proposta
        val response = PropostaResponse.newBuilder()
                                       .setId(proposta.id!!)
                                       .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

}

fun PropostaRequest.toModel() : Proposta {

    return Proposta(this.nome,
            this.email,
            this.documento,
            this.endereco,
            BigDecimal(this.salario))
}
