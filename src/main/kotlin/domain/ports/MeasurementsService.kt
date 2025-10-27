package domain.ports

import domain.Measurement

interface MeasurementsService {
    suspend fun createMeasurement(hookupID: String, value: Float)
}
