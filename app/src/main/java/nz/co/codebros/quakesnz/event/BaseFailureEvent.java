package nz.co.codebros.quakesnz.event;

import retrofit.RetrofitError;

/**
 * Created by leandro on 8/08/15.
 */
public abstract class BaseFailureEvent {
    private RetrofitError error;

    public BaseFailureEvent(RetrofitError error){
        this.error = error;
    }

    public RetrofitError getError() {
        return error;
    }
}
