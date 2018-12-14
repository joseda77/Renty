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


    //Para reservar es desde aquí
    @FormUrlEncoded
    @POST("booking")
    fun bookingCar(@Field("token") token: String,
                   @Field("carId") carId: Int,
                   @Field("bookingDate") bookingDate: String,
                   @Field("pickup") pickUp: String,
                   @Field("pickupDate") pickupDate: String,
                   @Field("deliverPlace") deliverPlace: String,
                   @Field("deliverDate") deliverDate: String): Observable<BookingCarResponse.Result>

    @POST("booking")
    fun bookingCar2(@Body request: BookingCarRequest.Request): Observable<BookingCarResponse.Result>

    @GET("booking/{token}")
    fun getBookingCars(@Path("token") token: String) : Observable<List<UserCarsResponse.Result>>

    @PUT("booking")
    fun deleteBookingCar(@Body request: DeleteBookingCarRequest.Request): Observable<List<UserCarsResponse.Result>>

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