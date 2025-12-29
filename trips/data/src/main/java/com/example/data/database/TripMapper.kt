package com.example.data.database

import com.example.domain.trips.Trip
import java.time.LocalDate

fun TripEntity.toDomain(): Trip = Trip(
    id = id,
    title = title,
    startDate = LocalDate.ofEpochDay(startDateDay),
    endDate = LocalDate.ofEpochDay(endDateDay),
)

fun Trip.toEntity(): TripEntity = TripEntity(
    id = id,
    title = title,
    startDateDay = startDate.toEpochDay(),
    endDateDay = endDate.toEpochDay(),
)
