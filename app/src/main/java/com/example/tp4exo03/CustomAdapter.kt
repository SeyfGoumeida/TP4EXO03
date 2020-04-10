package com.example.tp4exo03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class CustomAdapter (val taskList: ArrayList<Task>,var activity : MainActivity) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val taskname =  itemView.findViewById(R.id.task_name) as TextView
        val taskdate =  itemView.findViewById(R.id.task_date) as TextView
        val deletetask =  itemView.findViewById(R.id.deletetask) as Button


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task:Task=taskList[position]

        holder?.taskname?.text=task.name
        holder?.taskdate?.text=task.date
        holder.deletetask.setOnClickListener{
            var db =DataBaseHelper(activity)
            Toast.makeText(activity,"{  "+task.name+" } :has been deleted",Toast.LENGTH_SHORT).show()
            db.deleteData(task)
            (activity as MainActivity).refresh.performClick()


        }


    }
}