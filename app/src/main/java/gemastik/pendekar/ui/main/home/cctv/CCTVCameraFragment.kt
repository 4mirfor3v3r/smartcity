package gemastik.pendekar.ui.main.home.cctv

import android.net.Uri
import android.widget.MediaController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentCctvCameraBinding


class CCTVCameraFragment : DevFragment<FragmentCctvCameraBinding>(R.layout.fragment_cctv_camera){
    private val args:CCTVCameraFragmentArgs by navArgs()
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    override fun initData() {

    }

    override fun initUI() {
        binding.tvCCTVName.text = args.title
        binding.tvAddress.text = args.address

        //Creating MediaController
        val mediaController = MediaController(binding.videoCCTV.context)
        mediaController.hide()
        val path = "android.resource://" + context?.packageName + "/raw/cctv_${args.cctvId}"
        mediaController.setAnchorView(binding.videoCCTV)
//        binding.videoCCTV.setMediaController(mediaController)
        binding.videoCCTV.setVideoPath(path)
        binding.videoCCTV.requestFocus()
        binding.videoCCTV.start()
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
    }

    override fun initObserver() {

    }
}