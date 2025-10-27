package storage.influx

import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import domain.ConsumptionType
import domain.Measurement
import domain.ports.MeasurementsRepository
import java.time.Instant
import kotlin.getValue

class InfluxMeasurementRepository : MeasurementsRepository {
    private val influxDbClient: InfluxDBClient = InfluxDBClient.getInstance()

     private val writeApiBlocking by lazy { influxDbClient.getClient().writeApiBlocking }

    private val queryApi by lazy {influxDbClient.getClient().queryApi}

    override fun saveMeasurement(measurement: Measurement) {
        val type = measurement.consumptionType.name.lowercase()

        val point =
            Point
                .measurement(type)
                .addTag("hookupID", measurement.hookupID)
                .addField("value", measurement.consumptionValue)
                .time(Instant.now(), WritePrecision.NS)

        println(point)
        writeApiBlocking.writePoint(point)
    }

    override fun showActiveHookups() {
        val query = """
            from(bucket: "monitoring_service")
            |> range(start: -10s)
            |> filter(fn: (r) => r._field == "value" and r._value > 0)
            |> keep(columns: ["hookupID"])
            |> unique(column: "hookupID")
        """.trimIndent()

        val tables = queryApi.query(query)

        println("Active Hookups:")
        tables.forEach { table ->
            table.records.forEach { record ->
                val hookupID = record.getValueByKey("hookupID")
                println("HookupID: $hookupID")
            }
        }
    }

    private fun HourlyConsumption(type: ConsumptionType){
        val query = """
            from(bucket: "monitoring_service")
              |> range(start: -24h)
              |> filter(fn: (r) => r._measurement == "$type" and r._field == "value")
              |> aggregateWindow(
                  every: 1h,
                  createEmpty: false,
                  fn: (column, tables=<-) => tables
                    |> integral(unit: 1h)
                    |> group(columns: ["_start", "_stop", "_time"], mode: "by")
                    |> sum(column: "_value")
              )
        """.trimIndent()
    }

    override fun showHourlyConsumption(){
        val query = """
            from(bucket: "monitoring_service")
              |> range(start: -24h)
              |> filter(fn: (r) => r._measurement == "electricity" and r._field == "value")
              |> aggregateWindow(
                  every: 1h,
                  createEmpty: false,
                  fn: (column, tables=<-) => tables
                    |> integral(unit: 1h)
                    |> group(columns: ["_start", "_stop", "_time"], mode: "by")
                    |> sum(column: "_value")
              )
        """.trimIndent()

        val tables = queryApi.query(query)

        println("Electricity Consumption (24h):")
        tables.forEach { table ->
            table.records.forEach { record ->
                val time = record.time
                val value = record.value
                val measurement = record.measurement
                println("Time: $time, Measurement: $measurement, Value: $value")
            }
        }
    }
}
