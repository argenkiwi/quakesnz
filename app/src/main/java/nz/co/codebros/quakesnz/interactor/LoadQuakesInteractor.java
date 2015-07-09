package nz.co.codebros.quakesnz.interactor;

/**
 * Created by leandro on 9/07/15.
 */
public interface LoadQuakesInteractor {
    void loadQuakes(int scope, Listener listener);

    interface Listener {
        void onQuakesLoaded();
    }
}
