package nz.co.codebros.quakesnz

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import nz.co.codebros.quakesnz.core.ServicesModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    QuakesNZModule::class,
    ServicesModule::class
])
interface QuakesNZComponent : AndroidInjector<QuakesNZApplication> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<QuakesNZApplication>
}
