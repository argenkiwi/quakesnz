package nz.co.codebros.quakesnz.event;


/**
 * Created by leandro on 8/08/15.
 */
public abstract class BaseFailureEvent {
    private Throwable error;

    public BaseFailureEvent(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }
}
