package com.esteban.rentcar.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.car_layout.view.*

class CarViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    val txt_brand = itemView.brand
    val txt_model = itemView.model
    val img_thumbnail = itemView.thumbnail
    val txt_price = itemView.price
    val txt_rental_name= itemView.rental_name
    val context = itemView.getContext()
}