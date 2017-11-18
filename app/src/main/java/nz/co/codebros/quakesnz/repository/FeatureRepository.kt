package nz.co.codebros.quakesnz.repository

import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.BaseRepository
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

/**
 * Created by leandro on 19/06/17.
 */

class FeatureRepository @Inject constructor(
        subject: Subject<Feature>
) : BaseRepository<Feature>(subject)
