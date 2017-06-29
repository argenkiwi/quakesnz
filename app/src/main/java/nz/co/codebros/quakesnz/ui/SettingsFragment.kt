package nz.co.codebros.quakesnz.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import nz.co.codebros.quakesnz.R

/**
 * Created by leandro on 9/08/15.
 */
class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    override fun onStart() {
        super.onStart()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}
