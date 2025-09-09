package com.baseflow.geocoding;

import android.location.Address;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.baseflow.geocoding.utils.AddressMapper;
import com.baseflow.geocoding.utils.LocaleConverter;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
final class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {
    private static final String TAG = "MethodCallHandlerImpl";

    @Nullable
    private MethodChannel channel;
    private final Geocoding geocoding;

    MethodCallHandlerImpl(Geocoding geocoding) {
        this.geocoding = geocoding;
    }

    private void onIsPresent(MethodCall methodCall, MethodChannel.Result result) {
        result.success(Boolean.valueOf(this.geocoding.a()));
    }

    private void onLocationFromAddress(MethodCall methodCall, final MethodChannel.Result result) {
        final String str = (String) methodCall.argument("address");
        if (str == null || str.isEmpty()) {
            result.error("ARGUMENT_ERROR", "Supply a valid value for the 'address' parameter.", null);
        }
        this.geocoding.b(str, new GeocodeListenerAdapter() { // from class: com.baseflow.geocoding.MethodCallHandlerImpl.1
            @Override // com.baseflow.geocoding.GeocodeListenerAdapter
            public void onError(String str2) {
                result.error("IO_ERROR", String.format(str2, new Object[0]), null);
            }

            @Override // com.baseflow.geocoding.GeocodeListenerAdapter
            public void onGeocode(List<Address> list) {
                if (list == null || list.size() <= 0) {
                    result.error("NOT_FOUND", String.format("No coordinates found for '%s'", str), null);
                } else {
                    result.success(AddressMapper.toLocationHashMapList(list));
                }
            }
        });
    }

    private void onPlacemarkFromAddress(MethodCall methodCall, final MethodChannel.Result result) {
        final String str = (String) methodCall.argument("address");
        if (str == null || str.isEmpty()) {
            result.error("ARGUMENT_ERROR", "Supply a valid value for the 'address' parameter.", null);
        }
        this.geocoding.b(str, new GeocodeListenerAdapter() { // from class: com.baseflow.geocoding.MethodCallHandlerImpl.2
            @Override // com.baseflow.geocoding.GeocodeListenerAdapter
            public void onError(String str2) {
                result.error("IO_ERROR", String.format(str2, new Object[0]), null);
            }

            @Override // com.baseflow.geocoding.GeocodeListenerAdapter
            public void onGeocode(List<Address> list) {
                if (list == null || list.size() <= 0) {
                    result.error("NOT_FOUND", String.format("No coordinates found for '%s'", str), null);
                } else {
                    result.success(AddressMapper.toAddressHashMapList(list));
                }
            }
        });
    }

    private void onPlacemarkFromCoordinates(MethodCall methodCall, final MethodChannel.Result result) {
        final double dDoubleValue = ((Double) methodCall.argument("latitude")).doubleValue();
        final double dDoubleValue2 = ((Double) methodCall.argument("longitude")).doubleValue();
        this.geocoding.c(dDoubleValue, dDoubleValue2, new GeocodeListenerAdapter() { // from class: com.baseflow.geocoding.MethodCallHandlerImpl.3
            @Override // com.baseflow.geocoding.GeocodeListenerAdapter
            public void onError(String str) {
                result.error("IO_ERROR", String.format(str, new Object[0]), null);
            }

            @Override // com.baseflow.geocoding.GeocodeListenerAdapter
            public void onGeocode(List<Address> list) {
                if (list == null || list.size() <= 0) {
                    result.error("NOT_FOUND", String.format(Locale.ENGLISH, "No address information found for supplied coordinates (latitude: %f, longitude: %f).", Double.valueOf(dDoubleValue), Double.valueOf(dDoubleValue2)), null);
                } else {
                    result.success(AddressMapper.toAddressHashMapList(list));
                }
            }
        });
    }

    private void setLocaleIdentifier(MethodCall methodCall, MethodChannel.Result result) {
        this.geocoding.d(LocaleConverter.fromLanguageTag((String) methodCall.argument("localeIdentifier")));
        result.success(Boolean.TRUE);
    }

    void a(BinaryMessenger binaryMessenger) {
        if (this.channel != null) {
            Log.wtf(TAG, "Setting a method call handler before the last was disposed.");
            b();
        }
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "flutter.baseflow.com/geocoding", StandardMethodCodec.INSTANCE, binaryMessenger.makeBackgroundTaskQueue());
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    void b() {
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Log.d(TAG, "Tried to stop listening when no MethodChannel had been initialized.");
        } else {
            methodChannel.setMethodCallHandler(null);
            this.channel = null;
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "placemarkFromCoordinates":
                onPlacemarkFromCoordinates(methodCall, result);
                break;
            case "isPresent":
                onIsPresent(methodCall, result);
                break;
            case "locationFromAddress":
                onLocationFromAddress(methodCall, result);
                break;
            case "placemarkFromAddress":
                onPlacemarkFromAddress(methodCall, result);
                break;
            case "setLocaleIdentifier":
                setLocaleIdentifier(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }
}
