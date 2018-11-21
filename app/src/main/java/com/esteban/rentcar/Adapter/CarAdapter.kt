package com.esteban.rentcar.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.esteban.rentcar.CarDetail
import com.esteban.rentcar.MainActivity
import com.esteban.rentcar.R
import com.esteban.rentcar.model.Car
import com.esteban.rentcar.oauth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.car_layout.*
import kotlinx.android.synthetic.main.car_layout.*
import kotlinx.android.synthetic.main.activity_main.*




class CarAdapter (internal var context: Context, internal var carList: ArrayList<Car>) : RecyclerView.Adapter<CarViewHolder>() {



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


        holder.btn_see_car.setOnClickListener({
            val intent1: Intent = Intent(holder.context,CarDetail::class.java)
            holder.context.startActivity(intent1)
          /*  val intent :Intent = Intent(holder.context, CarDetail::class.java)
            intent.putExtra("idCar",carList[position].id.toString())
            holder.context.startActivity(intent)
            Toast.makeText(holder.context,carList[position].id.toString(), Toast.LENGTH_LONG).show()*/

        })

        holder.btn_rent_car.setOnClickListener {
            val intent: Intent = Intent(holder.context,oauth::class.java)
            holder.context.startActivity(intent)
        }

    }


}