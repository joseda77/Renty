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

        var items_of_pick_up = arrayOf("Aeropuerto")
        val adapter_pick_up = ArrayAdapter(this, android.R.layout.simple_spinner_item, items_of_pick_up)
        // Configura un diseño depslegable al adpater
        adapter_pick_up.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Enlazar el adaper creado con el spinner del view
        pick_up!!.setAdapter(adapter_pick_up)
            //Configuración del Spinner
        var items_of_type = arrayOf("Económico", "Compacto", "SUV", "Lujo")
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
        refreshList()
        updateDateInView()
        // Evento -> Click en calendarios, Mostrar DatePickerDialog que es configurado con OnDateSetListener
        txtDateFrom!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                btnDateCurrent = 1
                DatePickerDialog(this@MainActivity, dataSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
        txtDateTo!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                btnDateCurrent = 2
                DatePickerDialog(this@MainActivity, dataSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        //Evento -> Search Button
        search_button.setOnClickListener {
            pickUp = pick_up.selectedItem.toString()
            typeCar = type.selectedItem.toString()
            fromDate = txtDateFrom!!.text.toString()
            toDate = txtDateTo!!.text.toString()
            var typeCode : String = ""

            if (typeCar == "Económico") typeCode = "1"
            else if (typeCar == "Compacto") typeCode = "2"
            else if (typeCar == "SUV") typeCode = "3"
            else if (typeCar == "Lujo") typeCode = "4"

            if (pickUp == "Aeropuerto") pickUp = "mde"

            val sdf = SimpleDateFormat("YYYY-MM-dd")
            val today = sdf.format(Date())

            if (fromDate.compareTo(today,true) < 0) {
                Toast.makeText(this,
                        "No puedes rentar vehiculos en el pasado",
                        Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }

            if(fromDate.compareTo(toDate,true) > 0) {
                Toast.makeText(this,
                        "No puedes rentar vehiculos si la fecha de devolución es menor a la fecha de entrega",
                        Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val rentyServe by lazy {
                IRentyApi.create("https://renty-web.herokuapp.com/")
            }

            val rentyServe2 by lazy {
                IRentyApi.create("https://renty-ruby.herokuapp.com/")
            }

            listCar = ArrayList()
            var progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Getting cars")
            progressDialog.setCancelable(false)
            progressDialog.show()

            disposable = rentyServe.getCarList(fromDate, toDate, typeCode, pickUp).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->
                                Log.i("OOOOOOO","Tra eesti "+response.toString())
                                //response.cars cuando los metodos retornen lo que deban
                                for (car in response) {
                                    listCar.add(Car(car.id, car.type, car.brand, car.model,
                                            car.price.toString(), car.rental.id.toString(),
                                            car.rental.name, car.thumbnail, pickUp, fromDate, toDate))
                                }
                                refreshList()
                            }
                            ,
                            { error ->
                                progressDialog.dismiss()
                                Toast.makeText(this,
                                        "No se encuentran vehiculos con los parametros ingresados en Python",
                                        Toast.LENGTH_LONG).show()
                            }
                    )

            disposable = rentyServe2.getCarList(fromDate, toDate, typeCode, pickUp).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->

                                for (car in response) {
                                    listCar.add(Car(car.id, car.type, car.brand, car.model,
                                            car.price.toString(), car.rental.id.toString(),
                                            car.rental.name, car.thumbnail, pickUp, fromDate, toDate))
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
                    )
            //listCar = getList()
            closeKeyBoard()
            //ocultar panel
            values_container.visibility = View.GONE
            show_hide_button.text = "SHOW"
            showPanel = false
            search_button.visibility = View.GONE
            refreshList()
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

    /*fun getList(): ArrayList<Car> {
        var list = ArrayList<Car>()

        list.add(Car(2, "Type","Brand ", "model ", "price ",
                "rental_id 2","rental_name", "https://i.imgur.com/IyEp7mf.jpg"))
        list.add(Car(3, "Type","Brand ", "model ", "price ",
                "rental_id 3","rental_name", "https://i.imgur.com/XEgyzCW.jpg"))
        list.add(Car(4, "Type","Brand ", "model ", "price ",
                "rental_id 4","rental_name", "https://i.imgur.com/72bQoTW.jpg"))
        list.add(Car(5, "Type","Brand ", "model ", "price ",
                "rental_id 5","rental_name", "https://i.imgur.com/fbPHUCn.jpg"))
        list.add(Car(6, "Type","Brand ", "model ", "price ",
                "rental_id 1","rental_name", "https://i.imgur.com/1wrP717.jpg"))

        return list
    }*/

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
