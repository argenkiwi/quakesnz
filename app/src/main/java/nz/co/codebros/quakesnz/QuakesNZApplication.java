package nz.co.codebros.quakesnz;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import nz.co.codebros.quakesnz.component.ApplicationComponent;
import nz.co.codebros.quakesnz.component.DaggerApplicationComponent;
import nz.co.codebros.quakesnz.module.ApplicationModule;

/**
 * Created by Leandro on 25/07/2014.
 */
public class QuakesNZApplication extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private final ApplicationComponent component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();

    public static QuakesNZApplication get(Context context) {
        return (QuakesNZApplication) context.getApplicationContext();
    }

    public final ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
