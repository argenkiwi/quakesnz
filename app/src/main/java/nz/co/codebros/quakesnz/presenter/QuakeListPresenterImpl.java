package nz.co.codebros.quakesnz.presenter;

import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.ui.QuakeListView;

/**
 * Created by leandro on 9/07/15.
 */
public class QuakeListPresenterImpl implements QuakeListPresenter, LoadQuakesInteractor.Listener {

    private final LoadQuakesInteractor mInteractor;
    private QuakeListView mView;

    public QuakeListPresenterImpl(LoadQuakesInteractor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onLoadQuakes(int scope) {
        mView.showProgress();
        mInteractor.loadQuakes(scope, this);
    }

    @Override
    public void onQuakesLoaded() {
        if(mView != null) {
            mView.hideProgress();
            mView.listQuakes();
        }
    }

    @Override
    public void bindView(QuakeListView view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        mView = null;
    }
}
