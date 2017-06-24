package nz.co.codebros.quakesnz.presenter;

import android.os.Bundle;

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

    public abstract void onViewRestored(Bundle bundle);

    public abstract void onViewCreated();

    public abstract void onSaveInstanceState(Bundle bundle);
}
