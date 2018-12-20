package nz.co.codebros.quakesnz.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import nz.co.codebros.quakesnz.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
