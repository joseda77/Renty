package com.esteban.rentcar.services

object BookingCarRequest {
    data class Request(val token: String, val carId: Int, val bookingDate: String, val pickup: String,
                       val pickupDate: String, val deliverPlace: String, val deliverDate: String)
}

object DeleteBookingCarRequest {
    data class Request(val token: String, val bookingId: String)
}