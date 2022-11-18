package gemastik.pendekar.ui.main.home.safe_area

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.data.model.SearchHistoryDangerModel
import gemastik.pendekar.databinding.FragmentSafeRouteBinding
import gemastik.pendekar.ui.main.home.self_report.ReportViewModel
import gemastik.pendekar.utils.*

class SafeRouteFragment : DevFragment<FragmentSafeRouteBinding>(R.layout.fragment_safe_route),
    OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    override val vm: SafeRouteViewModel by lazy { getViewModel() }
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    private val listMarker: List<SearchHistoryDangerModel> = listOf(
        SearchHistoryDangerModel(
            0,
            "CCTV Jembatan Layang pasupati",
            "Tamansari, Kec. Bandung Wetan, Kota Bandung, Jawa Barat 40116",
            -6.900243,
            107.602264
        ),
        SearchHistoryDangerModel(
            1,
            "CCTV Pasar Sukajadi",
            "Jl. Sukajadi No.26, Sukabungah, Kec. Sukajadi, Kota Bandung, Jawa Barat 40162",
            -6.894551,
            107.597121
        ),
    )

    private var myLocation = LatLng(-6.9, 107.6)
    private var permissionDenied = false

    private var defaultPolyline: Polyline? = null
    private var defaultMarker : Marker? = null
    private lateinit var map: GoogleMap
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun initData() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
        binding.etSearch.setOnClickListener{
            launchSearch()
        }
    }

    override fun initObserver() {
        vm.safeRoute.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
                }
                is DevState.Empty -> {
                }
                is DevState.Success -> {
                    if(this::map.isInitialized){
                        defaultPolyline?.remove()
                        defaultMarker?.remove()
                        defaultMarker = map.addMarker(MarkerOptions().position(
                            LatLng(
                                it.data.route[0].sections[0].arrival.place.originalLocation.lat,
                                it.data.route[0].sections[0].arrival.place.originalLocation.lng
                            )))
                        val polyline = PolylineEncoderDecoder.decode(it.data.route[0].sections[0].polyline)
                        val latLngPolyline = polyline.map { it2 -> LatLng(it2.lat, it2.lng) }
                        defaultPolyline = map.addPolyline(PolylineOptions().addAll(latLngPolyline).color(resources.getColor(R.color.blue,null)))
                        map.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
                    }
                }
                is DevState.Failure -> {}
                is DevState.Default -> {}
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-6.9, 107.6), 14f))
        listMarker.forEach {
            val markerIcon =
                CustomMarkerDangerZoneView.getMarkerIcon(binding.map as ViewGroup, it.searchName)
            googleMap.addMarker(MarkerOptions().position(LatLng(it.lat, it.lng)).icon(markerIcon))
        }
        enableMyLocation()
    }

    override fun onMyLocationButtonClick(): Boolean {
        getLocation()
        return false
    }

    private fun launchSearch() {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireContext())
        getLocation()
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    place.latLng?.let { it1 -> vm.getRoute(myLocation, it1, listMarker.map { it2->LatLng(it2.lat,it2.lng) }) }
                }
            }
            AutocompleteActivity.RESULT_ERROR -> {
            }
            Activity.RESULT_CANCELED -> {
                // The user canceled the operation.
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {

        // [START maps_check_location_permission]
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            return
        }

        // 2. If if a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            PermissionUtils.RationaleDialog.newInstance(
                LOCATION_PERMISSION_REQUEST_CODE, true
            ).show(childFragmentManager, "dialog")
            return
        }

        // 3. Otherwise, request permission
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
        // [END maps_check_location_permission]
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        myLocation = LatLng(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(context, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val permissionId = 2
    }
}