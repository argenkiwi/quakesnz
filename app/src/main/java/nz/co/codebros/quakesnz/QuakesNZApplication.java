package nz.co.codebros.quakesnz;

import android.app.Application;
import android.content.Context;

import nz.co.codebros.quakesnz.component.ApplicationComponent;
import nz.co.codebros.quakesnz.component.DaggerApplicationComponent;
import nz.co.codebros.quakesnz.module.ApplicationModule;

/**
 * Created by Leandro on 25/07/2014.
 */
public class QuakesNZApplication extends Application {

    private final ApplicationComponent component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();

    public static QuakesNZApplication get(Context context) {
        return (QuakesNZApplication) context.getApplicationContext();
    }

    public final ApplicationComponent getComponent() {
        return component;
    }
}
