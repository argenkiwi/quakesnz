package nz.co.codebros.quakesnz

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class QuakesNZApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerQuakesNZComponent.builder().create(this)
}
