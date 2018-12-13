package com.esteban.rentcar

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.esteban.rentcar.model.Car
import com.esteban.rentcar.services.BookingCarRequest
import com.esteban.rentcar.services.IRentyApi
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_car_detail.*

class CarDetail : AppCompatActivity() {
    var disposable: Disposable? = null
    val pythonId = 967543461
    val rubyId = 123456789

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Retraiving data")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val intent = intent
        val id = Integer.parseInt(intent.getStringExtra("idCar"))
        val rentalID = Integer.parseInt(intent.getStringExtra("rentalID"))
        val pickup = intent.getStringExtra("pickup")
        val from = intent.getStringExtra("from")
        val to = intent.getStringExtra("to")

        rent.setOnClickListener {
            /*val intent: Intent = Intent(this, oauth::class.java)
            startActivity(intent)*/

            val token = "LLego algo de firebase"
            val today = "today"
            val deliverPlace = "aeropuerto"
            var bookingRequest = BookingCarRequest.Request(token,id, today, pickup, from, deliverPlace,
                    to)

            if(rentalID == pythonId){
                val rentyServe by lazy {
                    IRentyApi.create("https://renty-web.herokuapp.com/")
                }

                disposable = rentyServe.bookingCar(bookingRequest).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { response ->

                                }
                                ,
                                { error ->
                                    Toast.makeText(this,
                                            "No se puede reservar el vehiculo en Python",
                                            Toast.LENGTH_LONG).show()
                                }
                        )
            } else if (rentalID == rubyId) {
                val rentyServe2 by lazy {
                    IRentyApi.create("https://renty-ruby.herokuapp.com/")
                }

                disposable = rentyServe2.bookingCar(bookingRequest).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { response ->

                                }
                                ,
                                { error ->
                                    Toast.makeText(this,
                                            "No se puede reservar el vehiculo en Ruby",
                                            Toast.LENGTH_LONG).show()
                                }
                        )
            }
        }


        val rentyServe by lazy {
            IRentyApi.create("https://renty-web.herokuapp.com/")
        }

        val rentyServe2 by lazy {
            IRentyApi.create("https://renty-ruby.herokuapp.com/")
        }

        if (rentalID == 123456789) {
            disposable = rentyServe2.getCarDetails(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->
                                type.text = response.type
                                brand.text = response.brand
                                model.text = response.model
                                price.text = response.price.toString()
                                rental_name.text = response.rental.name
                                thumbnail.layoutParams.height = 400
                                thumbnail.layoutParams.width = 400
                                Picasso.get().load(response.thumbnail).into(thumbnail)
                                plate.text = response.plate
                                rating.text = response.rating.toString()
                                capacity.text = response.capacity.toString()
                                transmission.text = response.transmission
                                doors.text = response.doors.toString()
                                color.text = response.color
                                kms.text = response.kms.toString()

                                for (url in response.pictures) {
                                    var newView: ImageView //crea una instancia de imageview en tiempo de ejecucion
                                    newView = ImageView(this)//La configura en el contexto, en este caso sería car_Details.kt
                                    layout_scroll.addView(newView)//layout_scroll es el LInear layour que hay dentro del scroll, dentro van a ir todos los imagesviews. Añada la instancia de imageview creada
                                    //ACÁ PONER EL TAMAÑO DE LA IMAGEN
                                    newView.layoutParams.height = 900
                                    newView.layoutParams.width = 900
                                    //Acá se le setea el url al imageview con Picasso,
                                    Picasso.get().load(url).into(newView)
                                }
                                progressDialog.dismiss()
                            },
                            { error ->
                                Toast.makeText(this,"No se pueden cargar los datos",Toast.LENGTH_LONG).show()
                                Log.e("errrrrrrr", error.toString())
                                progressDialog.dismiss()
                            })


        }else if(rentalID == 967543461) {
            disposable = rentyServe.getCarDetails(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->
                                type.text = response.type
                                brand.text = response.brand
                                model.text = response.model
                                price.text = response.price.toString()
                                rental_name.text = response.rental.name
                                thumbnail.layoutParams.height = 400
                                thumbnail.layoutParams.width = 400
                                Picasso.get().load(response.thumbnail).into(thumbnail)
                                plate.text = response.plate
                                rating.text = response.rating.toString()
                                capacity.text = response.capacity.toString()
                                transmission.text = response.transmission
                                doors.text = response.doors.toString()
                                color.text = response.color
                                kms.text = response.kms.toString()

                                for (url in response.pictures) {
                                    var newView: ImageView //crea una instancia de imageview en tiempo de ejecucion
                                    newView = ImageView(this)//La configura en el contexto, en este caso
                                                                    // sería car_Details.kt

                                    layout_scroll.addView(newView)//layout_scroll es el LInear layour que hay dentro
                                    // del scroll, dentro van a ir todos los imagesviews. Añada la instancia de
                                    // imageview creada
                                    //ACÁ PONER EL TAMAÑO DE LA IMAGEN
                                    newView.layoutParams.height = 900
                                    newView.layoutParams.width = 900
                                    //Acá se le setea el url al imageview con Picasso,
                                    Picasso.get().load(url).into(newView)
                                }
                                progressDialog.dismiss()
                            },
                            { error ->
                                Toast.makeText(this,"No se pueden cargar los datos",Toast.LENGTH_LONG).show()
                                Log.e("errrrrrrr", error.toString())
                                progressDialog.dismiss()
                            })
        }
    }
}
