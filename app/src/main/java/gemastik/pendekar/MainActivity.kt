package gemastik.pendekar

import com.google.android.libraries.places.api.Places
import gemastik.pendekar.base.DevActivity
import gemastik.pendekar.databinding.ActivityMainBinding

class MainActivity : DevActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initData() {
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
    }

    override fun initUI() {

    }

    override fun initAction() {

    }

    override fun initObserver() {

    }
}