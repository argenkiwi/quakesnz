package nz.co.codebros.quakesnz.event;

import retrofit.client.Response;

/**
 * Created by leandro on 8/08/15.
 */
public abstract class BaseSuccessEvent<T> {
    private T data;
    private Response response;

    public BaseSuccessEvent(T data, Response response) {
        this.data = data;
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }
}
