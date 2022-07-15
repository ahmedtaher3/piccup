package com.piccup.piccup.ui.main.newrequest

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.api.ApolloResponse
import com.example.myapplication.GetCitiesQuery
import com.example.myapplication.GetSchoolsQuery
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseFragment
import com.piccup.piccup.databinding.FragmentStep2Binding
import com.piccup.piccup.ui.main.newrequest.adapter.*
import com.piccup.piccup.util.Status
import com.piccup.piccup.util.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Step2Fragment : BaseFragment<FragmentStep2Binding>(), EnterTimeAdapter.OnEnterTimeSelect,
    ExitTimeAdapter.OnExitTimeSelect, SectionAdapter.OnSectionSelect, GradesAdapter.OnGradeSelect,
    SchoolsAdapter.OnSchoolSelect, CityAdapter.OnCitySelect {

    lateinit var binding: FragmentStep2Binding
    val viewModel: NewRequestViewModel by activityViewModels()


    lateinit var enterTimeDialog: AlertDialog
    lateinit var exitTimeDialog: AlertDialog
    lateinit var sectionDialog: AlertDialog
    lateinit var gradeDialog: AlertDialog
    lateinit var schoolsDialog: AlertDialog
    lateinit var cityDialog: AlertDialog


    var schools = ArrayList<GetSchoolsQuery.School?>()
    var cities = ArrayList<GetCitiesQuery.City?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.step.postValue(2)
        viewModel.getCities()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding!!
        binding.city.setOnClickListener {
            showCityDialog()
        }
        binding.entryTimeLayout.setOnClickListener {
            showEnterTimeDialog()
        }
        binding.exitTimeLayout.setOnClickListener {
            showExitTimeDialog()
        }

        binding.selectSection.setOnClickListener {
            showSectionDialog()
        }

        binding.selectGrade.setOnClickListener {
            showGradeDialog()
        }

        binding.selectSchool.setOnClickListener {
            showSchoolsDialog()
        }

        binding.next.setOnClickListener {
            replaceFragment(Step3Fragment(), "Step2Fragment")

        }
        observe(viewModel.citiesState)
        {

            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as ApolloResponse<GetCitiesQuery.Data>
                    if (response.hasErrors()) {
                        baseActivity.showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        cities = response.data?.cities as ArrayList<GetCitiesQuery.City?>
                        if (cities.size > 0) {
                            viewModel.getSchools(cities.first()?.id!!)
                            binding.cityName.text = cities.first()?.name
                            viewModel.city.postValue(cities.first())
                        }


                    }

                }
            }
        }
        observe(viewModel.schoolsState)
        {

            when (it) {
                is Status.Loading -> {
                    baseActivity.showDialogLoading()
                }
                is Status.Success<*> -> {
                    baseActivity.hideDialogLoading()
                    val response = it.data as ApolloResponse<GetSchoolsQuery.Data>
                    if (response.hasErrors()) {
                        baseActivity.showWarningSnackbar(response.errors?.get(0)?.message!!)
                    } else {

                        schools = response.data?.schools as ArrayList<GetSchoolsQuery.School?>

                    }

                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_step2
    }


    fun showEnterTimeDialog() {

        val list = ArrayList<String>()
        list.add("07:00:00")
        list.add("07:30:00")
        list.add("08:00:00")
        list.add("08:30:00")
        list.add("09:00:00")

        val adpter = EnterTimeAdapter(baseActivity, ArrayList(), this)
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.select_layout, null)
        dialogBuilder.setView(dialogView)
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        enterTimeDialog = dialogBuilder.create()

        recycler.adapter = adpter
        adpter.setMyData(list)

        enterTimeDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        enterTimeDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        enterTimeDialog.show()
    }

    fun showExitTimeDialog() {

        val list = ArrayList<String>()
        list.add("12:00:00")
        list.add("12:30:00")
        list.add("13:00:00")
        list.add("13:30:00")
        list.add("14:00:00")
        list.add("14:30:00")
        list.add("14:45:00")
        list.add("15:00:00")
        list.add("15:30:00")

        val adpter = ExitTimeAdapter(baseActivity, ArrayList(), this)
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.select_layout, null)
        dialogBuilder.setView(dialogView)
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        exitTimeDialog = dialogBuilder.create()

        recycler.adapter = adpter
        adpter.setMyData(list)

        exitTimeDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        exitTimeDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        exitTimeDialog.show()
    }

    fun showSectionDialog() {

        val list = ArrayList<String>()
        list.add("American")
        list.add("Ig")
        list.add("National")
        list.add("Buck")


        val adpter = SectionAdapter(baseActivity, ArrayList(), this)
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.select_layout, null)
        dialogBuilder.setView(dialogView)
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        sectionDialog = dialogBuilder.create()
        recycler.adapter = adpter
        adpter.setMyData(list)

        sectionDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        sectionDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        sectionDialog.show()
    }

    fun showGradeDialog() {

        val list = ArrayList<String>()
        list.add("KG")
        list.add("G1 to G6")
        list.add("G7 to G9")
        list.add("G10 to G12")


        val adpter = GradesAdapter(baseActivity, ArrayList(), this)
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.select_layout, null)
        dialogBuilder.setView(dialogView)
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        gradeDialog = dialogBuilder.create()
        recycler.adapter = adpter
        adpter.setMyData(list)

        gradeDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        gradeDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        gradeDialog.show()
    }

    fun showSchoolsDialog() {
        val adpter = SchoolsAdapter(baseActivity, ArrayList(), this)
        val dialogBuilder = AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.select_layout, null)
        dialogBuilder.setView(dialogView)
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        schoolsDialog = dialogBuilder.create()
        recycler.adapter = adpter
        adpter.setMyData(schools)

        schoolsDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        schoolsDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        schoolsDialog.show()
    }

    fun showCityDialog() {
        val adpter = CityAdapter(baseActivity, ArrayList(), this)
        val dialogBuilder = AlertDialog.Builder(baseActivity)
        val dialogView: View = layoutInflater.inflate(R.layout.select_layout, null)
        dialogBuilder.setView(dialogView)
        val recycler = dialogView.findViewById<RecyclerView>(R.id.recycler)
        cityDialog = dialogBuilder.create()
        recycler.adapter = adpter
        adpter.setMyData(cities)

        cityDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        cityDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        cityDialog.show()
    }

    override fun setOnEnterTimeSelect(model: String) {
        viewModel.enterTime.postValue(model)
        binding.entryTime.text = model
        enterTimeDialog.dismiss()
    }

    override fun setOnExitTimeSelect(model: String) {
        viewModel.exitTime.postValue(model)
        binding.exitTime.text = model
        exitTimeDialog.dismiss()
    }

    override fun setOnSectionSelect(model: String) {
        viewModel.section.postValue(model)
        binding.selectSection.setText(model)
        sectionDialog.dismiss()
    }

    override fun setOnGradeSelect(model: String) {
        viewModel.grade.postValue(model)
        binding.selectGrade.setText(model)
        gradeDialog.dismiss()

    }

    override fun setOnSchoolSelect(model: GetSchoolsQuery.School?) {
        viewModel.school.postValue(model)
        binding.selectSchool.setText(model?.name)
        schoolsDialog.dismiss()
    }

    override fun setOnCitySelect(model: GetCitiesQuery.City?) {
        viewModel.city.postValue(model)
        binding.cityName.text = model?.name
        cityDialog.dismiss()
        viewModel.getSchools(model?.id!!)
    }

}