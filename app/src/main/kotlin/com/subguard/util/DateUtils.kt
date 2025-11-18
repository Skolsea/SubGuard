package com.subguard.util

import com.subguard.domain.model.BillingCycle
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Calcula la próxima fecha de facturación a partir de la fecha de inicio y el ciclo de facturación.
 * La lógica es encontrar la primera fecha futura que coincida con el ciclo.
 */
fun calculateNextBillingDate(startDate: LocalDate, cycle: BillingCycle, today: LocalDate = LocalDate.now()): LocalDate {
    var nextDate = startDate

    while (nextDate.isBefore(today) || nextDate.isEqual(today)) {
        nextDate = when (cycle) {
            BillingCycle.WEEKLY -> nextDate.plusWeeks(1)
            BillingCycle.MONTHLY -> nextDate.plusMonths(1)
            BillingCycle.YEARLY -> nextDate.plusYears(1)
        }
    }
    return nextDate
}

/**
 * Calcula los días restantes entre la fecha actual y la fecha futura.
 */
fun LocalDate.daysUntil(futureDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(LocalDate.now(), futureDate)
}
