package gemastik.pendekar.ui.main.home.list_cctv

import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentListCctvBinding
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.getViewModel


class ListCCTVFragment : DevFragment<FragmentListCctvBinding>(R.layout.fragment_list_cctv){
    override val vm: ListCCTVViewModel by lazy { getViewModel() }
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private lateinit var adapter:ListCCTVAdapter
    override fun initData() {
        adapter = ListCCTVAdapter {
            menuController?.navigate(ListCCTVFragmentDirections.actionListCCTVFragmentToCCTVCameraFragment(it.id))
        }
    }

    override fun initUI() {
        binding.rvListCCTV.adapter = adapter
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
        binding.btnMaps.setOnClickListener {
            menuController?.navigate(ListCCTVFragmentDirections.actionListCCTVFragmentToCCTVFragment())
        }
        vm.getListCCTV()
    }

    override fun initObserver() {
        vm.listCCTV.observe(viewLifecycleOwner){
            when(it){
                is DevState.Success ->{
                    adapter.updateList(it.data)
                }
                else ->{

                }
            }
        }
    }
}