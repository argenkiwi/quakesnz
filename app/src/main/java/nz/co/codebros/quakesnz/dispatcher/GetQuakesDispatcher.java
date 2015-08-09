package nz.co.codebros.quakesnz.dispatcher;

import de.greenrobot.event.EventBus;
import nz.co.codebros.quakesnz.event.BaseFailureEvent;
import nz.co.codebros.quakesnz.event.BaseSuccessEvent;
import nz.co.codebros.quakesnz.event.GetQuakesFailureEvent;
import nz.co.codebros.quakesnz.event.GetQuakesSuccessEvent;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leandro on 8/08/15.
 */
public class GetQuakesDispatcher extends BaseDispatcher<FeatureCollection>{

    public GetQuakesDispatcher(EventBus bus) {
        super(bus);
    }

    @Override
    protected BaseFailureEvent onFailure(RetrofitError error) {
        return new GetQuakesFailureEvent(error);
    }

    @Override
    protected BaseSuccessEvent<FeatureCollection> onSuccess(FeatureCollection featureCollection,
                                                            Response response) {
        return new GetQuakesSuccessEvent(featureCollection, response);
    }

}
