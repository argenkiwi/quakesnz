package nz.co.codebros.quakesnz;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import nz.co.codebros.quakesnz.component.ApplicationComponent;
import nz.co.codebros.quakesnz.component.DaggerApplicationComponent;
import nz.co.codebros.quakesnz.module.ApplicationModule;

/**
 * Created by Leandro on 25/07/2014.
 */
public class QuakesNZApplication extends Application {

    @Inject
    RequestHandler requestHandler;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static QuakesNZApplication get(Context context) {
        return ((QuakesNZApplication) context.getApplicationContext());
    }
}
