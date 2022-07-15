package com.piccup.piccup.ui.main.newrequest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.piccup.piccup.R
import com.piccup.piccup.base.BaseActivity
import com.piccup.piccup.databinding.ActivitySelectLocationBinding
import mumayank.com.airlocationlibrary.AirLocation
import java.io.IOException
import java.util.*

class SelectSingleLocation : BaseActivity<ActivitySelectLocationBinding>(), OnMapReadyCallback,
    View.OnClickListener {
    lateinit var binding: ActivitySelectLocationBinding
    var address: String = ""
    var placeName: String = ""
    var viewGroup: ViewGroup? = null
    lateinit var mMap: GoogleMap
    var mapFragment: SupportMapFragment? = null
    var autocompleteSupportFragment: AutocompleteSupportFragment? = null
    private lateinit var airLocation: AirLocation
    private lateinit var myLocation: Location
    private lateinit var lastLtLng: LatLng


    override fun getLayoutId(): Int {
        return R.layout.activity_select_location
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar? = supportActionBar

        if (actionBar != null) {
            getSupportActionBar()!!.hide();
            // actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding = viewDataBinding!!
        init()


    }

    fun init() {
        val myFragmentManager = supportFragmentManager
        mapFragment = myFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        viewGroup = findViewById(android.R.id.content)
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }


        binding.myLocation.setOnClickListener(this)
        binding.isBack.setOnClickListener(this)
        binding.selectLocation.setOnClickListener(this)



        airLocation = AirLocation(
            this@SelectSingleLocation,
            true,
            true,
            object : AirLocation.Callbacks {
                override fun onSuccess(location: Location) { // do something
                    myLocation = location

                }

                override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

                }
            })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        airLocation.onActivityResult(requestCode, resultCode, data) // ADD THIS LINE INSIDE onActivityResult
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults) // ADD THIS LINE INSIDE onRequestPermissionResult
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        googleMap.getUiSettings().setMyLocationButtonEnabled(false)
        googleMap.getUiSettings().setMapToolbarEnabled(false)
        googleMap.getUiSettings().setZoomControlsEnabled(false)
        googleMap.getUiSettings().setCompassEnabled(false)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted

                googleMap.isMyLocationEnabled = true


            } else {

            }


            googleMap.setOnCameraIdleListener {

                lastLtLng = googleMap.getCameraPosition().target
                var geocoder: Geocoder

                var addresses: List<Address> = ArrayList()
                geocoder = Geocoder(this@SelectSingleLocation, Locale("en"))
                try {
                    addresses = geocoder.getFromLocation(
                        googleMap.getCameraPosition().target.latitude,
                        googleMap.getCameraPosition().target.longitude,
                        1
                    ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (addresses.size != 0) {
                    address =
                        addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                    try {
                        placeName = addresses[0].locality
                        Log.e("address", address)
                    } catch (e: java.lang.Exception) {

                    }

                    autocompleteSupportFragment!!.setText(address)

                } else {
                    address = ""

                }


            }


            autocompleteSupportFragment =
                supportFragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as AutocompleteSupportFragment?
            autocompleteSupportFragment?.setPlaceFields(
                Arrays.asList(
                    Place.Field.ID,
                    Place.Field.LAT_LNG,
                    Place.Field.NAME
                )
            )
            autocompleteSupportFragment!!.setOnPlaceSelectedListener(object :
                PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {

                    lastLtLng = place.latLng!!
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 19f))
                }

                override fun onError(status: Status) {}
            })

        }
    }

    override fun onBackPressed() {
        // When the user hits the back button set the resultCode
        // as Activity.RESULT_CANCELED to indicate a failure
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.select_location ->
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(
                        this@SelectSingleLocation,
                       "Select Address",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {


                    Log.e("here", "yes");
                    val intent = Intent()

                    intent.putExtra("ADDRESS", address)
                    intent.putExtra("placeName", placeName)
                    intent.putExtra("LAT", lastLtLng.latitude)
                    intent.putExtra("LONG", lastLtLng.longitude)
                    setResult(AppCompatActivity.RESULT_OK, intent)
                    super.finish()


                }
            R.id.isBack -> finish()
            R.id.myLocation -> {
                try {

                    val latLng = LatLng(myLocation.latitude, myLocation.longitude)
                    lastLtLng = latLng
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19f))

                } catch (e: Exception) {

                }

            }
        }
    }

}