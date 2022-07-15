package com.piccup.piccup.ui.main.newrequest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.piccup.piccup.databinding.TextItemBinding
import java.util.*


class GradesAdapter(
    private val context: Context,
    private var myData: ArrayList<String>,
    private val listener: OnGradeSelect
) : RecyclerView.Adapter<GradesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            TextItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    interface OnGradeSelect {

        fun setOnGradeSelect(model: String)
    }

    fun setMyData(myData: ArrayList<String>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = myData[position]


        holder._binding?.text?.text = model


        holder.itemView.setOnClickListener {

            listener.setOnGradeSelect(model)

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