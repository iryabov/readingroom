package com.github.iryabov.readingroom.repository

import com.github.iryabov.readingroom.entity.Booking
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookingRepository: CrudRepository<Booking, Int> {
}