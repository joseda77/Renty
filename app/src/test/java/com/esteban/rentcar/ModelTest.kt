package com.esteban.rentcar

import com.esteban.rentcar.model.Car
import org.junit.Test
import org.junit.Assert.*

class ModelTest {

    val EXPECTED_ID: Int = 11
    val EXPECTED_TYPE: String = "Economico"
    val EXPECTED_BRAND: String = "Audi"
    val EXPECTED_MODEL: String = "2010"
    val EXPECTED_THUMBNAIL_URL: String = "www.example.com/images/1.jpg"
    val EXPECTED_PRICE: String = "100000"
    val EXPECTED_RENTAL_ID: String="123456789"
    val EXPECTED_RENTAL_NAME: String ="Backend 1"

    var car = Car(11,"Economico","Audi","2010","100000",
            "123456789","Backend 1","www.example.com/images/1.jpg","aeropuerto","2018-11-15","2018-11-17")

    @Test
    fun `car object correctly created`(){
        assertEquals(EXPECTED_ID, car.id)
        assertEquals(EXPECTED_TYPE, car.type)
        assertEquals(EXPECTED_BRAND, car.brand)
        assertEquals(EXPECTED_MODEL, car.model)
        assertEquals(EXPECTED_PRICE, car.price)
        assertEquals(EXPECTED_RENTAL_ID, car.rental_id)
        assertEquals(EXPECTED_RENTAL_NAME, car.rental_name)
        assertEquals(EXPECTED_THUMBNAIL_URL, car.thumbnail_url)
    }




}