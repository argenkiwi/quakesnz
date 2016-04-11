package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import dagger.Component;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.component.ApplicationComponent;
import nz.co.codebros.quakesnz.scope.FragmentScope;

/**
 * Created by leandro on 9/08/15.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerSettingsFragment_FragmentComponent.builder()
                .applicationComponent(QuakesNZApplication.get(getActivity())
                        .getComponent())
                .build().inject(this);
        addPreferencesFromResource(R.xml.preferences);
    }

    @FragmentScope
    @Component(dependencies = ApplicationComponent.class)
    public interface FragmentComponent {
        void inject(SettingsFragment fragment);
    }
}
