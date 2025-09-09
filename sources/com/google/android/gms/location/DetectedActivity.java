package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Comparator;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

@SafeParcelable.Class(creator = "DetectedActivityCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class DetectedActivity extends AbstractSafeParcelable {
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;

    /* renamed from: a, reason: collision with root package name */
    final int f13241a;

    /* renamed from: b, reason: collision with root package name */
    final int f13242b;

    @NonNull
    public static final Comparator zza = new zzk();

    @NonNull
    public static final Parcelable.Creator<DetectedActivity> CREATOR = new zzl();

    @SafeParcelable.Constructor
    public DetectedActivity(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) int i3) {
        this.f13241a = i2;
        this.f13242b = i3;
    }

    @ShowFirstParty
    public final boolean equals(@Nullable Object obj) {
        if (obj instanceof DetectedActivity) {
            DetectedActivity detectedActivity = (DetectedActivity) obj;
            if (this.f13241a == detectedActivity.f13241a && this.f13242b == detectedActivity.f13242b) {
                return true;
            }
        }
        return false;
    }

    public int getConfidence() {
        return this.f13242b;
    }

    public int getType() {
        int i2 = this.f13241a;
        if (i2 > 22 || i2 < 0) {
            return 4;
        }
        return i2;
    }

    @ShowFirstParty
    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.f13241a), Integer.valueOf(this.f13242b));
    }

    @NonNull
    public String toString() {
        int type = getType();
        String string = type != 0 ? type != 1 ? type != 2 ? type != 3 ? type != 4 ? type != 5 ? type != 7 ? type != 8 ? type != 16 ? type != 17 ? Integer.toString(type) : "IN_RAIL_VEHICLE" : "IN_ROAD_VEHICLE" : DebugCoroutineInfoImplKt.RUNNING : "WALKING" : "TILTING" : "UNKNOWN" : "STILL" : "ON_FOOT" : "ON_BICYCLE" : "IN_VEHICLE";
        int i2 = this.f13242b;
        StringBuilder sb = new StringBuilder(String.valueOf(string).length() + 36 + String.valueOf(i2).length() + 1);
        sb.append("DetectedActivity [type=");
        sb.append(string);
        sb.append(", confidence=");
        sb.append(i2);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        Preconditions.checkNotNull(parcel);
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f13241a);
        SafeParcelWriter.writeInt(parcel, 2, this.f13242b);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
