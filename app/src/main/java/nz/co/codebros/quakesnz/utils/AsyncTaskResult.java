package nz.co.codebros.quakesnz.utils;

/**
 * Created by leandro on 9/08/15.
 */
public class AsyncTaskResult<T> {
    private Exception error;
    private T result;

    public AsyncTaskResult(T result) {
        this.result = result;
    }

    public AsyncTaskResult(Exception error) {
        this.error = error;
    }

    public Exception getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

    public boolean isError() {
        return this.error != null;
    }
}
