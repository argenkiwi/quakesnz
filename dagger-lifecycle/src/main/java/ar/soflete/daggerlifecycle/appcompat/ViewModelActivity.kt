package ar.soflete.daggerlifecycle.appcompat

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import ar.soflete.daggerlifecycle.ViewModelFactory
import javax.inject.Inject

/**
 * Created by Leandro on 17/02/2018.
 */
abstract class ViewModelActivity<VM : ViewModel> : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ViewModelProviders.of(this, viewModelFactory)
                .let { viewModelProvider -> onCreateViewModel(viewModelProvider) }
                .let { viewModel -> onViewModelCreated(viewModel) }
    }

    protected abstract fun onCreateViewModel(viewModelProvider: ViewModelProvider): VM
    protected abstract fun onViewModelCreated(viewModel: VM)
}