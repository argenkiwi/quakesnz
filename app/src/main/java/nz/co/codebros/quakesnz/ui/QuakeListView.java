package nz.co.codebros.quakesnz.ui;

import android.view.View;

import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 9/07/15.
 */
public interface QuakeListView {
    void hideProgress();

    void listQuakes(Feature[] features);

    void showDownloadFailedMessage();

    /**
     * Launches quake detail screen.
     *
     * @param view    View to animate from.
     * @param feature Quake data.
     */
    void showQuakeDetail(View view, Feature feature);

    void showLoadFailedMessage();

    void showProgress();
}
