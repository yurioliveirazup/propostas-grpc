package br.com.zup.edu.propostas.grpc

import io.micronaut.aop.Around
import io.micronaut.core.annotation.Order
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@Around
@MustBeDocumented
@Retention(RUNTIME)
@Target(CLASS, FUNCTION)
annotation class ErrorHandler()
