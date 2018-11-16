package com.esteban.rentcar.services

object ListCarsResponse {
    data class Result(val id: Int, val brand: String,val thumbnail: String, val price: Int,
                   val type: String, val model: String, val rental: Rental)
    data class Rental(val id: Int, val name: String)
}