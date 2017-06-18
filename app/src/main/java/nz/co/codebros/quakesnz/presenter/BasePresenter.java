package nz.co.codebros.quakesnz.presenter;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * Created by leandro on 19/06/17.
 */

public abstract class BasePresenter {
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    protected void disposeAll(){
        while (!disposables.isEmpty()) {
            disposables.remove(0).dispose();
        }
    }
}
