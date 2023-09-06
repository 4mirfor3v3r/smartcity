package gemastik.pendekar.ui.main.home

import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentHomeBinding
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.getViewModel

class HomeFragment : DevFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val vm: HomeViewModel by lazy { getViewModel() }
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        with(binding){
            btnSelfReport.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToSelfReportFragment())
            }
            btnCCTV.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToListCCTVFragment())
            }
            btnSafeRoute.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToSafeRouteFragment())
            }
        }
        vm.getTotalCrime()
    }

    override fun initObserver() {
        vm.totalCrime.observe(viewLifecycleOwner){
            when(it){
                is DevState.Success ->{
                    binding.tvCrimeCount.text = it.data.toString()
                }
                else ->{

                }
            }
        }
    }
}