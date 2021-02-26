package nz.co.codebros.quakesnz.core.usecase

import android.content.SharedPreferences
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import javax.inject.Inject

class LoadFeaturesUseCase @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val store: Store<Int, FeatureCollection>
) {
    suspend fun execute(refresh: Boolean = false): FeatureCollection {
        val mmi = sharedPreferences.getString("pref_intensity", null)?.toInt() ?: 3
        return when {
            refresh -> store.fresh(mmi)
            else -> store.get(mmi)
        }
    }
}
