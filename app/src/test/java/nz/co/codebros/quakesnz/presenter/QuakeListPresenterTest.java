package nz.co.codebros.quakesnz.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.view.QuakeListView;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by leandro on 2/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuakeListPresenterTest {
    private QuakeListPresenter presenter;

    @Mock
    private QuakeListView view;

    @Mock
    private GetFeaturesInteractor interactor;

    @Mock
    private FeatureCollection featureCollection;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeListPresenter(view, interactor);
    }

    @Test
    public void shouldCancelCall() {
        presenter.onDestroyView();
        interactor.cancel();
    }

    @Test
    public void shouldListQuakes() throws IOException {
        Feature[] features = {};
        when(featureCollection.getFeatures()).thenReturn(features);
        presenter.onNext(featureCollection);
        verify(view).listQuakes(features);
    }

    @Test
    public void shouldRefreshQuakes() {
        presenter.onRefresh();
        verify(view).showProgress();
        verify(interactor).execute(presenter);
    }
}