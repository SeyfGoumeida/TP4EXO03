package com.example.tp4exo03

import android.annotation.SuppressLint
import android.database.DatabaseErrorHandler
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var picker: DatePicker? = null

    @SuppressLint("SetTextI18n", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //DATA BASE
        val context =this
        //Liste
        val Tasks=ArrayList<Task>()

        // get reference to views
        val task = findViewById(R.id.task) as EditText
        val tasks = findViewById(R.id.recyclerView) as RecyclerView
        val add = findViewById(R.id.add_button) as Button
        val submit = findViewById(R.id.submit) as Button
        val cancel = findViewById(R.id.cancel) as Button
        val validat = findViewById(R.id.validate) as Button
        val date_layout =  findViewById(R.id.data_time) as LinearLayout
        val date =  findViewById(R.id.date) as TextView
        val datePicker = findViewById<DatePicker>(R.id.datePicker1)
        val refresh = findViewById(R.id.refresh) as Button
        // set on-click listeners

        //1 ADD
        add.setOnClickListener {
            if (date_layout.visibility==INVISIBLE)
            {
                datePicker.visibility=View.VISIBLE
                submit.visibility=View.VISIBLE
                cancel.visibility=View.VISIBLE
                add.visibility=View.INVISIBLE
                task.visibility=View.VISIBLE
                add_task.visibility=View.VISIBLE
                tasks.visibility=View.INVISIBLE
                Toast.makeText(context, "Select a date and time.", Toast.LENGTH_SHORT).show()
            }
            else
            {   datePicker.visibility=View.INVISIBLE
                date_layout.visibility = View.INVISIBLE
            }

        }
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        //2
        submit.setOnClickListener{
            date.text = ""+datePicker.year+" / "+datePicker.month+" / "+datePicker.dayOfMonth
            date_layout.visibility = View.VISIBLE
            datePicker.visibility=View.INVISIBLE
            submit.visibility=View.INVISIBLE
            cancel.visibility=View.INVISIBLE
            add.visibility=View.INVISIBLE


        }
        //3
        cancel.setOnClickListener{
            date_layout.visibility = View.INVISIBLE
            datePicker.visibility=View.INVISIBLE
            submit.visibility=View.INVISIBLE
            cancel.visibility=View.INVISIBLE
            add.visibility=View.VISIBLE
            task.visibility=View.INVISIBLE
            add_task.visibility=View.INVISIBLE
            tasks.visibility=View.VISIBLE

        }
        //4
        task.setOnClickListener{
            tasks.visibility=View.INVISIBLE
        }

        //Liste of tasks
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        var db =DataBaseHelper(context)

        // Affichage et Refresh
         fun refresh(){
            val list=db.readData()
            Tasks.removeAll(Tasks)
            for (i in 0.. list.size-1) {Tasks.add(db.readData()[i])}
            val adapter = CustomAdapter(Tasks,this)
            recyclerView.adapter=adapter
        }
        //5
        refresh.setOnClickListener{
            refresh()
        }

        //Add and refresh
        validat.setOnClickListener{

            if(task.text.toString().length>0 )
            {
                tasks.visibility=View.VISIBLE
                add.visibility=View.VISIBLE
                date_layout.visibility = View.INVISIBLE
                task.visibility=View.INVISIBLE
                add_task.visibility=View.INVISIBLE

                //adding the new task info to the data base
                var newtask =Task(task.text.toString(),""+datePicker.year+" / "+datePicker.month+" / "+datePicker.dayOfMonth)
                var db =DataBaseHelper(context)
                db.insertData(newtask)

                refresh()

            } else {
                Toast.makeText(context,"Give your task a name first ",Toast.LENGTH_SHORT).show()
            }
        }
        refresh()
    }

}
