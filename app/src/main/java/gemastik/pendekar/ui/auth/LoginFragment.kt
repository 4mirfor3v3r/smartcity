package gemastik.pendekar.ui.auth

import android.util.Patterns
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentLoginBinding

class LoginFragment : DevFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val authController by lazy { activity?.findNavController(R.id.nav_host_fragment_auth) }
    private val mainController by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }

    override fun initData() {  }

    override fun initUI() {  }

    override fun initAction() {
//        verifyEmail()
//        verifyNewPassword("")
        binding.etEmail.editText?.doAfterTextChanged {
//            verifyEmail()
            binding.btnLogin.isEnabled = isVerified()
        }
        binding.etPassword.editText?.doAfterTextChanged { text ->
//            verifyNewPassword(text.toString())
            binding.btnLogin.isEnabled = isVerified()
        }

        binding.tvRegister.setOnClickListener {
            authController?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.btnLogin.setOnClickListener {
            mainController?.navigate(ContainerAuthFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
        }
    }

    override fun initObserver() {

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

    private fun isVerified(): Boolean {
        return binding.etEmail.editText?.text?.isNotEmpty() == true &&
                binding.etPassword.editText?.text?.isNotEmpty() == true
    }

}