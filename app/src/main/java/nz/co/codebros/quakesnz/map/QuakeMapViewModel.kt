package nz.co.codebros.quakesnz.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by Leandro on 16/02/2018.
 */
class QuakeMapViewModel @Inject constructor(
        val quakeMapState: LiveData<QuakeMapState>
) : ViewModel()
