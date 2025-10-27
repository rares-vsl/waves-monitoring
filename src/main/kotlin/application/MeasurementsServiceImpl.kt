package application

import domain.ConsumptionType
import domain.Measurement
import domain.ports.MeasurementsRepository
import domain.ports.MeasurementsService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.Hook
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class MeasurementsServiceImpl(
    private val repository: MeasurementsRepository,
) : MeasurementsService {
    private val client =
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000L
                connectTimeoutMillis = 10_000L
                socketTimeoutMillis = 10_000L
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

    override suspend fun createMeasurement(hookupID: String, value: Float) {
        val response: HttpResponse =
            client.get("http://localhost:3001/api/internal/smart-furniture-hookups/$hookupID") {
            }

        val json = Json.parseToJsonElement(response.bodyAsText()).jsonObject
        val type = json["utilityType"]?.jsonPrimitive?.content

        println("utilityType: $type")

        if(type == null){
            throw IllegalStateException("Type is missing in the first smart furniture item")
        }

        repository.saveMeasurement(Measurement(hookupID, ConsumptionType.valueOf(type.uppercase()), value))
    }
}
