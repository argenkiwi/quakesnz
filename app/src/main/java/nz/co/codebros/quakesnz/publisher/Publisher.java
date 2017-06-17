package nz.co.codebros.quakesnz.publisher;

import io.reactivex.Observer;

/**
 * Created by leandro on 17/06/17.
 */
public class Publisher<T> {
    private Observer<T> observer;

    public Publisher(Observer<T> observer){
        this.observer = observer;
    }

    public void publish(T value){
        observer.onNext(value);
    }
}
