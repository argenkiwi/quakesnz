package nz.co.codebros.quakesnz.event;

import retrofit.RetrofitError;

/**
 * Created by leandro on 8/08/15.
 */
public class GetQuakesFailureEvent extends BaseFailureEvent {
    public GetQuakesFailureEvent(RetrofitError error) {
        super(error);
    }
}
