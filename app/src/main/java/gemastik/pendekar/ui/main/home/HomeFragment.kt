package gemastik.pendekar.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.findNavController
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentHomeBinding

class HomeFragment : DevFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val menuController by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        with(binding){
            btnSelfReport.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToSelfReportFragment())
            }
            btnDangerousPoint.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToVurnPointFragment())
            }
            btnCCTV.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToCCTVFragment())
            }
            btnSafeRoute.setOnClickListener {
                menuController?.navigate(HomeFragmentDirections.actionHomeFragmentToSafeRouteFragment())
            }
            btnMenu.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(context,it)
                popupMenu.menuInflater.inflate(R.menu.menu_home,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
//                        R.id.action_setting ->
//
                        R.id.action_logout ->
                            menuController?.popBackStack()
                    }
                    true
                })
                popupMenu.show()
            }
        }
    }

    override fun initObserver() {

    }
}