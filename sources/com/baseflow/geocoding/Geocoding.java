package com.baseflow.geocoding;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Geocoder$GeocodeListener;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
class Geocoding {
    private final Context androidContext;

    @Nullable
    private Locale locale;

    Geocoding(Context context) {
        this.androidContext = context;
    }

    private static Geocoder createGeocoder(Context context, @Nullable Locale locale) {
        return locale != null ? new Geocoder(context, locale) : new Geocoder(context);
    }

    private List<Address> deprecatedGetFromLocation(Geocoder geocoder, double d2, double d3) throws IOException {
        return geocoder.getFromLocation(d2, d3, 5);
    }

    private List<Address> deprecatedGetFromLocationName(Geocoder geocoder, String str) throws IOException {
        return geocoder.getFromLocationName(str, 5);
    }

    @RequiresApi(api = 33)
    private void getAddressesWithGeocodeListener(Geocoder geocoder, String str, int i2, final GeocodeListenerAdapter geocodeListenerAdapter) {
        geocoder.getFromLocationName(str, i2, new Geocoder$GeocodeListener() { // from class: com.baseflow.geocoding.Geocoding.1
            public void onError(@Nullable String str2) {
                geocodeListenerAdapter.onError(str2);
            }

            public void onGeocode(List<Address> list) {
                geocodeListenerAdapter.onGeocode(list);
            }
        });
    }

    @RequiresApi(api = 33)
    private void getLocationWithGeocodeListener(Geocoder geocoder, double d2, double d3, int i2, final GeocodeListenerAdapter geocodeListenerAdapter) {
        geocoder.getFromLocation(d2, d3, i2, new Geocoder$GeocodeListener() { // from class: com.baseflow.geocoding.Geocoding.2
            public void onError(@Nullable String str) {
                geocodeListenerAdapter.onError(str);
            }

            public void onGeocode(List<Address> list) {
                geocodeListenerAdapter.onGeocode(list);
            }
        });
    }

    boolean a() {
        return Geocoder.isPresent();
    }

    void b(String str, GeocodeListenerAdapter geocodeListenerAdapter) {
        Geocoder geocoderCreateGeocoder = createGeocoder(this.androidContext, this.locale);
        if (Build.VERSION.SDK_INT >= 33) {
            getAddressesWithGeocodeListener(geocoderCreateGeocoder, str, 5, geocodeListenerAdapter);
            return;
        }
        try {
            geocodeListenerAdapter.onGeocode(deprecatedGetFromLocationName(geocoderCreateGeocoder, str));
        } catch (IOException e2) {
            geocodeListenerAdapter.onError(e2.getMessage());
        }
    }

    void c(double d2, double d3, GeocodeListenerAdapter geocodeListenerAdapter) {
        Geocoder geocoderCreateGeocoder = createGeocoder(this.androidContext, this.locale);
        if (Build.VERSION.SDK_INT >= 33) {
            getLocationWithGeocodeListener(geocoderCreateGeocoder, d2, d3, 5, geocodeListenerAdapter);
            return;
        }
        try {
            geocodeListenerAdapter.onGeocode(deprecatedGetFromLocation(geocoderCreateGeocoder, d2, d3));
        } catch (IOException e2) {
            geocodeListenerAdapter.onError(e2.getMessage());
        }
    }

    void d(Locale locale) {
        this.locale = locale;
    }
}
