package nz.co.codebros.quakesnz.dispatcher;

import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.event.BaseFailureEvent;
import nz.co.codebros.quakesnz.event.BaseSuccessEvent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leandro on 8/08/15.
 */
public abstract class BaseDispatcher<T> implements Callback<T> {
    private final EventBus mBus;

    public BaseDispatcher(EventBus bus) {
        mBus = bus;
    }

    @Override
    public final void failure(RetrofitError error) {
        mBus.post(onFailure(error));
    }

    @Override
    public final void success(T t, Response response) {
        mBus.post(onSuccess(t, response));
    }

    protected abstract BaseFailureEvent onFailure(RetrofitError error);

    protected abstract BaseSuccessEvent<T> onSuccess(T t, Response response);
}
