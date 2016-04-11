package nz.co.codebros.quakesnz.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import javax.inject.Inject;

import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.component.DaggerSettingsComponent;
import nz.co.codebros.quakesnz.module.SettingsModule;
import nz.co.codebros.quakesnz.presenter.SettingsPresenter;
import nz.co.codebros.quakesnz.view.SettingsView;

/**
 * Created by leandro on 9/08/15.
 */
public class SettingsFragment extends PreferenceFragment implements SettingsView {

    @Inject
    SettingsPresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerSettingsComponent.builder()
                .applicationComponent(QuakesNZApplication.get(context).getComponent())
                .settingsModule(new SettingsModule(this))
                .build().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
