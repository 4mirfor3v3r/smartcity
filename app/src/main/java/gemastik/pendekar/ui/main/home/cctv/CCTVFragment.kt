package gemastik.pendekar.ui.main.home.cctv

import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentCctvBinding
import gemastik.pendekar.utils.CustomMarkerCCTVView

class CCTVFragment : DevFragment<FragmentCctvBinding>(R.layout.fragment_cctv), OnMapReadyCallback,
    OnMarkerClickListener {
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    private val listMarker: List<LatLng> =
        listOf(LatLng(-6.900243, 107.602267), LatLng(-6.894551, 107.597121))

    override fun initData() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-6.9, 107.6), 14f))
        googleMap.setOnMarkerClickListener(this)
        val markerIcon1 = CustomMarkerCCTVView.getMarkerIcon(
            binding.map as ViewGroup,
            "CCTV Jembatan Layang pasupati"
        )
        val markerIcon2 = CustomMarkerCCTVView.getMarkerIcon(
            binding.map as ViewGroup,
            "CCTV Pasar Sukajadi"
        )
        googleMap.addMarker(MarkerOptions().position(listMarker[0]).icon(markerIcon1))
        googleMap.addMarker(MarkerOptions().position(listMarker[1]).icon(markerIcon2))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (marker.position == listMarker[0]) {
            menuController?.navigate(
                CCTVFragmentDirections.actionCCTVFragmentToCCTVCameraFragment(
                    cctvId = 0,
                    title = marker.title ?: "",
                    address = "Tamansari, Kec. Bandung Wetan, Kota Bandung, Jawa Barat 40116"
                )
            )
        } else if (marker.position == listMarker[1]) {
            menuController?.navigate(
                CCTVFragmentDirections.actionCCTVFragmentToCCTVCameraFragment(
                    cctvId = 1,
                    title = marker.title ?: "",
                    address = "Jl. Sukajadi No.26, Sukabungah, Kec. Sukajadi, Kota Bandung, Jawa Barat 40162"
                )
            )
        }
        return true
    }

}