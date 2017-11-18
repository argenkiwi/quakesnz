package nz.co.codebros.quakesnz.repository

import io.reactivex.subjects.Subject
import nz.co.codebros.quakesnz.core.BaseRepository
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import javax.inject.Inject

/**
 * Created by leandro on 18/06/17.
 */

class FeatureCollectionRepository @Inject constructor(
        subject: Subject<FeatureCollection>
) : BaseRepository<FeatureCollection>(subject)