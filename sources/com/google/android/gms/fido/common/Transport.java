package com.google.android.gms.fido.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.media3.exoplayer.rtsp.RtspHeaders;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.fido.common.Transport;
import com.umeng.commonsdk.framework.UMModuleRegister;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes3.dex */
public enum Transport implements ReflectedParcelable {
    BLUETOOTH_CLASSIC("bt"),
    BLUETOOTH_LOW_ENERGY("ble"),
    NFC("nfc"),
    USB("usb"),
    INTERNAL(UMModuleRegister.INNER),
    HYBRID("cable");


    @NonNull
    public static final Parcelable.Creator<Transport> CREATOR = new Parcelable.Creator() { // from class: com.google.android.gms.fido.common.zza
        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            try {
                return Transport.fromString(parcel.readString());
            } catch (Transport.UnsupportedTransportException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ Object[] newArray(int i2) {
            return new Transport[i2];
        }
    };
    private final String zzb;

    public static class UnsupportedTransportException extends Exception {
        public UnsupportedTransportException(@NonNull String str) {
            super(str);
        }
    }

    Transport(String str) {
        this.zzb = str;
    }

    @NonNull
    public static Transport fromString(@NonNull String str) throws UnsupportedTransportException {
        for (Transport transport : values()) {
            if (str.equals(transport.zzb)) {
                return transport;
            }
        }
        if (str.equals("hybrid")) {
            return HYBRID;
        }
        throw new UnsupportedTransportException(String.format("Transport %s not supported", str));
    }

    @NonNull
    public static List<Transport> parseTransports(@NonNull JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        HashSet hashSet = new HashSet(jSONArray.length());
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            String string = jSONArray.getString(i2);
            if (string != null && !string.isEmpty()) {
                try {
                    hashSet.add(fromString(string));
                } catch (UnsupportedTransportException unused) {
                    Log.w(RtspHeaders.TRANSPORT, "Ignoring unrecognized transport ".concat(string));
                }
            }
        }
        return new ArrayList(hashSet);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // java.lang.Enum
    @NonNull
    public String toString() {
        return this.zzb;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeString(this.zzb);
    }
}
