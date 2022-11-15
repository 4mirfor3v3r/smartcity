package gemastik.pendekar.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentVurnPointBinding
import gemastik.pendekar.ui.main.home.cctv.CCTVFragmentDirections
import gemastik.pendekar.utils.CustomMarkerCCTVView
import gemastik.pendekar.utils.CustomMarkerDangerZoneView

class VurnPointFragment : DevFragment<FragmentVurnPointBinding>(R.layout.fragment_vurn_point),
    OnMapReadyCallback {
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
    }

}