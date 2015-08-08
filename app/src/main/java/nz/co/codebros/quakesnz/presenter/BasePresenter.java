package nz.co.codebros.quakesnz.presenter;

/**
 * Created by leandro on 8/08/15.
 */
public class BasePresenter<T> {

    private T view;

    public void bindView(T view) {
        this.view = view;
        onBindView();
    }

    protected void onBindView() {

    }

    public void unbindView() {
        onUnbindView();
        this.view = null;
    }

    protected void onUnbindView() {

    }

    protected T getView() {
        return view;
    }
}
