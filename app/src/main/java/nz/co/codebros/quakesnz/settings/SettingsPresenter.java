package nz.co.codebros.quakesnz.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.presenter.BasePresenter;

/**
 * Created by leandro on 6/07/17.
 */

public class SettingsPresenter extends BasePresenter<SettingsView>
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final SharedPreferences preferences;
    private final LoadFeaturesInteractor interactor;

    SettingsPresenter(
            SettingsView view,
            SharedPreferences preferences,
            LoadFeaturesInteractor interactor
    ) {
        super(view);
        this.preferences = preferences;
        this.interactor = interactor;
    }

    @Override
    public void onViewRestored(Bundle bundle) {
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onViewCreated() {
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onDestroyView() {
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        interactor.execute();
    }
}
