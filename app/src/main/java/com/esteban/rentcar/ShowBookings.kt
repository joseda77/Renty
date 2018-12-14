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
        progressDialog.setMessage("Getting Bookings")
        progressDialog.setCancelable(false)
        progressDialog.show()
        progressDialog.dismiss()
        val intent = intent
        val idToken = intent.getStringExtra("userToken")
        Toast.makeText(this, idToken, Toast.LENGTH_LONG).show()

        /*cancel.setOnClickListener {
            val intent: Intent = Intent(this, oauth::class.java)
            startActivity(intent)
        }*/

        /*val rentyServe by lazy {
            IRentyApi.create("https://renty-web.herokuapp.com/")
        }

        val rentyServe2 by lazy {
            IRentyApi.create("https://renty-ruby.herokuapp.com/")
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
*/
    }
}
