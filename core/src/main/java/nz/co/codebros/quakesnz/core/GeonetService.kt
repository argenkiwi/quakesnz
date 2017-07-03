package nz.co.codebros.quakesnz.core

import io.reactivex.Single
import nz.co.codebros.quakesnz.core.model.FeatureCollection
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by leandro on 9/07/15.
 */
interface GeonetService {

    @GET("quake/{publicID}")
    fun getQuake(@Path("publicID") publicID: String): Single<FeatureCollection>

    @GET("quake")
    fun getQuakes(@Query("MMI") mmi: Int): Single<FeatureCollection>
}
