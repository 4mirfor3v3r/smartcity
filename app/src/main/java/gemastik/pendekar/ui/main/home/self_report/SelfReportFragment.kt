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
import gemastik.pendekar.utils.ReportStatus

class SelfReportFragment : DevFragment<FragmentSelfReportBinding>(R.layout.fragment_self_report) {
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    private lateinit var adapter:HistoryReportAdapter
    override fun initData() {
        adapter = HistoryReportAdapter()
    }

    override fun initUI() {
        binding.rvHistory.adapter = adapter
    }

    override fun initAction() {
        binding.btnCreateReport.setOnClickListener{
            menuController?.navigate(SelfReportFragmentDirections.actionSelfReportFragmentToCreateReportFragment())
        }
    }

    override fun initObserver() {
        adapter.add(HistoryReportModel("5","Motor Berkerumun","Jl. Soekarno Hatta","13 Oktober 2022",ReportStatus.PROCESS))
        adapter.add(HistoryReportModel("4","Motor Berkerumun","Jl. Soekarno Hatta","12 Oktober 2022",ReportStatus.FAILURE))
        adapter.add(HistoryReportModel("3","Motor Berkerumun","Jl. Soekarno Hatta","10 Oktober 2022",ReportStatus.SUCCESS))
        adapter.add(HistoryReportModel("2","Balapan Liar    ","Jl. Buah Batu, Kec. Lengkong, Kota Bandung","30 September 2022",ReportStatus.SUCCESS))
        adapter.add(HistoryReportModel("1","Tawuran","Jl. Asia Afrika, Kebon Pisang, Kota Bandung","17 Agustus 2022",ReportStatus.SUCCESS))
    }
}