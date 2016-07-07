package nz.co.codebros.quakesnz.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nz.co.codebros.quakesnz.interactor.GetFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.view.QuakeDetailView;

import static org.mockito.Mockito.verify;

/**
 * Created by leandro on 7/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuakeDetailPresenterTest {

    private QuakeDetailPresenter presenter;

    @Mock
    private QuakeDetailView view;

    @Mock
    private GetFeatureInteractor interactor;

    @Mock
    private Feature feature;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeDetailPresenter(view, interactor);
    }

    @Test
    public void shouldShowDetails() {
        presenter.onInit(feature);

        verify(view).showDetails(feature);
    }

    @Test
    public void shouldGetFeature() {
        final String publicID = "";
        presenter.onInit(publicID);

        verify(interactor).execute(presenter, publicID);
    }

    @Test
    public void shouldShowDetailOnNext() {
        presenter.onNext(feature);

        verify(view).showDetails(feature);
    }
}