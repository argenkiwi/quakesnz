package nz.co.codebros.quakesnz.core

/**
 * Created by leandro on 24/06/17.
 */

interface Presenter<in P> {
    fun onInit(props: P? = null)

    fun onViewCreated()

    fun onDestroyView()
}
