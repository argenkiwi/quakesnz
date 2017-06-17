package nz.co.codebros.quakesnz.repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * Created by leandro on 18/06/17.
 */

public abstract class Publisher<T> {

    private final Subject<T> subject;

    Publisher(Subject<T> subject) {
        this.subject = subject;
    }

    void publish(T value){
        subject.onNext(value);
    }

    public Disposable subscribe(Consumer<T> consumer) {
        return subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }
}
