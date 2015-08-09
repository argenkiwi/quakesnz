package nz.co.codebros.quakesnz.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import javax.inject.Inject;

import dagger.Component;
import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.component.ApplicationComponent;
import nz.co.codebros.quakesnz.event.GetQuakesRequestEvent;
import nz.co.codebros.quakesnz.scope.FragmentScope;

/**
 * Created by leandro on 9/08/15.
 */
public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    EventBus mBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSettingsFragment_FragmentComponent.builder()
                .applicationComponent(QuakesNZApplication.get(getActivity())
                        .getApplicationComponent())
                .build().inject(this);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("pref_filter")){
            mBus.post(new GetQuakesRequestEvent());
        }
    }

    @FragmentScope
    @Component(dependencies = ApplicationComponent.class)
    public interface FragmentComponent {
        void inject(SettingsFragment fragment);
    }
}
