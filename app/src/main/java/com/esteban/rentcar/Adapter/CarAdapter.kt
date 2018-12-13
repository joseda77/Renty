package com.esteban.rentcar.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.esteban.rentcar.CarDetail
import com.esteban.rentcar.R
import com.esteban.rentcar.R.id.from
import com.esteban.rentcar.R.id.to
import com.esteban.rentcar.model.Car
import com.esteban.rentcar.oauth
import com.esteban.rentcar.services.BookingCarRequest
import com.esteban.rentcar.services.IRentyApi
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class CarAdapter (internal var context: Context, internal var carList: ArrayList<Car>) : RecyclerView.Adapter<CarViewHolder>() {


    var disposable: Disposable? = null
    val pythonId = "967543461"
    val rubyId = "123456789"
    //para especificar el layout de nuestro listado así como componer la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {


        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.car_layout,parent,false)
        return CarViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    //para retornar el ítem actual
    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.txt_id.text = carList[position].id.toString()
        holder.txt_type.text = carList[position].type
        holder.txt_brand.text = carList[position].brand
        holder.txt_model.text = carList[position].model
        holder.txt_price.text = carList[position].price
        holder.txt_rental_id.text = carList[position].rental_id
        holder.txt_rental_name.text = carList[position].rental_name


        Picasso.get().load(carList[position].thumbnail_url).placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.img_thumbnail)


        holder.btn_see_car.setOnClickListener{
            val intent :Intent = Intent(holder.context, CarDetail::class.java)
            intent.putExtra("idCar",carList[position].id.toString())
            intent.putExtra("rentalID", carList[position].rental_id)
            intent.putExtra("pickup", carList[position].pickup)
            intent.putExtra("from",carList[position].from)
            intent.putExtra("to", carList[position].to)
            holder.context.startActivity(intent)

        }

        holder.btn_rent_car.setOnClickListener {
           /* val intent: Intent = Intent(holder.context,oauth::class.java)
            holder.context.startActivity(intent)*/
            val token = "LLego algo de firebase"
            val today = "today"
            val deliverPlace = "aeropuerto"
            var bookingRequest = BookingCarRequest.Request(token,carList[position].id, today,
                    carList[position].pickup, carList[position].from, deliverPlace,
                    carList[position].to)

            if(carList[position].rental_id == pythonId){
                rentCar(1,bookingRequest)
            } else if (carList[position].rental_id == rubyId) {
                rentCar(2,bookingRequest)
            }
        }

    }

    fun rentCar(value: Int, request: BookingCarRequest.Request){
        if(value == 1){
            val rentyServe by lazy {
                IRentyApi.create("https://renty-web.herokuapp.com/")
            }

            disposable = rentyServe.bookingCar(request).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->

                            }
                            ,
                            { error ->
                                Toast.makeText(this.context,
                                        "No se puede reservar el vehiculo en Python",
                                        Toast.LENGTH_LONG).show()
                            }
                    )
        } else if (value == 2) {
            val rentyServe2 by lazy {
                IRentyApi.create("https://renty-ruby.herokuapp.com/")
            }

            disposable = rentyServe2.bookingCar(request).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->

                            }
                            ,
                            { error ->
                                Toast.makeText(this.context,
                                        "No se puede reservar el vehiculo en Ruby",
                                        Toast.LENGTH_LONG).show()
                            }
                    )
        }
    }
}