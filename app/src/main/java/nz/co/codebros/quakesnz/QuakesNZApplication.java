package nz.co.codebros.quakesnz;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import nz.co.codebros.quakesnz.component.DaggerApplicationComponent;

/**
 * Created by Leandro on 25/07/2014.
 */
public class QuakesNZApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
