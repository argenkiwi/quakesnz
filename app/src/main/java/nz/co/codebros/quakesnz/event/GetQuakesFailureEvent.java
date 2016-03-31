package nz.co.codebros.quakesnz.event;

/**
 * Created by leandro on 8/08/15.
 */
public class GetQuakesFailureEvent extends BaseFailureEvent {
    public GetQuakesFailureEvent(Throwable error) {
        super(error);
    }
}
