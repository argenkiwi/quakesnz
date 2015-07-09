package nz.co.codebros.quakesnz.interactor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import nz.co.codebros.quakesnz.service.GeonetIntentService;

/**
 * Created by leandro on 9/07/15.
 */
public class LoadQuakesInteractorImpl implements LoadQuakesInteractor {

    private static final String TAG = LoadQuakesInteractorImpl.class.getSimpleName();

    private final Context mContext;

    public LoadQuakesInteractorImpl(Context context) {
        mContext = context;
    }

    @Override
    public void loadQuakes(int scope, final Listener listener) {

        BroadcastReceiver receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case GeonetIntentService.ACTION_DOWNLOAD_SUCCESS:
                        Log.d(TAG, "Quakes loaded successfully.");
                        listener.onQuakesLoaded();
                        break;
                    case GeonetIntentService.ACTION_DOWNLOAD_FAILURE:
                        // FIXME: Notify error.
                        Log.w(TAG, "Quakes failed to load.");
                        listener.onQuakesLoaded();
                        break;
                }

                LocalBroadcastManager.getInstance(mContext).unregisterReceiver(this);
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(GeonetIntentService.ACTION_DOWNLOAD_SUCCESS);
        filter.addAction(GeonetIntentService.ACTION_DOWNLOAD_FAILURE);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, filter);

        final Intent intent = new Intent(mContext, GeonetIntentService.class);
        intent.putExtra(GeonetIntentService.EXTRA_SCOPE, scope);
        mContext.startService(intent);
    }
}
