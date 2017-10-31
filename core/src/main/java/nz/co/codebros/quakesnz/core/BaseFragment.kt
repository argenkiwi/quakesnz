package nz.co.codebros.quakesnz.core

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import dagger.android.support.AndroidSupportInjection

/**
 * Created by leandro on 6/07/17.
 */

abstract class BaseFragment<P> : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arguments = arguments
        when {
            savedInstanceState != null -> presenter.onRestoreState(savedInstanceState)
            arguments != null -> presenter.onInit(fromArguments(arguments))
            else -> presenter.onInit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveState(outState)
    }

    protected open fun fromArguments(bundle: Bundle): P? = null

    protected abstract val presenter: BasePresenter<*, P>
}
