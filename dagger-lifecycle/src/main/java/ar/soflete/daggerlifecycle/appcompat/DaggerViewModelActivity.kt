package ar.soflete.daggerlifecycle.appcompat

import ar.soflete.daggerlifecycle.DaggerViewModel
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by Leandro on 17/02/2018.
 */
abstract class DaggerViewModelActivity<VM : DaggerViewModel> : ViewModelActivity<VM>(),
        HasFragmentInjector, HasSupportFragmentInjector {
    override fun fragmentInjector() = viewModel.frameworkFragmentInjector
    override fun supportFragmentInjector() = viewModel.supportFragmentInjector
}
