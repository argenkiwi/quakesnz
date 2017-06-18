package nz.co.codebros.quakesnz;

import io.reactivex.Single;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by leandro on 9/07/15.
 */
public interface GeonetService {

    @GET("quake/{publicID}")
    Single<FeatureCollection> getQuake(@Path("publicID") String publicID);

    @GET("quake")
    Single<FeatureCollection> getQuakes(@Query("MMI") int mmi);
}
