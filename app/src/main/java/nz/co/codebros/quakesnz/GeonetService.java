package nz.co.codebros.quakesnz;

import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by leandro on 9/07/15.
 */
public interface GeonetService {
    @GET("/quakes/services/{filter}.json")
    Observable<FeatureCollection> listAllQuakes(@Path("filter") String filter);
}
