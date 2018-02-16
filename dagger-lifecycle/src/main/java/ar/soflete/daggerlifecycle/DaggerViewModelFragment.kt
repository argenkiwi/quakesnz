package ar.soflete.daggerlifecycle

import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by Leandro on 17/02/2018.
 */
abstract class DaggerViewModelFragment<VM : DaggerViewModel> : ViewModelFragment<VM>(),
        HasSupportFragmentInjector {
    override fun supportFragmentInjector() = viewModel.supportFragmentInjector
}