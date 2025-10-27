package interfaces.webapi.plugins

import domain.Measurement
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureRequestValidation(){
    install(RequestValidation) {
        validate<Measurement> { measurement ->
            val errors = mutableListOf<String>()
            if (measurement.hookupID.isBlank()) errors.add("hookupID is missing")
            if (errors.isNotEmpty()) ValidationResult.Invalid(errors.joinToString(", ")) else ValidationResult.Valid
        }
    }
}