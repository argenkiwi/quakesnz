package nz.co.codebros.quakesnz.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import nz.co.codebros.quakesnz.R

/**
 * Created by leandro on 29/06/17.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
