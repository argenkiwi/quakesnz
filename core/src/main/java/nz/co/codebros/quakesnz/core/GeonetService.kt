package nz.co.codebros.quakesnz.core

import io.reactivex.Observable
import nz.co.codebros.quakesnz.core.data.FeatureCollection
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeonetService {

    @GET("quake/{publicID}")
    fun getQuake(@Path("publicID") publicID: String): Observable<FeatureCollection>

    @GET("quake")
    fun getQuakes(@Query("MMI") mmi: Int): Observable<FeatureCollection>
}
