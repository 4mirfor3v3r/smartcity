package gemastik.pendekar.ui.auth

import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentRegisterBinding

class RegisterFragment : DevFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val authController by lazy { activity?.findNavController(R.id.nav_host_fragment_auth) }
    private val mainController by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }

    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            authController?.popBackStack()
        }

        binding.cbAgreement.setOnCheckedChangeListener { _, isChecked ->
            binding.btnRegister.isEnabled = isChecked
        }

        binding.btnRegister.setOnClickListener {
            mainController?.navigate(ContainerAuthFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
        }
    }

    override fun initObserver() {

    }
}