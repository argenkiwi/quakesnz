package nz.co.codebros.quakesnz

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by Leandro on 25/07/2014.
 */
class QuakesNZApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerApplicationComponent.builder().create(this)
}
