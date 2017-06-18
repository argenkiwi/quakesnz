package nz.co.codebros.quakesnz.publisher;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by leandro on 18/06/17.
 */

public class Publisher<T> {

    private final Observable<T> observable;

    public Publisher(Observable<T> observable) {
        this.observable = observable;
    }

    public Disposable subscribe(Consumer<T> consumer) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
