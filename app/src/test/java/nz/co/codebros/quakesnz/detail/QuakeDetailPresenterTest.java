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
import nz.co.codebros.quakesnz.interactor.LoadFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.publisher.Publisher;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by leandro on 7/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuakeDetailPresenterTest {

    private QuakeDetailPresenter presenter;

    private Feature feature;

    @Mock
    private QuakeDetailView view;

    @Mock
    private Publisher<Feature> publisher;

    @Mock
    private LoadFeatureInteractor interactor;

    @Mock
    private Throwable e;

    @Mock
    private Disposable d;


    @Captor
    private ArgumentCaptor<CompletableObserver> completableObserverArgumentCaptor;

    @Captor
    private ArgumentCaptor<Consumer<Feature>> consumerArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeDetailPresenter(view, publisher, interactor);
        feature = new Feature();
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
        verify(interactor).execute(eq(publicID), completableObserverArgumentCaptor.capture());
        return completableObserverArgumentCaptor.getValue();
    }

    private Consumer<Feature> getFeatureConsumer(){
        presenter.onViewCreated();
        verify(publisher).subscribe(consumerArgumentCaptor.capture());
        return consumerArgumentCaptor.getValue();
    }
}