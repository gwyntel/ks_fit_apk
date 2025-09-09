package com.baseflow.geolocator.location;

import com.baseflow.geolocator.errors.ErrorCodes;

/* loaded from: classes3.dex */
public interface LocationServiceListener {
    void onLocationServiceError(ErrorCodes errorCodes);

    void onLocationServiceResult(boolean z2);
}
