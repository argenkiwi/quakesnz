package nz.co.codebros.quakesnz.settings

import android.content.SharedPreferences

import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor
import nz.co.codebros.quakesnz.core.BasePresenter

/**
 * Created by leandro on 6/07/17.
 */

class SettingsPresenter internal constructor(
        view: SettingsView,
        private val preferences: SharedPreferences,
        private val interactor: LoadFeaturesInteractor
) : BasePresenter<SettingsView, Unit>(view), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onViewCreated() {
        super.onViewCreated()
        preferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        preferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        addDisposable(interactor.execute())
    }
}
