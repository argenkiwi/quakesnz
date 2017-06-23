package nz.co.codebros.quakesnz.list;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.interactor.LoadFeaturesInteractor;
import nz.co.codebros.quakesnz.interactor.SelectFeatureInteractor;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.publisher.Publisher;

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
    private LoadFeaturesInteractor interactor;

    @Mock
    private Publisher<FeatureCollection> publisher;

    @Mock
    private SelectFeatureInteractor selectFeatureInteractor;

    @Captor
    private ArgumentCaptor<CompletableObserver> completableObserverArgumentCaptor;

    @Captor
    private ArgumentCaptor<Consumer<FeatureCollection>> consumerArgumentCaptor;

    @Mock
    private FeatureCollection featureCollection;

    @Mock
    private Disposable d;

    @Mock
    private Throwable e;

    @Mock
    private Feature feature;

    @Before
    public void setUp() throws Exception {
        presenter = new QuakeListPresenter(view, interactor, publisher, selectFeatureInteractor);
    }

    @Test
    public void shouldShowProgress() {
        getCompletableObserver().onSubscribe(d);
        verify(view).showProgress();
    }

    @Test
    public void shouldHideProgress() {
        getCompletableObserver().onComplete();
        verify(view).hideProgress();
    }

    @Test
    public void shouldHideProgressOnError() {
        getCompletableObserver().onError(e);
        verify(view).hideProgress();
    }

    @Test
    public void shouldShowError() {
        getCompletableObserver().onError(e);
        verify(view).showError();
    }

    @Test
    public void shouldDisposeCompletable() {
        getCompletableObserver().onSubscribe(d);
        presenter.onDestroyView();
        verify(d).dispose();
    }

    private CompletableObserver getCompletableObserver() {
        presenter.onRefresh();
        verify(interactor).execute(completableObserverArgumentCaptor.capture());
        return completableObserverArgumentCaptor.getValue();
    }

    @Test
    public void shouldListQuakes() throws Exception {
        Feature[] features = {};
        when(featureCollection.getFeatures()).thenReturn(features);
        getFeatureCollectionConsumer().accept(featureCollection);
        verify(view).listQuakes(features);
    }

    @Test
    public void shouldDisposeConsumer() {
        getFeatureCollectionConsumer();
        presenter.onDestroyView();
        verify(d).dispose();
    }

    @Test
    public void shouldSelectFeature(){
        presenter.onFeatureSelected(feature);
        verify(selectFeatureInteractor).execute(feature);
    }

    private Consumer<FeatureCollection> getFeatureCollectionConsumer() {
        when(publisher.subscribe(Matchers.<Consumer<FeatureCollection>>anyObject())).thenReturn(d);
        presenter.onViewCreated();
        verify(publisher).subscribe(consumerArgumentCaptor.capture());
        return consumerArgumentCaptor.getValue();
    }
}