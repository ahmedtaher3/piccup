package com.piccup.piccup.ui.main.newrequest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.piccup.piccup.databinding.PackageItemBinding
import com.piccup.piccup.ui.main.newrequest.PackageModel
import com.piccup.piccup.util.extensions.greyLine
import com.piccup.piccup.util.extensions.greyText
import com.piccup.piccup.util.extensions.yellowLine
import com.piccup.piccup.util.extensions.yellowText
import java.util.*


class PackagesAdapter(
    private val context: Context,
    private var myData: ArrayList<PackageModel>,
    private val listener: OnPackageSelect
) : RecyclerView.Adapter<PackagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            PackageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    interface OnPackageSelect {

        fun setOnPackageSelect(model: PackageModel)
    }

    fun setMyData(myData: ArrayList<PackageModel>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = myData[position]


        holder._binding?.name?.text = model.name

        if (model.selected) {
            holder._binding?.name?.setTypeface(null, Typeface.BOLD);
            holder._binding?.name?.yellowText()
            holder._binding?.line?.yellowLine()

        } else {
            holder._binding?.name?.setTypeface(null, Typeface.NORMAL);
            holder._binding?.name?.greyText()
            holder._binding?.line?.greyLine()


        }


        holder.itemView.setOnClickListener {

            for (i in myData) {
                i.selected = false
            }
            model.selected = true
            notifyDataSetChanged()
            listener.setOnPackageSelect(model)

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: PackageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var _binding: PackageItemBinding? = null

        init {
            this._binding = binding


        }

    }


}