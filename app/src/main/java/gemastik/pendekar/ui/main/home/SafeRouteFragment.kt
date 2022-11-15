package gemastik.pendekar.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.android.gms.maps.model.MarkerOptions
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentSafeRouteBinding
import gemastik.pendekar.utils.CustomMarkerDangerZoneView
import gemastik.pendekar.utils.PermissionUtils

class SafeRouteFragment : DevFragment<FragmentSafeRouteBinding>(R.layout.fragment_safe_route),
    OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private val listMarker: List<LatLng> = listOf(LatLng(-6.900243, 107.602267), LatLng(-6.894551, 107.597121))

    private var myLocation = LatLng(-6.9, 107.6)
    private var permissionDenied = false
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
    }

    override fun initObserver() {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-6.9, 107.6), 14f))
        val markerIcon1 = CustomMarkerDangerZoneView.getMarkerIcon(
            binding.map as ViewGroup,
            "CCTV Jembatan Layang pasupati"
        )
        val markerIcon2 = CustomMarkerDangerZoneView.getMarkerIcon(
            binding.map as ViewGroup,
            "CCTV Pasar Sukajadi"
        )
        googleMap.addMarker(MarkerOptions().position(listMarker[0]).icon(markerIcon1))
        googleMap.addMarker(MarkerOptions().position(listMarker[1]).icon(markerIcon2))
        enableMyLocation()
    }

    override fun onMyLocationButtonClick(): Boolean {
        getLocation()
        return false
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
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
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
                        myLocation = LatLng(location.latitude,location.longitude)
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