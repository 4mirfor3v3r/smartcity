package gemastik.pendekar.ui.auth

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
        binding.etEmail.editText?.doAfterTextChanged { text ->
            if (!text.isNullOrEmpty()){
                binding.btnLogin.isEnabled = true
            }
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

}