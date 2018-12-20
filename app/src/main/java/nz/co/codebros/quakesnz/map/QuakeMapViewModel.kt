package nz.co.codebros.quakesnz.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nz.co.codebros.quakesnz.map.model.QuakeMapState
import javax.inject.Inject

class QuakeMapViewModel @Inject constructor(
        val quakeMapState: LiveData<QuakeMapState>
) : ViewModel()
