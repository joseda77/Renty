package com.esteban.rentcar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.esteban.rentcar.model.Car
import kotlinx.android.synthetic.main.activity_main.*
import com.esteban.rentcar.Adapter.*
import android.content.Intent



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        my_recycler.setHasFixedSize(true)
        my_recycler.layoutManager = LinearLayoutManager(this)

        search_button.setOnClickListener {

            //**********


            var list = getList()
            my_recycler.adapter = CarAdapter(this,list)




            var text: String=""

            text = "Pick up: "+ pick_up.text.toString() + " Type: "+ type.text + " From: " + from.text + " To: " +to.text
            Toast.makeText(this,text, Toast.LENGTH_LONG).show()
        }


    }

    fun getList(): ArrayList<Car> {
        var list = ArrayList<Car>()
        list.add(Car(1, "Type","Brand ", "model ", "price ","rental_id 1","rental_name"))
        list.add(Car(2, "Type","Brand ", "model ", "price ","rental_id 2","rental_name"))
        list.add(Car(3, "Type","Brand ", "model ", "price ","rental_id 3","rental_name"))
        list.add(Car(4, "Type","Brand ", "model ", "price ","rental_id 4","rental_name"))
        list.add(Car(5, "Type","Brand ", "model ", "price ","rental_id 5","rental_name"))

        return list;
    }

    fun eventSearch(){
        var list = getList()



    }
}
