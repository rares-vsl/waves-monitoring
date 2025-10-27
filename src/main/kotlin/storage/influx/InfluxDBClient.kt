package storage.influx

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import com.influxdb.client.kotlin.InfluxDBClientKotlin
import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import io.github.cdimascio.dotenv.Dotenv

class InfluxDBClient private constructor() {
    private val dotenv = Dotenv.load()

    private val influxDBClient: InfluxDBClient =
        InfluxDBClientFactory.create(
            dotenv.get("INFLUX_URL"),
            dotenv.get("INFLUX_TOKEN").toCharArray(),
            dotenv.get("INFLUX_ORG"),
            dotenv.get("INFLUX_BUCKET"),
        )

    private val influxDBClient2: InfluxDBClientKotlin =
        InfluxDBClientKotlinFactory.create(
            dotenv.get("INFLUX_URL"),
            dotenv.get("INFLUX_TOKEN").toCharArray(),
            dotenv.get("INFLUX_ORG"),
            dotenv.get("INFLUX_BUCKET")
        )

    companion object {
        @Volatile
        private var istance: storage.influx.InfluxDBClient? = null

        fun getInstance(): storage.influx.InfluxDBClient =
            istance ?: synchronized(this) {
                istance ?: InfluxDBClient().also { istance = it }
            }
    }

    fun getClient(): InfluxDBClient = influxDBClient
//
//    fun close() {
//        influxDBClient.close()
//        istance = null
//    }
}
