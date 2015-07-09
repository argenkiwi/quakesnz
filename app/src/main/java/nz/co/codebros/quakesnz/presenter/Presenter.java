package nz.co.codebros.quakesnz.presenter;

/**
 * Created by leandro on 10/07/15.
 */
public interface Presenter<T> {
    void bindView(T view);
    void unbindView();
}
