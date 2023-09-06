package gemastik.pendekar.ui.main

import android.view.View
import android.view.animation.TranslateAnimation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import gemastik.pendekar.R
import gemastik.pendekar.base.DevFragment
import gemastik.pendekar.databinding.FragmentContainerMainBinding

class ContainerMainFragment : DevFragment<FragmentContainerMainBinding>(R.layout.fragment_container_main) {
    override fun initData() {

    }

    override fun initUI() {
//        val nav = childFragmentManager.findFragmentById(R.id.nav_host_fragment_menu) as NavHostFragment
//        NavigationUI.setupWithNavController(binding.bnMenu, nav.navController)
//        nav.navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.homeFragment, R.id.helpFragment, R.id.accountFragment -> {
//                    showBottomNav()
//                }
//                else -> hideBottomNav()
//            }
//        }
    }

    override fun initAction() {

    }

    override fun initObserver() {

    }

//    private fun showBottomNav(duration: Int = 400) {
//        if (binding.bnMenu.visibility == View.VISIBLE) return
//        binding.bnMenu.visibility = View.VISIBLE
//        val animate = TranslateAnimation(0f, 0f, binding.bnMenu.height.toFloat(), 0f)
//        animate.duration = duration.toLong()
//        binding.bnMenu.startAnimation(animate)
//    }

//    private fun hideBottomNav(duration: Int = 400) {
//        if (binding.bnMenu.visibility == View.GONE) return
//        val animate = TranslateAnimation(0f, 0f, 0f, binding.bnMenu.height.toFloat())
//        animate.duration = duration.toLong()
//        binding.bnMenu.startAnimation(animate)
//        binding.bnMenu.visibility = View.GONE
//    }
}