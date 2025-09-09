package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Patterns;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdkWithMembers;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@ShowFirstParty
@KeepForSdkWithMembers
@SafeParcelable.Class(creator = "ProxyRequestCreator")
/* loaded from: classes3.dex */
public class ProxyRequest extends AbstractSafeParcelable {
    public static final int VERSION_CODE = 2;

    /* renamed from: a, reason: collision with root package name */
    final int f12640a;

    /* renamed from: b, reason: collision with root package name */
    final Bundle f12641b;

    @NonNull
    @SafeParcelable.Field(id = 4)
    public final byte[] body;

    @SafeParcelable.Field(id = 2)
    public final int httpMethod;

    @SafeParcelable.Field(id = 3)
    public final long timeoutMillis;

    @NonNull
    @SafeParcelable.Field(id = 1)
    public final String url;

    @NonNull
    public static final Parcelable.Creator<ProxyRequest> CREATOR = new zza();
    public static final int HTTP_METHOD_GET = 0;
    public static final int HTTP_METHOD_POST = 1;
    public static final int HTTP_METHOD_PUT = 2;
    public static final int HTTP_METHOD_DELETE = 3;
    public static final int HTTP_METHOD_HEAD = 4;
    public static final int HTTP_METHOD_OPTIONS = 5;
    public static final int HTTP_METHOD_TRACE = 6;
    public static final int HTTP_METHOD_PATCH = 7;
    public static final int LAST_CODE = 7;

    @ShowFirstParty
    @KeepForSdkWithMembers
    public static class Builder {
        private final String zza;
        private int zzb = ProxyRequest.HTTP_METHOD_GET;
        private long zzc = 3000;
        private byte[] zzd = new byte[0];
        private final Bundle zze = new Bundle();

        public Builder(@NonNull String str) {
            Preconditions.checkNotEmpty(str);
            if (Patterns.WEB_URL.matcher(str).matches()) {
                this.zza = str;
                return;
            }
            throw new IllegalArgumentException("The supplied url [ " + str + "] is not match Patterns.WEB_URL!");
        }

        @NonNull
        public ProxyRequest build() {
            if (this.zzd == null) {
                this.zzd = new byte[0];
            }
            return new ProxyRequest(2, this.zza, this.zzb, this.zzc, this.zzd, this.zze);
        }

        @NonNull
        public Builder putHeader(@NonNull String str, @NonNull String str2) {
            Preconditions.checkNotEmpty(str, "Header name cannot be null or empty!");
            Bundle bundle = this.zze;
            if (str2 == null) {
                str2 = "";
            }
            bundle.putString(str, str2);
            return this;
        }

        @NonNull
        public Builder setBody(@NonNull byte[] bArr) {
            this.zzd = bArr;
            return this;
        }

        @NonNull
        public Builder setHttpMethod(int i2) {
            boolean z2 = false;
            if (i2 >= 0 && i2 <= ProxyRequest.LAST_CODE) {
                z2 = true;
            }
            Preconditions.checkArgument(z2, "Unrecognized http method code.");
            this.zzb = i2;
            return this;
        }

        @NonNull
        public Builder setTimeoutMillis(long j2) {
            Preconditions.checkArgument(j2 >= 0, "The specified timeout must be non-negative.");
            this.zzc = j2;
            return this;
        }
    }

    ProxyRequest(int i2, String str, int i3, long j2, byte[] bArr, Bundle bundle) {
        this.f12640a = i2;
        this.url = str;
        this.httpMethod = i3;
        this.timeoutMillis = j2;
        this.body = bArr;
        this.f12641b = bundle;
    }

    @NonNull
    public Map<String, String> getHeaderMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.f12641b.size());
        for (String str : this.f12641b.keySet()) {
            String string = this.f12641b.getString(str);
            if (string == null) {
                string = "";
            }
            linkedHashMap.put(str, string);
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    @NonNull
    public String toString() {
        return "ProxyRequest[ url: " + this.url + ", method: " + this.httpMethod + " ]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.url, false);
        SafeParcelWriter.writeInt(parcel, 2, this.httpMethod);
        SafeParcelWriter.writeLong(parcel, 3, this.timeoutMillis);
        SafeParcelWriter.writeByteArray(parcel, 4, this.body, false);
        SafeParcelWriter.writeBundle(parcel, 5, this.f12641b, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.f12640a);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
