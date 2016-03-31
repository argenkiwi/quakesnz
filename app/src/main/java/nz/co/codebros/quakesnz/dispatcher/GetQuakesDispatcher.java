package nz.co.codebros.quakesnz.dispatcher;


import org.greenrobot.eventbus.EventBus;

import nz.co.codebros.quakesnz.event.BaseFailureEvent;
import nz.co.codebros.quakesnz.event.BaseSuccessEvent;
import nz.co.codebros.quakesnz.event.GetQuakesFailureEvent;
import nz.co.codebros.quakesnz.event.GetQuakesSuccessEvent;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit2.Response;


/**
 * Created by leandro on 8/08/15.
 */
public class GetQuakesDispatcher extends BaseDispatcher<FeatureCollection> {
    public GetQuakesDispatcher(EventBus bus) {
        super(bus);
    }

    @Override
    protected BaseFailureEvent onFailure(Throwable error) {
        return new GetQuakesFailureEvent(error);
    }

    @Override
    protected BaseSuccessEvent<FeatureCollection> onSuccess(Response<FeatureCollection> response) {
        return new GetQuakesSuccessEvent(response);
    }
}
