package nz.co.codebros.quakesnz.core

import nz.co.codebros.quakesnz.core.data.FeatureCollection
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeonetService {

    @GET("quake/{publicID}")
    suspend fun getQuake(@Path("publicID") publicID: String): FeatureCollection

    @GET("quake")
    suspend fun getQuakes(@Query("MMI") mmi: Int): FeatureCollection
}
