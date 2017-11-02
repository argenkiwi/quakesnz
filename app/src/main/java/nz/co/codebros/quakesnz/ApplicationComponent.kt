package nz.co.codebros.quakesnz

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import nz.co.codebros.quakesnz.core.ServicesModule
import nz.co.codebros.quakesnz.module.SubjectsModule
import javax.inject.Singleton

/**
 * Created by leandro on 9/07/15.
 */
@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ServicesModule::class,
        SubjectsModule::class
))
internal interface ApplicationComponent : AndroidInjector<QuakesNZApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<QuakesNZApplication>()
}
