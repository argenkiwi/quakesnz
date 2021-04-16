package nz.co.codebros.quakesnz.core.usecase

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import nz.co.codebros.quakesnz.core.data.Feature
import javax.inject.Inject

class LoadFeatureUseCase @Inject constructor(private val store: Store<String, Feature>) {

    suspend fun execute(publicId: String, refresh: Boolean = true): Result<Feature> = try {
        when {
            refresh -> store.fresh(publicId)
            else -> store.get(publicId)
        }.let { Result.Success(it) }
    } catch (t: Throwable) {
        Result.Failure(t)
    }
}
