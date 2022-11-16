package gemastik.pendekar

import android.support.multidex.MultiDexApplication
import gemastik.pendekar.utils.ApplicationContext

class PendekarApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        ApplicationContext.getInstance().init(applicationContext)
    }
}