package com.esteban.rentcar.services

object ListCarsResponse {
    data class Result(val id: Int, val brand: String,val thumbnail: String, val price: Int,
                   val type: String, val model: String, val rental: Rental)
    data class Rental(val id: Int, val name: String)
}

/*object ListCarResponse {
    data class Result(val cars: List<Car>)
    data class Car(val id: Int, val brand: String,val thumbnail: String, val price: Int,
                      val type: String, val model: String, val rental: Rental)
    data class Rental(val id: Int, val name: String)
}*/

object DetailCarResponse {
    data class Result(val id: Int, val brand: String,val thumbnail: String, val price: Int,
                      val type: String, val model: String, val rental: Rental,
                      val plate: String, val rating: Int, val capacity: Int,
                      val transmission: String, val doors: Int, val color: String, val kms: Int,
                      val pictures: List<String>)

    data class Rental(val id: Int, val name: String)
}

object BookingCarResponse {
    data class Result(val statusCode: Int)
}

object UserCarsResponse {
    data class Result(val bookingID: String, val uid: String, val car: ListCarsResponse.Result,
                      val bookingDate: String, val pickup: String, val pickupDate: String,
                      val deliverPlace: String, val deliverDate: String, val rental: DetailCarResponse.Rental)
}