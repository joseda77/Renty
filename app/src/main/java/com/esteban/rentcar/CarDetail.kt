package com.esteban.rentcar

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.esteban.rentcar.services.IRentyApi
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_car_detail.*

class CarDetail : AppCompatActivity() {
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Retraiving data")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val intent = intent
        val id = Integer.parseInt(intent.getStringExtra("idCar"))

        rent.setOnClickListener {
            val intent: Intent = Intent(this,oauth::class.java)
            startActivity(intent)
        }


        val rentyServe by lazy {
            IRentyApi.create("https://renty-heroku.herokuapp.com")
        }

        disposable = rentyServe.getCarDetails(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            response ->
                            for (car in response) {
                                id_car.text = car.id.toString()
                                type.text = car.type
                                brand.text = car.brand
                                model.text = car.model
                                price.text = car.price.toString()
                                rental_id.text = car.rental.id.toString()
                                rental_id.text = car.rental.name
                                //thumbnail.layoutParams.height = 200
                                //thumbnail.layoutParams.width = 200
                                Picasso.get().load(car.thumbnail).into(thumbnail)
                                plate.text = car.plate
                                rating.text = car.rating.toString()
                                capacity.text = car.capacity.toString()
                                transmission.text = car.transmission
                                doors.text = car.doors.toString()
                                color.text = car.color
                                kms.text = car.kms.toString()

                                for(url in car.picture) {
                                    Picasso.get().load(url).into(images)
                                }
                            }
                            progressDialog.dismiss()
                        },
                        {
                            error ->
                            Log.e("errrrrrrr", error.toString())
                            progressDialog.dismiss()
                        })

    }
}
