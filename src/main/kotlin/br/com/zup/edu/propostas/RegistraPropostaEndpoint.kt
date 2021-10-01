package br.com.zup.edu.propostas

import br.com.zup.edu.NovaPropostaRequest
import br.com.zup.edu.NovaPropostaResponse
import br.com.zup.edu.RegistraPropostaGrpcServiceGrpc
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
class RegistraPropostaEndpoint : RegistraPropostaGrpcServiceGrpc.RegistraPropostaGrpcServiceImplBase() {


    override fun registra(request: NovaPropostaRequest,
                          responseObserver: StreamObserver<NovaPropostaResponse>) {

        // validação dos campos
        // converter request para um modelo
        // salvar a proposta no banco de dados
        
        println("Recebendo nova requisição de proposta")
        println(request)


        responseObserver.onNext(NovaPropostaResponse.getDefaultInstance())
        responseObserver.onCompleted()
    }

}