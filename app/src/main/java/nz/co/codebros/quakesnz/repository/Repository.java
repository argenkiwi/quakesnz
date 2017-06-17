package nz.co.codebros.quakesnz.repository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by leandro on 17/06/17.
 */

public class Repository<T> {
    private Observable<T> observable;

    public Repository(Observable<T> observable ){
        this.observable = observable;
    }

    public Disposable subscribe(Consumer<T> consumer){
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
