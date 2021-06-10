package br.com.zup.edu.propostas

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import java.lang.annotation.Documented
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import kotlin.reflect.KClass

@CPF
@ConstraintComposition(CompositionType.OR) // isValidCpf(documento) || isValidCnpj(documento)
@CNPJ
@ReportAsSingleViolation // the error reports of each individual composing constraint are ignored
@Constraint(validatedBy = [ ]) // we don't need a validator :-)
@Documented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class CpfOuCnpj(
        val message: String = "nao eh um documento valido",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = []
)
