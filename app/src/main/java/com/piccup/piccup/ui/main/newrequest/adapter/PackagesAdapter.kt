package com.piccup.piccup.ui.main.newrequest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piccup.piccup.R
import com.piccup.piccup.databinding.PackageItemBinding
import com.piccup.piccup.ui.main.newrequest.PackageModel
import com.piccup.piccup.util.extensions.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


class PackagesAdapter(
    private val context: Context,
    private var myData: ArrayList<PackageModel>,
    private val listener: OnPackageSelect
) : RecyclerView.Adapter<PackagesAdapter.ViewHolder>() {


    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }

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
        holder._binding?.desc?.text = model.description
        holder._binding?.perMonth?.text = model.per_month
        holder._binding?.perSemester?.text = model.per_semester
        holder._binding?.perSemesterMonth?.text =
            roundOffDecimal(model.per_semester?.toDouble()!! / 3).toString()

        Glide
            .with(context)
            .load(model.photo)
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .into(holder._binding?.image!!);

        if (model.selected) {
            holder._binding?.itemLayout?.setBackgroundResource(R.drawable.package_selected)

            holder._binding?.name?.greenText()
            holder._binding?.name?.greenText()
            holder._binding?.perMonth?.greenText()
            holder._binding?.perMonthText?.greenText()
            holder._binding?.perSemester?.greenText()
            holder._binding?.perSemesterText?.greenText()
            holder._binding?.perSemesterMonth?.greenText()
            holder._binding?.perSemesterTextMonth?.greenText()
            holder._binding?.selected?.visible()

        } else {
            holder._binding?.itemLayout?.setBackgroundResource(R.drawable.package_not_selected)


            holder._binding?.name?.greyText()
            holder._binding?.name?.blackText()
            holder._binding?.name?.blackText()
            holder._binding?.perMonth?.blackText()
            holder._binding?.perMonthText?.blackText()
            holder._binding?.perSemester?.blackText()
            holder._binding?.perSemesterText?.blackText()
            holder._binding?.perSemesterMonth?.greyText()
            holder._binding?.perSemesterTextMonth?.greyText()
            holder._binding?.selected?.hide()

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