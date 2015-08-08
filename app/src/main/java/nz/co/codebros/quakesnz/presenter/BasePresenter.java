package nz.co.codebros.quakesnz.presenter;

/**
 * Created by leandro on 8/08/15.
 */
public class BasePresenter<T> implements Presenter<T> {

    private T view;

    @Override
    public void bindView(T view) {
        this.view = view;
        onBindView();
    }

    protected void onBindView() {

    }

    @Override
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
