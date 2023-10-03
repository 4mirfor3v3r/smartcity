package gemastik.pendekar.ui.main.home.self_report

import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentSelfReportBinding
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.getViewModel

class SelfReportFragment : DevFragment<FragmentSelfReportBinding>(R.layout.fragment_self_report) {
    override val vm: ReportViewModel by lazy { getViewModel() }

    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    private lateinit var adapter:HistoryReportAdapter

    override fun initData() {
        adapter = HistoryReportAdapter()
        vm.getListReport()
    }

    override fun initUI() {
        binding.rvHistory.adapter = adapter
    }

    override fun initAction() {
        binding.btnCreateReport.setOnClickListener{
            menuController?.navigate(SelfReportFragmentDirections.actionSelfReportFragmentToCreateReportFragment())
        }
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
    }

    override fun initObserver() {
        vm.listReport.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
//                    binding.msvHistoryTest.showLoadingLayout()
                }
                is DevState.Empty -> {
//                    binding.msvHistoryTest.showEmptyLayout()
                }
                is DevState.Success -> {
//                    binding.msvHistoryTest.showDefaultLayout()
                        adapter.updateList(it.data)
                }
                is DevState.Failure -> {}
                is DevState.Default -> {}
            }
        }
        vm.createAllReportResult.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
                }
                is DevState.Empty -> {
                }
                is DevState.Success -> {
                    adapter.updateList(it.data.reversed())
                }
                is DevState.Failure -> {}
                is DevState.Default -> {}
            }
        }
    }
}