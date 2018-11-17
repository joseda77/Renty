package com.esteban.rentcar

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.esteban.rentcar.model.Car
import kotlinx.android.synthetic.main.activity_main.*
import com.esteban.rentcar.Adapter.*
import com.esteban.rentcar.services.IRentyApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import android.content.Intent
import com.esteban.rentcar.R.id.gone
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var txtDateFrom: TextView? = null
    var txtDateTo: TextView? = null
    var calendar: Calendar = Calendar.getInstance()
    var btnDateCurrent: Int = 1
    var showPanel: Boolean = true
    var disposable: Disposable? = null
    var listCar: ArrayList<Car> = ArrayList()
    var fromDate: String = ""
    var toDate: String = ""
    var pickUp: String = ""
    var typeCar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instanciar los views en las variables
        txtDateFrom = from
        txtDateTo = to


        //Inicializar el Recycler
        my_recycler.setHasFixedSize(true)
        my_recycler.layoutManager = LinearLayoutManager(this)


            //Temporalmente el boton Search lleva a la activity de login

            val intent = Intent(this, OauthGoogle::class.java)
            startActivity(intent)


            var list = getList()
            my_recycler.adapter = CarAdapter(this, list)


            //Configuración del Spinner
        var items_of_type = arrayOf("Car type 1", "Car type 2", "Car type 3")
            // Crear el ArrayAdapter para el spinner
        val adapter_spinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, items_of_type)
            // Configura un diseño depslegable al adpater
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Enlazar el adaper creado con el spinner del view
        type!!.setAdapter(adapter_spinner)


            //Configuración de los Calendarios
            //Crear un OnDataSetListener para recibir la fecha ingresada por el usuario
            val dataSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateInView()

                }
            }

            //EVENTOS

            // Evento -> Click en calendarios, Mostrar DatePickerDialog que es configurado con OnDateSetListener
            txtDateFrom!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    btnDateCurrent = 1
                    DatePickerDialog(this@MainActivity, dataSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
                }
            })
            txtDateTo!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    btnDateCurrent = 2
                    DatePickerDialog(this@MainActivity, dataSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
                }
            })


            //Evento -> Search Button
            search_button.setOnClickListener {
                pickUp = pick_up.text.toString()
                typeCar = type.selectedItem.toString()
                fromDate = txtDateFrom!!.text.toString()
                toDate = txtDateTo!!.text.toString()
                val rentyServe by lazy {
                    IRentyApi.create("https://renty-heroku.herokuapp.com")
                }

                listCar = ArrayList()
                var progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Retraiving data")
                progressDialog.setCancelable(false)
                progressDialog.show();
                disposable = rentyServe.getCarList(fromDate, toDate, typeCar, pickUp).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { response ->
                                    Log.i("RRRResponse", response.toString())
                                    for (car in response) {
                                        listCar.add(Car(car.id, car.type, car.brand, car.model,
                                                car.price.toString(), car.rental.id.toString(),
                                                car.rental.name, car.thumbnail))
                                    }
                                    refreshList()
                                    progressDialog.dismiss()
                                }
                                ,
                                { error ->
                                    Log.e("errrror", error.toString())
                                    progressDialog.dismiss()
                                }
                        )
                var text: String = ""
                text = "Pick up: " + pick_up.text.toString() + " Type: " + type.selectedItem.toString() + " From: " + from.text + " To: " + to.text
                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
                closeKeyBoard()
            }

            //Evento -> Hide-Show Panel
            show_hide_button.setOnClickListener {
                if (showPanel == true) {
                    values_container.visibility = View.GONE
                    show_hide_button.text = "SHOW"
                    showPanel = false
                    search_button.visibility = View.GONE
                } else {
                    values_container.visibility = View.VISIBLE
                    show_hide_button.text = "HIDE"
                    showPanel = true
                    search_button.visibility = View.VISIBLE
                }
            }
        }

        fun refreshList() {
            my_recycler.adapter = null
            my_recycler.adapter = CarAdapter(this, listCar)
        }

        fun getList(): ArrayList<Car> {
            var list = ArrayList<Car>()
            list.add(Car(1, "Type", "Brand ", "model ", "price ", "rental_id 1", "rental_name", "http://i.imgur.com/DvpvklR.png"))
            /*list.add(Car(2, "Type","Brand ", "model ", "price ","rental_id 2","rental_name"))
        list.add(Car(3, "Type","Brand ", "model ", "price ","rental_id 3","rental_name"))
        list.add(Car(4, "Type","Brand ", "model ", "price ","rental_id 4","rental_name"))
        list.add(Car(5, "Type","Brand ", "model ", "price ","rental_id 5","rental_name"))
        list.add(Car(6, "Type","Brand ", "model ", "price ","rental_id 1","rental_name"))
        list.add(Car(7, "Type","Brand ", "model ", "price ","rental_id 2","rental_name"))
        list.add(Car(8, "Type","Brand ", "model ", "price ","rental_id 3","rental_name"))
        list.add(Car(9, "Type","Brand ", "model ", "price ","rental_id 4","rental_name"))
        list.add(Car(10, "Type","Brand ", "model ", "price ","rental_id 5","rental_name"))
*/
            return list;
        }

        private fun closeKeyBoard() {
            var view = this.currentFocus
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        private fun updateDateInView() {
            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            if (btnDateCurrent == 1) {
                txtDateFrom!!.text = sdf.format(calendar.getTime())
            } else if (btnDateCurrent == 2) {
                txtDateTo!!.text = sdf.format(calendar.getTime())
            }

        }
}
