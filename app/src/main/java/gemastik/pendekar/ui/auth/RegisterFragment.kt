package gemastik.pendekar.ui.auth

import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentRegisterBinding

class RegisterFragment : DevFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val authController by lazy { activity?.findNavController(R.id.nav_host_fragment_auth) }
    private val mainController by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }
    private lateinit var adapter : ArrayAdapter<String>
    private var isMale=-1
    private var isRegisterPolice = -1
    private var isAgreementChecked = false

    override fun initData() {
        val accountType = resources.getStringArray(R.array.accountType)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,accountType)
    }

    override fun initUI() {
        binding.dropdownType.adapter = adapter
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            authController?.popBackStack()
        }

        binding.etName.editText?.doAfterTextChanged {
            verifyFullName()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etUsername.editText?.doAfterTextChanged {
            verifyUsername()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etEmail.editText?.doAfterTextChanged {
            verifyEmail()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etPassword.editText?.doAfterTextChanged { text ->
            verifyNewPassword(text.toString())
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etPhoneNumber.editText?.doAfterTextChanged {
            verifyPhone()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId==R.id.rbMale){
                isMale=1
            }else if(checkedId==R.id.rbFemale){
                isMale=0
            }
        }
        binding.dropdownType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                isRegisterPolice = if (position == 0){
                    0
                }else{
                    1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                isRegisterPolice = -1
            }
        }

        binding.cbAgreement.setOnCheckedChangeListener { _, isChecked ->
            isAgreementChecked=isChecked
        }

        binding.btnRegister.setOnClickListener {
            mainController?.navigate(ContainerAuthFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
        }
    }

    override fun initObserver() {

    }


    private fun verifyFullName() {
        if ((binding.etName.editText?.text?.length ?: 0) < 3) {
            binding.etName.error = "Minimal 3 Karakter"
        } else {
            binding.etName.error = null
        }
    }

    private fun verifyUsername() {
        if ((binding.etUsername.editText?.text?.length ?: 0) < 3) {
            binding.etUsername.error = "Minimal 3 Karakter"
        } else {
            binding.etUsername.error = null
        }
    }

    private fun verifyEmail() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.editText?.text.toString()).matches()) {
            binding.etEmail.error = "Gunakan format email yang benar"
        } else {
            binding.etEmail.error = null
        }
    }

    private fun verifyNewPassword(text: String) {
        if (text.length < 8 || text == text.lowercase() || text == text.uppercase()) {
            binding.etPassword.error = "Minimal 8 karakter, dan mengandung huruf besar dan kecil"
        } else {
            binding.etPassword.error = null
        }
    }

    private fun verifyPhone() {
        if ((binding.etUsername.editText?.text?.length ?: 0) < 11) {
            binding.etUsername.error = "Minimal 11 Karakter"
        } else {
            binding.etUsername.error = null
        }
    }

    private fun isVerified(): Boolean {
        return binding.etName.error == null &&
                binding.etUsername.error == null &&
                binding.etEmail.error == null &&
                binding.etPassword.error == null &&
                binding.etPhoneNumber.error == null &&
                isMale != -1 &&
                isRegisterPolice != -1&&
                isAgreementChecked
    }
}