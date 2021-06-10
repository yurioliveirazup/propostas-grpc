package br.com.zup.edu.propostas

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Entity
class Proposta(@field:NotBlank val nome: String,
               @field:NotBlank  @field:Email val email: String,
               @field:NotBlank @field:CpfOuCnpj val documento: String,
               @field:NotBlank val endereco: String,
               @field:Positive val salario: BigDecimal,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}
