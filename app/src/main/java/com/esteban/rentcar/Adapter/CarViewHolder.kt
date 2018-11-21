package com.esteban.rentcar.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.car_layout.view.*

class CarViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val txt_id = itemView.id_car
    val txt_type= itemView.type
    val txt_brand = itemView.brand
    val txt_model = itemView.model
    val img_thumbnail = itemView.thumbnail
    val txt_price = itemView.price
    val txt_rental_id = itemView.rental_id
    val txt_rental_name= itemView.rental_name
    val btn_see_car = itemView.see_car_button
    val btn_rent_car = itemView.rent
    val context = itemView.getContext()






}