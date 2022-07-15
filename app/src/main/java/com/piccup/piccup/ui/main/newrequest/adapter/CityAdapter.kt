package com.piccup.piccup.ui.main.newrequest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.GetCitiesQuery
import com.piccup.piccup.databinding.TextItemBinding
import java.util.*


class CityAdapter(
    private val context: Context,
    private var myData: ArrayList<GetCitiesQuery.City?>,
    private val listener: OnCitySelect
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            TextItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    interface OnCitySelect {
        fun setOnCitySelect(model: GetCitiesQuery.City?)
    }

    fun setMyData(myData: ArrayList<GetCitiesQuery.City?>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = myData[position]

        holder._binding?.text?.text = model?.name

        holder.itemView.setOnClickListener {
            listener.setOnCitySelect(model)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: TextItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var _binding: TextItemBinding? = null

        init {
            this._binding = binding
        }

    }


}