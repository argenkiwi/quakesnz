package nz.co.codebros.quakesnz.event;

import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.Response;

/**
 * Created by leandro on 8/08/15.
 */
public class GetQuakesSuccessEvent extends BaseSuccessEvent<FeatureCollection> {
    public GetQuakesSuccessEvent(Response<FeatureCollection> response) {
        super(response);
    }
}
