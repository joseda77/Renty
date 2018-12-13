package com.esteban.rentcar

import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.esteban.rentcar.model.Car
import com.esteban.rentcar.services.IRentyApi
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_car_detail.*
import org.junit.Assert
import org.junit.Test

class ServiceTest {


    var disposable: Disposable? = null
    var listCar: ArrayList<Car> = ArrayList()


    //init test to search
    private val rentyServePythonSearch by lazy {//Python
        IRentyApi.create("https://renty-web.herokuapp.com/")
    }
    private val fromDate: String = "2018-12-15"
    private val toDate: String = "2018-12-17"
    private val pickUp: String = "mde"
    private val typeCar: String = "4"
    @Test
    fun servicePythonOneOMoreCar(){
        listCar = ArrayList()
        disposable = rentyServePythonSearch.getCarList(fromDate,toDate ,typeCar , pickUp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size>0)
                }
    }

    @Test
    fun servicePythonWithoutResponseWithoutOneParam(){
        listCar = ArrayList()
        disposable = rentyServePythonSearch.getCarList("",toDate ,typeCar , pickUp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    @Test
    fun servicePythonWithoutResponseWithoutTwoParam(){
        listCar = ArrayList()
        disposable = rentyServePythonSearch.getCarList("",toDate ,"" , pickUp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    @Test
    fun servicePythonWithoutResponseWithoutThreeParam(){
        listCar = ArrayList()
        disposable = rentyServePythonSearch.getCarList("","" ,typeCar , "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    @Test
    fun servicePythonWithoutResponseWithoutFourParam(){
        listCar = ArrayList()
        disposable = rentyServePythonSearch.getCarList("","" ,"" , "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    private val rentyServeRuby by lazy {//Ruby
        IRentyApi.create("https://renty-ruby.herokuapp.com/")
    }
    @Test
    fun serviceRubyOneOMoreCar(){
        listCar = ArrayList()
        disposable = rentyServeRuby.getCarList(fromDate,toDate ,typeCar , pickUp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size>0)
                }
    }

    @Test
    fun serviceRubyWithoutResponseWithoutOneParam(){
        listCar = ArrayList()
        disposable = rentyServeRuby.getCarList("",toDate ,typeCar , pickUp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    @Test
    fun serviceRubyWithoutResponseWithoutTwoParam(){
        listCar = ArrayList()
        disposable = rentyServeRuby.getCarList("",toDate ,"" , pickUp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    @Test
    fun serviceRubyWithoutResponseWithoutThreeParam(){
        listCar = ArrayList()
        disposable = rentyServeRuby.getCarList("","" ,typeCar , "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }

    @Test
    fun serviceRubyWithoutResponseWithoutFourParam(){
        listCar = ArrayList()
        disposable = rentyServeRuby.getCarList("","" ,"" , "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    for (car in response) {
                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                car.price.toString(), car.rental.id.toString(),
                                car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                    }
                    assert(listCar.size==0)
                }
    }


    //end search service

    //init getcar service

    private val rentyServePythonGetCar by lazy {
        IRentyApi.create("https://renty-web.herokuapp.com/")
    }
    var id_car:String=""
    var type:String=""
    var brand:String=""
    var model:String=""
    var price:String=""
    var rental_id:String=""
    var rental_name:String=""
    var thumbnail:String=""
    var plate:String=""
    var rating:String=""
    var capacity:String=""
    var transmission:String=""
    var doors:String=""
    var color:String=""
    var kms:String=""

    @Test
    fun servicePythonGetCarNotEqualsParameters(){
        disposable = rentyServePythonGetCar.getCarDetails(2).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    id_car = response.id.toString()
                    type = response.type
                    brand = response.brand
                    model = response.model
                    price = response.price.toString()
                    rental_id = response.rental.id.toString()
                    rental_name = response.rental.name
                    thumbnail = response.thumbnail
                    plate = response.plate
                    rating = response.rating.toString()
                    capacity = response.capacity.toString()
                    transmission = response.transmission
                    doors = response.doors.toString()
                    color = response.color
                    kms = response.kms.toString()

                    Assert.assertNotEquals("",id_car)
                    Assert.assertNotEquals("",type)
                    Assert.assertNotEquals("",brand)
                    Assert.assertNotEquals("",model)
                    Assert.assertNotEquals("",price)
                    Assert.assertNotEquals("",rental_id)
                    Assert.assertNotEquals("",rental_name)
                    Assert.assertNotEquals("",thumbnail)
                    Assert.assertNotEquals("",plate)
                    Assert.assertNotEquals("",rating)
                    Assert.assertNotEquals("",capacity)
                    Assert.assertNotEquals("",transmission)
                    Assert.assertNotEquals("",doors)
                    Assert.assertNotEquals("",color)
                    Assert.assertNotEquals("",kms)
                }
    }

    private val rentyServeRubyGetCar by lazy {
        IRentyApi.create("https://renty-ruby.herokuapp.com/")
    }
    @Test
    fun serviceRubyGetCarNotEqualsParameters(){
        disposable = rentyServeRubyGetCar.getCarDetails(2).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    id_car = response.id.toString()
                    type = response.type
                    brand = response.brand
                    model = response.model
                    price = response.price.toString()
                    rental_id = response.rental.id.toString()
                    rental_name = response.rental.name
                    thumbnail = response.thumbnail
                    plate = response.plate
                    rating = response.rating.toString()
                    capacity = response.capacity.toString()
                    transmission = response.transmission
                    doors = response.doors.toString()
                    color = response.color
                    kms = response.kms.toString()

                    Assert.assertNotEquals("",id_car)
                    Assert.assertNotEquals("",type)
                    Assert.assertNotEquals("",brand)
                    Assert.assertNotEquals("",model)
                    Assert.assertNotEquals("",price)
                    Assert.assertNotEquals("",rental_id)
                    Assert.assertNotEquals("",rental_name)
                    Assert.assertNotEquals("",thumbnail)
                    Assert.assertNotEquals("",plate)
                    Assert.assertNotEquals("",rating)
                    Assert.assertNotEquals("",capacity)
                    Assert.assertNotEquals("",transmission)
                    Assert.assertNotEquals("",doors)
                    Assert.assertNotEquals("",color)
                    Assert.assertNotEquals("",kms)
                }
    }




}