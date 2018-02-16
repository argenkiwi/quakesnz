package ar.soflete.daggerlifecycle

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import ar.soflete.daggerlifecycle.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Leandro on 17/02/2018.
 */
abstract class ViewModelActivity<VM : ViewModel> : FragmentActivity() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<VM>
    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }
}