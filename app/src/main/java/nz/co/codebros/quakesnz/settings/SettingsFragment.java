package nz.co.codebros.quakesnz.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import nz.co.codebros.quakesnz.R;

/**
 * Created by leandro on 29/06/17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsView {

    @Inject
    SettingsPresenter presenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) presenter.onRestoreState(savedInstanceState);
        presenter.onViewCreated();
    }
}
