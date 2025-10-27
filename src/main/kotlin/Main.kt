import application.MeasurementsServiceImpl
import domain.ConsumptionType
import domain.Measurement
import storage.influx.InfluxMeasurementRepository

suspend fun main() {
    val measurementsRepository = InfluxMeasurementRepository()
    val measurementsService = MeasurementsServiceImpl(measurementsRepository)


    // Send the measurement to InfluxDB
    println("Sending measurement to InfluxDB...")

    println(ConsumptionType.ELECTRICITY.name.lowercase())
//    measurementsRepository.showHourlyConsumption()


//    measurementsService.createMeasurement(measurement)
//    println("Measurement sent successfully!")
}
