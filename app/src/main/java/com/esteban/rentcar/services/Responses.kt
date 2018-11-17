package com.esteban.rentcar.services

object ListCarsResponse {
    data class Result(val id: Int, val brand: String,val thumbnail: String, val price: Int,
                   val type: String, val model: String, val rental: Rental)
    data class Rental(val id: Int, val name: String)
}

object DetailCarResponse {
    data class Result(val id: Int, val brand: String,val thumbnail: String, val price: Int,
                      val type: String, val model: String, val rental: Rental,
                      val plate: String, val rating: Int, val capacity: Int,
                      val transmission: String, val doors: Int, val color: String, val kms: Int,
                      val picture: List<String>)

    data class Rental(val id: Int, val name: String)
}