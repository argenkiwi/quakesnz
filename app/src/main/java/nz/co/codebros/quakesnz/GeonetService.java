package nz.co.codebros.quakesnz;

import io.reactivex.Observable;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by leandro on 9/07/15.
 */
public interface GeonetService {

    @GET("quake/{publicID}")
    Observable<FeatureCollection> getQuake(@Path("publicID") String publicID);

    @GET("quake")
    Observable<FeatureCollection> getQuakes(@Query("MMI") int mmi);
}
