package com.esteban.rentcar

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.esteban.rentcar.services.IRentyApi
import io.reactivex.disposables.Disposable

class ShowBookings : AppCompatActivity() {

    var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_bookings)

       var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Retraiving data")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val intent = intent
        val id = intent.getStringExtra("userId")
        Toast.makeText(this, "Este es el id de usuario " + id, Toast.LENGTH_LONG).show()

        /*cancel.setOnClickListener {
            val intent: Intent = Intent(this, oauth::class.java)
            startActivity(intent)
        }*/

        val rentyServe by lazy {
            IRentyApi.create("https://renty-web.herokuapp.com/")
        }

        /*disposable = rentyServe.getBookingCars(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            for(car in response) {
                                listCar.add(Car(car.id, car.type, car.brand, car.model,
                                        car.price.toString(), car.rental.id.toString(),
                                        car.rental.name, car.thumbnail,pickUp,fromDate,toDate))
                            }
                            refreshList()
                            progressDialog.dismiss()
                        },
                        { error ->
                            progressDialog.dismiss()
                            Toast.makeText(this,
                                    "No se encuentran vehiculos con los parametros ingresados en Ruby",
                                    Toast.LENGTH_LONG).show()
                        }
                )*/

    }
}
