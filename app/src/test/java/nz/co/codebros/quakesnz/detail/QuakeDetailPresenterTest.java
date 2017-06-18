package nz.co.codebros.quakesnz.detail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractorImpl;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.repository.Publisher;

import static org.mockito.Matchers.eq;
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
    private Publisher<Feature> publisher;

    @Mock
    private LoadFeatureInteractorImpl interactor;

    @Mock
    private Throwable e;

    @Mock
    private Disposable d;

    @Mock
    private Feature feature;

    @Captor
    private ArgumentCaptor<CompletableObserver> completableObserverArgumentCaptor;

    @Captor
    private ArgumentCaptor<Consumer<Feature>> consumerArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeDetailPresenter(view, publisher, interactor);
    }

    @Test
    public void shouldShowLoadingError(){
        getCompletableObserver().onError(e);
        verify(view).showLoadingError();
    }

    public void shouldDispose() {
        getCompletableObserver().onSubscribe(d);
        presenter.onDestroyView();
        verify(d).dispose();
    }

    @Test
    public void shouldShowDetails() throws Exception {
        getFeatureConsumer().accept(feature);
        verify(view).showDetails(feature);
    }

    private CompletableObserver getCompletableObserver() {
        final String publicID = "";
        presenter.onRefresh(publicID);
        verify(interactor).execute(completableObserverArgumentCaptor.capture(), eq(publicID));
        return completableObserverArgumentCaptor.getValue();
    }

    private Consumer<Feature> getFeatureConsumer(){
        presenter.onCreateView();
        verify(publisher).subscribe(consumerArgumentCaptor.capture());
        return consumerArgumentCaptor.getValue();
    }
}