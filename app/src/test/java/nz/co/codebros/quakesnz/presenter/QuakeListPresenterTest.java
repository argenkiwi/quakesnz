package nz.co.codebros.quakesnz.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.LoadCitiesHelper;
import nz.co.codebros.quakesnz.view.QuakeListView;
import retrofit2.Call;
import retrofit2.Callback;

import static org.mockito.Matchers.anyString;
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
    private GeonetService service;

    @Mock
    private Call<FeatureCollection> call;

    @Mock
    private LoadCitiesHelper helper;
    private Feature feature;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeListPresenter(view, service, helper);
        when(service.listAllQuakes(anyString())).thenReturn(call);
    }

    @Test
    public void shouldCancelCall() {
        presenter.onViewCreated("");
        presenter.onDestroyView();
        call.cancel();
    }

    @Test
    public void shouldLoadQuakes() {
        String filter = "filter";
        presenter.onViewCreated(filter);
        verify(view).showProgress();
        verify(service).listAllQuakes(filter);
        verify(call).enqueue(Matchers.<Callback<FeatureCollection>>any());
    }

    @Test
    public void shouldListQuakes() throws IOException {
        Feature[] features = {};
        presenter.onFeaturesLoaded(features);
        verify(helper).execute(features);
        verify(view).listQuakes(features);
        verify(view).hideProgress();
    }

    @Test
    public void shouldRefreshQuakes() {
        String filter = "felt";
        presenter.onRefresh(filter);
        verify(view).showProgress();
        verify(call).enqueue(Matchers.<Callback<FeatureCollection>>any());
    }

    @Test
    public void shouldShowError() {
        presenter.onFeaturesFailedToLoad();
        verify(view).hideProgress();
        verify(view).showDownloadFailedMessage();
    }
}