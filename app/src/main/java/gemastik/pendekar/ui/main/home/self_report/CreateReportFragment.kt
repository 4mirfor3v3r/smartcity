package gemastik.pendekar.ui.main.home.self_report

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.databinding.FragmentCreateReportBinding
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.ReportStatus
import gemastik.pendekar.utils.getViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CreateReportFragment :
    DevFragment<FragmentCreateReportBinding>(R.layout.fragment_create_report) {

    override val vm: ReportViewModel by lazy { getViewModel() }
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private var report = HistoryReportModel(null, "", "", "", ReportStatus.PROCESS)

    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        with(binding) {
            btnBack.setOnClickListener {
                menuController?.popBackStack()
            }
            val cal = Calendar.getInstance()
            etDate.editText?.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth ->

                        val formatter = SimpleDateFormat("dd MMMM yyyy",Locale.getDefault())
                        cal.set(year,month,dayOfMonth)
                        report.reportDate = formatter.format(cal.time)
                        binding.etDate.editText?.setText(formatter.format(cal.time))
                        btnSubmit.isEnabled = isInputValid()
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            btnSubmit.setOnClickListener {
                if (report.reportTitle.isNotEmpty()) {
                    vm.addReport(report)
                }
            }
            etName.editText?.doAfterTextChanged {
                report.reportTitle = it.toString()
                btnSubmit.isEnabled = isInputValid()
            }
            etLocation.editText?.doAfterTextChanged {
                report.reportAddress = it.toString()
                btnSubmit.isEnabled = isInputValid()
            }
        }
    }

    override fun initObserver() {
        vm.createReportResult.observe(viewLifecycleOwner) {
            if (it is DevState.Success) {
                vm.getAllReport()
                Toast.makeText(context, "Penambahan Laporan Berhasil", Toast.LENGTH_SHORT).show()
                menuController?.popBackStack()
            } else if (it is DevState.Failure) {
                Toast.makeText(context, "Penambahan Laporan Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isInputValid(): Boolean {
        return !binding.etName.editText?.text.isNullOrEmpty() &&
                !binding.etLocation.editText?.text.isNullOrEmpty() &&
                !binding.etDate.editText?.text.isNullOrEmpty()
    }
}