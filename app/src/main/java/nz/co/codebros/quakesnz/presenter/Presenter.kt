package nz.co.codebros.quakesnz.presenter

import android.os.Bundle

/**
 * Created by leandro on 24/06/17.
 */

internal interface Presenter {
    fun onViewRestored(bundle: Bundle)

    fun onViewCreated()

    fun onSaveInstanceState(bundle: Bundle)

    fun onDestroyView()
}
