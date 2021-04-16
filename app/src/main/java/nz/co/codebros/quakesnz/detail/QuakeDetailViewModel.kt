package nz.co.codebros.quakesnz.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nz.co.codebros.quakesnz.detail.model.QuakeDetailModel
import javax.inject.Inject

@HiltViewModel
class QuakeDetailViewModel @Inject constructor(
        val quakeDetailModel: QuakeDetailModel
) : ViewModel()
