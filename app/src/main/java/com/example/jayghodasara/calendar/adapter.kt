package com.example.jayghodasara.calendar

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class adapter(var context: Context, var list: ArrayList<CalendarPojo>) : RecyclerView.Adapter<adapter.Vholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {

        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)

        return Vholder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vholder, position: Int) {

        var user: CalendarPojo = list[position]
        holder.name.text = user.event
        holder.number.text = user.date
    }


    inner class Vholder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView = view.findViewById(R.id.textView1)
        var number: TextView = view.findViewById(R.id.date)
    }
}