package com.piccup.piccup.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.UserSchoolRequestsQuery
import com.piccup.piccup.databinding.RequestItemBinding
import com.piccup.piccup.util.extensions.visible
import java.util.*


class MainAdapter(
    private val context: Context,
    private var myData: ArrayList<UserSchoolRequestsQuery.UserSchoolRequest?>,
    private val listener: OnRequestSelect
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            RequestItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    interface OnRequestSelect {
        fun setOnRequestSelect(model: UserSchoolRequestsQuery.UserSchoolRequest?)
    }

    fun setMyData(myData: ArrayList<UserSchoolRequestsQuery.UserSchoolRequest?>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = myData[position]

        holder._binding?.studentName?.text = model?.student_name
        holder._binding?.status?.text = model?.status
        holder._binding?.requestId?.text = "Request id  #${model?.id}"
        holder._binding?.date?.text = "Created at ${model?.created_at}"
        if (!model?.response.isNullOrEmpty()) {
            holder._binding?.reason?.visible()
            holder._binding?.response?.text = model?.response
        }
        /*when(model?.status)
        {
            ""
        }*/

        holder.itemView.setOnClickListener {
            listener.setOnRequestSelect(model)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: RequestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var _binding: RequestItemBinding? = null

        init {
            this._binding = binding
        }

    }


}