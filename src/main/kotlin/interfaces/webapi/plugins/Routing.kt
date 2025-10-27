package interfaces.webapi.plugins

import domain.ports.MeasurementsService
import interfaces.webapi.routes.internal.measurementRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(measurementsService: MeasurementsService) {
    routing {
        route("/api/internal") {
            measurementRoutes(measurementsService)
        }
    }
}
