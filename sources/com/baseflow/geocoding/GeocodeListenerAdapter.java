package com.baseflow.geocoding;

import android.location.Address;
import androidx.annotation.Nullable;
import java.util.List;

/* loaded from: classes3.dex */
public interface GeocodeListenerAdapter {
    void onError(@Nullable String str);

    void onGeocode(@Nullable List<Address> list);
}
