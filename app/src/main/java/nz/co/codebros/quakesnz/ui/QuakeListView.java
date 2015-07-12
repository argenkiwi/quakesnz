package nz.co.codebros.quakesnz.ui;

import android.view.View;

import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 9/07/15.
 */
public interface QuakeListView {

    void showProgress();

    void hideProgress();

    void listQuakes(Feature[] features);

    void showQuakeDetail(View view, Feature feature);
}
