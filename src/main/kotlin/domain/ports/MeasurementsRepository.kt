package domain.ports

import domain.Measurement

interface MeasurementsRepository {
    fun saveMeasurement(measurement: Measurement)
    fun showActiveHookups()
    fun showHourlyConsumption()
}
