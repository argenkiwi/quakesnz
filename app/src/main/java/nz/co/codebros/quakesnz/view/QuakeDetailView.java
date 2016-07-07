package nz.co.codebros.quakesnz.view;

import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 7/07/16.
 */
public interface QuakeDetailView {
    void share(Feature publicId);

    void showDetails(Feature feature);

    void showLoadingError();
}