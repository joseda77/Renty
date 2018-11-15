package com.esteban.rentcar.services

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface IRentyApi {

    @GET("/categorias")
    fun getCategories(): Observable<CategoryResponse.Result>

    @GET("/estudiantes")
    fun getStudents(@Query("id_grupo") idGrupo: Int): Observable<StudentResponse.Result>

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