package nz.co.codebros.quakesnz;

import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by leandro on 9/07/15.
 */
public interface GeonetService {

    @GET("quake/{publicID}")
    Observable<FeatureCollection> getQuake(@Path("publicID") String publicID);

    @GET("quake")
    Observable<FeatureCollection> getQuakes(@Query("MMI") int mmi);

    @GET("quake")
    @Headers("Cache-Control: public, only-if-cached, max-stale=3600")
    Observable<FeatureCollection> getQuakesCached(@Query("MMI") int mmi);
}
