package com.esteban.rentcar.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.esteban.rentcar.R
import com.esteban.rentcar.model.Car

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
      //  holder.img_thumbnail.setImageResource(carList[position].thumbnail)
        holder.txt_price.text = carList[position].price
        holder.txt_rental_id.text = carList[position].rental_id
        holder.txt_rental_name.text = carList[position].rental_name

    }


}