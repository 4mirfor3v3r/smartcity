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
import gemastik.pendekar.data.model.SearchHistoryCCTVModel
import gemastik.pendekar.databinding.FragmentCctvBinding
import gemastik.pendekar.utils.CustomMarkerCCTVView
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.getViewModel

class CCTVFragment : DevFragment<FragmentCctvBinding>(R.layout.fragment_cctv), OnMapReadyCallback, OnMarkerClickListener {
    override val vm: CCTVViewModel by lazy { getViewModel() }
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private lateinit var adapter: CCTVSearchAdapter
    private val listMarker: List<SearchHistoryCCTVModel> = listOf(
        SearchHistoryCCTVModel(0,"CCTV Cipaganti","Jl. Raden AA. Wiranata Kusumah No.29-31, Pasir Kaliki, Kec. Cicendo, Kota Bandung, Jawa Barat 40171",-6.900243, 107.602264),
        SearchHistoryCCTVModel(1,"CCTV Pasar Sukajadi","Jl. Sukajadi No.26, Sukabungah, Kec. Sukajadi, Kota Bandung, Jawa Barat 40162",-6.894551, 107.597121),
    )

    override fun initData() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        adapter = CCTVSearchAdapter {
//            menuController?.navigate(
//                CCTVFragmentDirections.actionCCTVFragmentToCCTVCameraFragment(
//                    cctvId = it.id.toString(),
//                    title = it.searchName,
//                    address = it.address
//                )
//            )
        }
    }

    override fun initUI() {
//        binding.rvSearchResult.adapter = adapter
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
        vm.getListCCTV()
    }

    override fun initObserver() {
//        adapter.updateList(listMarker)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-6.9, 107.6), 14f))
        googleMap.setOnMarkerClickListener(this)

        vm.listCCTV.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
                }
                is DevState.Empty -> {
                }
                is DevState.Success -> {
                    it.data.forEach {cctv ->
                        val markerIcon = CustomMarkerCCTVView.getMarkerIcon(
                            binding.map as ViewGroup,
                            ""
                        )
                        val marker = googleMap.addMarker(
                            MarkerOptions().position(LatLng(cctv.lat.toDouble(), cctv.lng.toDouble())).icon(markerIcon)
                        )
                        marker?.tag = cctv.id
                    }
                }
                is DevState.Failure -> {}
                is DevState.Default -> {}
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        menuController?.navigate(CCTVFragmentDirections.actionCCTVFragmentToCCTVCameraFragment(marker.tag.toString()?:"-1"))
        return true
    }

}