package nz.co.codebros.quakesnz

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class QuakesNZ : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.factory().create(this)
}
