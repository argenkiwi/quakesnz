package nz.co.codebros.quakesnz.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Leandro on 06/04/2014.
 */
public class LatLngUtils {

    public static double findDistance(LatLng c1, LatLng c2) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(c2.latitude-c1.latitude);
        double dLng = Math.toRadians(c2.longitude-c1.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(c1.latitude)) * Math.cos(Math.toRadians(c2.latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return dist * meterConversion;
    }
}
