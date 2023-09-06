package gemastik.pendekar.ui.main.home.cctv_camera

import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaItem.LiveConfiguration
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentCctvCameraBinding
import gemastik.pendekar.utils.ApplicationContext
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.getViewModel


class CCTVCameraFragment : DevFragment<FragmentCctvCameraBinding>(R.layout.fragment_cctv_camera){
    override val vm: CCTVCameraViewModel by lazy { getViewModel() }
    private val args:CCTVCameraFragmentArgs by navArgs()
    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private lateinit var player:ExoPlayer

    override fun initData() {
        player = ExoPlayer.Builder(context?:ApplicationContext.get())
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context?:ApplicationContext.get())
                    .setLiveTargetOffsetMs(1000))
            .build()
    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuController?.popBackStack()
        }
        if (args.cctvId == "-1"){
            Toast.makeText(context, "ID kamera invalid", Toast.LENGTH_SHORT).show()
        }else {
            vm.getOneCCTV(args.cctvId)
        }
    }

    override fun initObserver() {
        vm.cctvDetail.observe(viewLifecycleOwner){
            when(it){
                is DevState.Success ->{
                    val mediaItem = MediaItem.Builder()
                        .setUri("rtmp://sinar.versa.my.id/output/${it.data.id}")
                        .setLiveConfiguration(LiveConfiguration.Builder().build())
                        .build()
                    player.setMediaItem(mediaItem)
                    player.play()
                    binding.videoCCTV.player = player
                    binding.tvCCTVName.text = it.data.address
                }
                else ->{

                }
            }
        }
    }
}