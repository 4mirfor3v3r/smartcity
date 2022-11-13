package gemastik.pendekar.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import at.favre.lib.armadillo.Armadillo
import at.favre.lib.armadillo.ArmadilloSharedPreferences
import com.google.gson.Gson

class DevPreferenceManager(private val context: Context, private val prefName: String, private val gson: Gson) {

    /**
     * sharedPreference that will be used
     */
    private val mPreferences: ArmadilloSharedPreferences by lazy {
        Armadillo.create(context, prefName)
            .encryptionFingerprint(context, getDeviceId(context))
            .build()
    }

    /**
     * Method to get boolean from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return boolean value of desired preference
     */
    fun getBoolean(key: String, default: Boolean): Boolean {
        return mPreferences.getBoolean(key, default)
    }

    /**
     * Method to set boolean to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun setBoolean(key: String, value: Boolean) {
        mPreferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Method to get int from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return int value of desired preference
     */
    fun getInt(key: String, default: Int): Int {
        return mPreferences.getInt(key, default)
    }

    /**
     * Method to set int to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun setInt(key: String, value: Int) {
        mPreferences.edit().putInt(key, value).apply()
    }

    /**
     * Method to get string from preference
     * @param key preference's name
     * @param default preference's default value when the value is null
     * @return string value of desired preference
     */
    fun getString(key: String, default: String): String {
        return mPreferences.getString(key, default)!!
    }

    /**
     * Method to set string to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun setString(key: String, value: String) {
        mPreferences.edit().putString(key, value).apply()
    }

    /**
     * Method to get long from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return long value of desired preference
     */
    fun getLong(key: String, default: Long): Long {
        return mPreferences.getLong(key, default)
    }

    /**
     * Method to set long to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun setLong(key: String, value: Long) {
        mPreferences.edit().putLong(key, value).apply()
    }

    /**
     * Method to get float from preference
     * @param key preference's name
     * @param default preference's value when the value is null
     * @return float value of desired preference
     */
    fun getFloat(key: String, default: Float): Float {
        return mPreferences.getFloat(key, default)
    }

    /**
     * Method to set float to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun setFloat(key: String, value: Float) {
        mPreferences.edit().putFloat(key, value).apply()
    }

    /**
     * Method to get object from preference
     * @param key preference's name
     * @return object value of desired preference
     */
    fun <T> getObject(key: String, type: Class<T>): T? {
        val json = getString(key, "")

        return if (json.isNotEmpty())
            try {
                gson.fromJson(json, type)
            } catch (exception: Exception) {
                null
            }
        else
            null
    }

    /**
     * Method to set object to preference
     * @param key preference's name
     * @param value preference's value
     */
    fun setObject(key: String, value: Any) {
        val json = gson.toJson(value)
        mPreferences.edit().putString(key, json).apply()
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}