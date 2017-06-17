package nz.co.codebros.quakesnz.repository;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * Created by leandro on 18/06/17.
 */

public abstract class Repository<T> extends Publisher<T> {

    public Repository(Subject subject) {
        super(subject);
    }

    protected Completable load(Single<T> single) {
        return single
                .doAfterSuccess(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T it) throws Exception {
                        publish(it);
                    }
                })
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
