package nz.co.codebros.quakesnz.presenter;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nz.co.codebros.quakesnz.interactor.LoadQuakesInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.ui.QuakeListView;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by leandro on 15/07/15.
 */
public class QuakeListPresenterImplTest extends TestCase {

    private QuakeListPresenterImpl quakeListPresenter;

    @Mock
    private LoadQuakesInteractor mockLoadQuakesInteractor;

    @Mock
    private QuakeListView mockQuakesListView;

    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        quakeListPresenter = new QuakeListPresenterImpl(mockLoadQuakesInteractor);
        quakeListPresenter.bindView(mockQuakesListView);
    }

    public void testOnLoadQuakes() throws Exception {
        quakeListPresenter.onLoadQuakes();
        verify(mockQuakesListView).showProgress();
        verify(mockLoadQuakesInteractor).loadQuakes(any(LoadQuakesInteractor.Listener.class));
    }

    public void testOnQuakesDownloaded() throws Exception {
        quakeListPresenter.onQuakesDownloaded();
        verify(mockLoadQuakesInteractor).loadQuakes(any(LoadQuakesInteractor.Listener.class));
    }

    public void testOnQuakesLoaded() throws Exception {
        Feature[] features = new Feature[]{};
        quakeListPresenter.onQuakesLoaded(features);
        verify(mockQuakesListView).hideProgress();
        verify(mockQuakesListView).listQuakes(features);
    }

    public void testOnQuakesLoadFailed() throws Exception {
        quakeListPresenter.onQuakesLoadFailed();
        verify(mockQuakesListView).hideProgress();
        verify(mockQuakesListView).showLoadFailedMessage();
    }

    public void testOnRefresh() throws Exception {
        quakeListPresenter.onRefresh();
        verify(mockQuakesListView).showProgress();
        verify(mockLoadQuakesInteractor).downloadQuakes(any(LoadQuakesInteractor.Listener.class));
    }
}