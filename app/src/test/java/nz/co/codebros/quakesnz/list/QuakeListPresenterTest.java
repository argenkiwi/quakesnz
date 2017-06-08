package nz.co.codebros.quakesnz.list;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import nz.co.codebros.quakesnz.interactor.GetFeaturesInteractor;
import nz.co.codebros.quakesnz.list.QuakeListPresenter;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.list.QuakeListView;

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

    @Mock
    private Disposable d;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeListPresenter(view, interactor);
    }

    @Test
    public void shouldListQuakes() throws IOException {
        Feature[] features = {};
        when(featureCollection.getFeatures()).thenReturn(features);
        presenter.onSuccess(featureCollection);
        verify(view).listQuakes(features);
    }

    @Test
    public void shouldRefreshQuakes() {
        presenter.onRefresh();
        verify(view).showProgress();
        verify(interactor).execute(presenter);
    }

    @Test
    public void shouldDispose(){
        presenter.onSubscribe(d);
        presenter.onDestroyView();
        verify(d).dispose();
    }
}