package gemastik.pendekar.ui.main.home.danger_area

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.data.model.SearchHistoryDangerModel
import gemastik.pendekar.databinding.FragmentVurnPointBinding
import gemastik.pendekar.utils.CustomMarkerDangerZoneView
import gemastik.pendekar.utils.PermissionUtils

class VurnPointFragment : DevFragment<FragmentVurnPointBinding>(R.layout.fragment_vurn_point),
    OnMapReadyCallback {
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private lateinit var adapter: DangerSearchAdapter
    private lateinit var maps: GoogleMap
    private val listMarker: List<SearchHistoryDangerModel> = listOf(
        SearchHistoryDangerModel(0,"CCTV Jembatan Layang pasupati","Tamansari, Kec. Bandung Wetan, Kota Bandung, Jawa Barat 40116",-6.900243, 107.602264),
        SearchHistoryDangerModel(1,"CCTV Pasar Sukajadi","Jl. Sukajadi No.26, Sukabungah, Kec. Sukajadi, Kota Bandung, Jawa Barat 40162",-6.894551, 107.597121),
    )

    override fun initData() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        adapter = DangerSearchAdapter {
            if(this::maps.isInitialized){
                maps.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.lat,it.lng),17f))
            }
        }
    }

    override fun initUI() {
        binding.rvSearchResult.adapter = adapter
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
    }

    override fun initObserver() {
        adapter.updateList(listMarker)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-6.9, 107.6), 14f))
        maps = googleMap
        listMarker.forEach {
            val markerIcon = CustomMarkerDangerZoneView.getMarkerIcon(binding.map as ViewGroup, it.searchName)
            googleMap.addMarker(MarkerOptions().position(LatLng(it.lat, it.lng)).icon(markerIcon))
        }
        enableMyLocation()
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
            maps.isMyLocationEnabled = true
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}