package nz.co.codebros.quakesnz.view;

import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 9/07/15.
 */
public interface QuakeListView {
    void hideProgress();

    void listQuakes(Feature[] features);

    void showDownloadFailedMessage();

    void showLoadFailedMessage();

    void showProgress();
}