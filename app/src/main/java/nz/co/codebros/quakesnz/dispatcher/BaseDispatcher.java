package nz.co.codebros.quakesnz.dispatcher;

import org.greenrobot.eventbus.EventBus;

import nz.co.codebros.quakesnz.event.BaseFailureEvent;
import nz.co.codebros.quakesnz.event.BaseSuccessEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by leandro on 8/08/15.
 */
public abstract class BaseDispatcher<T> implements Callback<T> {
    private final EventBus mBus;

    public BaseDispatcher(EventBus bus) {
        mBus = bus;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mBus.post(onFailure(t));
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        mBus.post(onSuccess(response));
    }

    protected abstract BaseFailureEvent onFailure(Throwable error);

    protected abstract BaseSuccessEvent<T> onSuccess(Response<T> response);
}
