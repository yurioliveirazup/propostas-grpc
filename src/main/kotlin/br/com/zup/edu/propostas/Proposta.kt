package br.com.zup.edu.propostas

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Entity
class Proposta(@field:NotBlank val nome: String,
               @field:NotBlank @field:Email val email: String,
               @field:NotBlank val documento: String,
               @field:PositiveOrZero @field:NotNull val salario: BigDecimal,
               @field:NotBlank val endereco: String,
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null

}