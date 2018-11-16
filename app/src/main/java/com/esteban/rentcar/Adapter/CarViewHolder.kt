package com.esteban.rentcar.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.car_layout.view.*

class CarViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val txt_id = itemView.plate
    val txt_type= itemView.rating
    val txt_brand = itemView.capacity
    val txt_model = itemView.transmission
    //val img_thumbnail = itemView.thumbnail
    val txt_price = itemView.doors
    val txt_rental_id = itemView.color
    val txt_rental_name= itemView.rental_name
    val btn_see_car = itemView.see_car_button
    val context = itemView.getContext()





}