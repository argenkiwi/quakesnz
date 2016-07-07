package nz.co.codebros.quakesnz.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import nz.co.codebros.quakesnz.R;

/**
 * Created by leandro on 12/04/16.
 */
public class QuakesUtils {

    public static int getColor(Context context, int mmi) {
        switch (mmi) {
            case 12:
            case 11:
            case 10:
            case 9:
            case 8:
            case 7:
                return ContextCompat.getColor(context, R.color.severe);
            case 6:
                return ContextCompat.getColor(context, R.color.strong);
            case 5:
                return ContextCompat.getColor(context, R.color.moderate);
            case 4:
                return ContextCompat.getColor(context, R.color.light);
            case 3:
                return ContextCompat.getColor(context, R.color.weak);
            default:
                return ContextCompat.getColor(context, R.color.unnoticeable);
        }
    }

    public static String getIntensity(Context context, int mmi) {
        switch (mmi) {
            case 12:
            case 11:
            case 10:
            case 9:
            case 8:
            case 7:
                return context.getString(R.string.severe);
            case 6:
                return context.getString(R.string.strong);
            case 5:
                return context.getString(R.string.moderate);
            case 4:
                return context.getString(R.string.light);
            case 3:
                return context.getString(R.string.weak);
            default:
                return context.getString(R.string.unnoticeable);
        }
    }
}
