package nz.co.codebros.quakesnz.event;


import retrofit2.Response;

/**
 * Created by leandro on 8/08/15.
 */
public abstract class BaseSuccessEvent<T> {
    private final Response<T> response;

    public BaseSuccessEvent(Response<T> response) {
        this.response = response;
    }

    public T getData() {
        return response.body();
    }

    public Response getResponse() {
        return response;
    }
}
