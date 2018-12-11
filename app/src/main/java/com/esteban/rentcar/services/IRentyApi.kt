package com.esteban.rentcar.services

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface IRentyApi {

    @GET("cars/search")
    fun getCarList(@Query("from") from: String,
                   @Query("to") to: String,
                   @Query("type") type: String,
                   @Query("pickup") pickUp: String): Observable<List<ListCarsResponse.Result>>

    /*
    @GET("cars/search")
    fun getCarList(@Query("from") from: String,
                   @Query("to") to: String,
                   @Query("type") type: String,
                   @Query("pickup") pickUp: String): Observable<ListCarResponse.Result>*/

    @GET("cars/{id}/")
    fun getCarDetails(@Path("id") id: Int): Observable<DetailCarResponse.Result>

    @POST("booking")
    fun bookingCar(@Body request: BookingCarRequest.Request): Observable<List<BookingCarResponse.Result>>

    @POST("mybookings")
    fun getBookings(@Body idToken: String): Observable<List<ListCarsResponse.Result>>

    companion object {
        fun create(url: String): IRentyApi {
            var retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build()

            return retrofit.create(IRentyApi::class.java)
        }
    }


}