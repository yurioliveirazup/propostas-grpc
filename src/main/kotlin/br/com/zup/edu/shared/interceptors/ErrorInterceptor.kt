package br.com.zup.edu.shared.interceptors

import br.com.zup.edu.propostas.Proposta
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@InterceptorBean(ErrorAdvice::class)
class ErrorInterceptor : MethodInterceptor<Any, Any> {

    override fun intercept(context: MethodInvocationContext<Any, Any>): Any? {

        return try {
            context.proceed()
        } catch (e: Exception) {

            val responseObserver = context.parameterValues[1] as StreamObserver<*>

            val status = when(e) {
                is ConstraintViolationException ->  Status.INVALID_ARGUMENT.withCause(e)
                                                                           .withDescription(e.message)
                else -> Status.UNKNOWN.withCause(e)
                                      .withDescription("um erro inesperado aconteceu")
            }


            val statusRuntimeException = StatusRuntimeException(status)

            responseObserver.onError(statusRuntimeException)
        }
    }

}