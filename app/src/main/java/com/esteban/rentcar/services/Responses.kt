package com.esteban.rentcar.services

object CategoryResponse {
    data class Result(val status: String, val entity: List<Entity>, val error: String)
    data class Entity(val id: Int, val nombre: String, val tipo: Int)
}

object StudentResponse {
    data class Result(val status: String, val entity: List<Entity>, val error: String)
    data class Entity(val id: Int, val nombres: String, val apellidos: String, val grupoxestudiante__fila: Int,
                      val grupoxestudiante__columna: Int, val sexo_biologico: Int, val descripcion: String)
}

object ListCarsResponse {
    data class Result(val id: Int, val brand: String, val model: String, val thumbnail: String,
                      val price: Int, val type: String, val rental: Rental)
    data class Rental(val id: Int, val name: String)
}