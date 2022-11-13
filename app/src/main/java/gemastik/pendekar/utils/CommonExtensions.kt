package gemastik.pendekar.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import gemastik.pendekar.data.local.DevPreferenceManager

//inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(): T {
//    return ViewModelProvider(this, ViewModelFactory.viewModelFactory)[T::class.java]
//}

fun prefs(context:Context?=null): DevPreferenceManager {
    return DevPreferenceManager(context?:ApplicationContext.get(), "gemastik.pendekar.prefs", Gson())
}

fun logDebug(vararg message: String?) {
    Log.d("TAG_DEBUG", message.toList().toString())
}

fun logError(vararg message: String?) {
    Log.e("TAG_ERROR", message.toList().toString())
}