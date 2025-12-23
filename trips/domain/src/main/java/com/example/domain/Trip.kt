package com.example.domain

import java.time.LocalDate

data class Trip(
    val id: String,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
