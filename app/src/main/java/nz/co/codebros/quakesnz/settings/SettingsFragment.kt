package nz.co.codebros.quakesnz.settings

import android.content.Context
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection
import nz.co.codebros.quakesnz.R

/**
 * Created by leandro on 29/06/17.
 */

class SettingsFragment : PreferenceFragmentCompat(), SettingsView {

    @Inject
    internal lateinit var presenter: SettingsPresenter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) presenter.onRestoreState(savedInstanceState)
        presenter.onViewCreated()
    }
}
