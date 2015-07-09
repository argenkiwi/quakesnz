package nz.co.codebros.quakesnz.presenter;

import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.ui.QuakeListView;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenterImpl implements QuakeListPresenter, LoadQuakesInteractor.Listener {

    private final QuakeListView mView;
    private final LoadQuakesInteractor mInteractor;

    public QuakeListPresenterImpl(QuakeListView view, LoadQuakesInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void onLoadQuakes(int scope) {
        mView.showProgress();
        mInteractor.loadQuakes(scope, this);
    }

    @Override
    public void onQuakesLoaded() {
        mView.hideProgress();
        mView.listQuakes();
    }
}
