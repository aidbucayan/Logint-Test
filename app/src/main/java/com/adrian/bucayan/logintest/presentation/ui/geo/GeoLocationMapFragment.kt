package com.adrian.bucayan.logintest.presentation.ui.geo

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.adrian.bucayan.logintest.BuildConfig
import com.adrian.bucayan.logintest.R
import com.adrian.bucayan.logintest.common.Constants
import com.adrian.bucayan.logintest.databinding.FragmentGeoLocationMapBinding
import com.adrian.bucayan.logintest.domain.model.User
import com.adrian.bucayan.logintest.presentation.ui.MainActivity
import com.birjuvachhani.locus.Locus
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import java.text.DecimalFormat
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class GeoLocationMapFragment : Fragment(R.layout.fragment_geo_location_map) , OnMapReadyCallback {

    private var _binding: FragmentGeoLocationMapBinding? = null
    private val binding get() = _binding!!
    private var mapboxMap: MapboxMap? = null
    private lateinit var user: User
    private val EARTH_RADIUS = 371000.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentGeoLocationMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(activity!!, BuildConfig.MapboxAccessToken)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.displayToolbar(true)
        user = arguments?.getParcelable(Constants.SELECTED_USER)!!
        user.name?.let { (activity as MainActivity?)?.toolBarTitle("$it Location") }

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onMapReady(mMapboxMap: MapboxMap) {
        mapboxMap = mMapboxMap
        mMapboxMap.uiSettings.isCompassEnabled = false
        mMapboxMap.setStyle(
            Style.MAPBOX_STREETS
        )
        val latLng = LatLng(
            user.address?.geo?.lat!!.toDouble(),
            user.address?.geo?.lng!!.toDouble()
        )

        Locus.getCurrentLocation(requireActivity()) { result ->
            result.location?.let {
                computeForDistance(it)
                result.error?.let {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        resources.getText(R.string.location_error), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun computeForDistance(currentLocation: Location) {
        val points: MutableList<LatLng> = ArrayList()

        val latLngCurrentLocation =
            LatLng(currentLocation.latitude, currentLocation.longitude)
        points.add(latLngCurrentLocation)

        val latLngDestination = LatLng(user.address?.geo?.lat!!.toDouble(), user.address?.geo?.lng!!.toDouble())
        points.add(latLngDestination)

        val latLngBounds = LatLngBounds.Builder()
            .includes(points)
            .build()

        mapboxMap!!.easeCamera(
            CameraUpdateFactory.newLatLngBounds(
                latLngBounds,
                50, 50, 50, 50
            ), 1
        )

       val userLoc = Location("")
        userLoc.latitude = user.address?.geo?.lat!!.toDouble()
        userLoc.longitude = user.address?.geo?.lng!!.toDouble()

        val distanceInMeters: Float = currentLocation.distanceTo(userLoc)
        Timber.e("distance in %s", distanceInMeters)

        val distanceInKM: Float = distanceInMeters / 1000
        val numberFormat = DecimalFormat("#.00")

        binding.tvGeoLocDistance.text = "Your distance form " + user.name + " is " +
                numberFormat.format(distanceInKM) + " KM"
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


