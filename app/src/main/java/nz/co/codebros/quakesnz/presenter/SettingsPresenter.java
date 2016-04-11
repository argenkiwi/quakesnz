package nz.co.codebros.quakesnz.presenter;

import android.content.SharedPreferences;

/**
 * Created by leandro on 12/04/16.
 */
public class SettingsPresenter {
    private final SharedPreferences preferences;

    public SettingsPresenter(SharedPreferences preferences) {
        this.preferences = preferences;
    }
}
