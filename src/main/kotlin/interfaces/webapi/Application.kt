package interfaces.webapi

import application.MeasurementsServiceImpl
import interfaces.webapi.plugins.configureRequestValidation
import interfaces.webapi.plugins.configureRouting
import interfaces.webapi.plugins.configureSerialization
import interfaces.webapi.plugins.configureStatusPages
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import storage.influx.InfluxMeasurementRepository

fun main() {
    embeddedServer(Netty, port = 3003, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val measurementsRepository = InfluxMeasurementRepository()
    val measurementsService = MeasurementsServiceImpl(measurementsRepository)

    configureRouting(measurementsService)
    configureStatusPages()
    configureSerialization()
    configureRequestValidation()
}
