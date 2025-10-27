package interfaces.webapi.routes.internal

import domain.ports.MeasurementsService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.requests.MeasurementRequest

fun Route.measurementRoutes(measurementsService: MeasurementsService) {
    route("/measurements") {
        get {
            call.respond("OK!")
        }
        post {
            val smartFurnitureId = call.request.queryParameters["smart_furniture_hookup_id"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                "Missing or malformed smart_furniture_hookup_id"
            )


            val req = call.receive<MeasurementRequest>()

            measurementsService.createMeasurement(smartFurnitureId, req.realTimeConsumption)




//            val measurement = Measurement(
//                hookupID = x.,
//                consumptionType = ConsumptionType.valueOf(x.type.uppercase()),
//                consumptionValue = x.provision_rate
//            )
//
//            measurementsService.createMeasurement(measurement)
//
            call.respond(HttpStatusCode.OK, mapOf("message" to "Data received"))
        }
    }
}
