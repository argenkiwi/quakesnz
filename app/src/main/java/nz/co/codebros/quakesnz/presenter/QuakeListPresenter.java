package nz.co.codebros.quakesnz.presenter;

import nz.co.codebros.quakesnz.ui.QuakeListView;

/**
 * Created by leandro on 9/07/15.
 */
public interface QuakeListPresenter extends Presenter<QuakeListView> {
    void onLoadQuakes();

    void onRefresh();
}
