package gemastik.pendekar.ui.main.home.self_report

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.databinding.FragmentCreateReportBinding
import gemastik.pendekar.utils.ApplicationContext
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.ReportStatus
import gemastik.pendekar.utils.getViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CreateReportFragment :
    DevFragment<FragmentCreateReportBinding>(R.layout.fragment_create_report) {

    override val vm: ReportViewModel by lazy { getViewModel() }
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private var report = HistoryReportModel(null, "", "", "", "", ReportStatus.PROCESS)
    private var pickedImage: File? = null
    private lateinit var openCamera:ActivityResultLauncher<Uri>
    private lateinit var uri: Uri

    override fun initData() {
        if(checkPermissions()) {
            openCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { res ->
                if (res) {
                    binding.btnPhoto.visibility = View.INVISIBLE
                    Glide.with(context ?: ApplicationContext.get())
                        .load(uri)
                        .into(binding.ivPhoto)
                    binding.btnSubmit.isEnabled = isInputValid()
                }else {
                    pickedImage = null
                }
            }
        }else{
            requestPermissions()
        }
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
                        binding.etDate.editText?.setText(formatter.format(cal.time))
                        btnSubmit.isEnabled = isInputValid()
                        val formatter2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault())
                        report.reportDate = formatter2.format(cal.time)
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            btnSubmit.setOnClickListener {
                if (report.reportTitle.isNotEmpty() && pickedImage != null) {
                    btnSubmit.startAnimation()
                    vm.addReport(report, pickedImage!!)
                }
            }
            btnPhoto.setOnClickListener {
                dispatchTakePictureIntent()
            }
            ivPhoto.setOnClickListener {
                dispatchTakePictureIntent()
            }
            etName.editText?.doAfterTextChanged {
                report.senderName = it.toString()
                btnSubmit.isEnabled = isInputValid()
            }
            etTitle.editText?.doAfterTextChanged {
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
                binding.btnSubmit.revertAnimation()
                Toast.makeText(context, "Penambahan Laporan Berhasil", Toast.LENGTH_SHORT).show()
                menuController?.popBackStack()
            } else if (it is DevState.Failure) {
                Toast.makeText(context, "Penambahan Laporan Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isInputValid(): Boolean {
        return !binding.etTitle.editText?.text.isNullOrEmpty() &&
                !binding.etLocation.editText?.text.isNullOrEmpty() &&
                !binding.etDate.editText?.text.isNullOrEmpty() &&
                pickedImage != null
    }

    private fun dispatchTakePictureIntent() {
        val file = File.createTempFile(
            "IMG_",
            ".jpg",
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        pickedImage = file
        uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
        openCamera.launch(uri)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            permissionId
        )
        initData()
    }
    companion object {
        private const val permissionId = 3
    }
}