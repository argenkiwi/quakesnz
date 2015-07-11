package nz.co.codebros.quakesnz;

import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by leandro on 9/07/15.
 */
public interface GeonetService {

    @GET("/quakes/services/{filter}.json")
    void listAllQuakes(@Path("filter") String filter, Callback<FeatureCollection> callback);
}
