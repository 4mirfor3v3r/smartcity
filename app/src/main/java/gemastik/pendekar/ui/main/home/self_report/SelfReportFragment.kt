package gemastik.pendekar.ui.main.home.self_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.databinding.FragmentSelfReportBinding
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.ReportStatus
import gemastik.pendekar.utils.getViewModel

class SelfReportFragment : DevFragment<FragmentSelfReportBinding>(R.layout.fragment_self_report) {
    override val vm: ReportViewModel by lazy { getViewModel() }

    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    private lateinit var adapter:HistoryReportAdapter

    override fun initData() {
        adapter = HistoryReportAdapter()
        vm.getAllReport()
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
        vm.historyReportResult.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
//                    binding.msvHistoryTest.showLoadingLayout()
                }
                is DevState.Empty -> {
                    val listReport = listOf(
                        HistoryReportModel(null,"Tawuran","Jl. Asia Afrika, Kebon Pisang, Kota Bandung","17 Agustus 2022",ReportStatus.SUCCESS),
                        HistoryReportModel(null,"Balapan Liar    ","Jl. Buah Batu, Kec. Lengkong, Kota Bandung","30 September 2022",ReportStatus.SUCCESS),
                        HistoryReportModel(null,"Motor Berkerumun","Jl. Soekarno Hatta","10 Oktober 2022",ReportStatus.SUCCESS),
                        HistoryReportModel(null,"Motor Berkerumun","Jl. Soekarno Hatta","12 Oktober 2022",ReportStatus.FAILURE),
                        HistoryReportModel(null,"Motor Berkerumun","Jl. Soekarno Hatta","13 Oktober 2022",ReportStatus.PROCESS),

                    )
                    vm.addAllReport(listReport)
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