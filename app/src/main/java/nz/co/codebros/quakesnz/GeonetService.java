package nz.co.codebros.quakesnz;

import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leandro on 9/07/15.
 */
public interface GeonetService {
    @GET("/quakes/services/{filter}.json")
    Call<FeatureCollection> listAllQuakes(@Path("filter") String filter);
}
