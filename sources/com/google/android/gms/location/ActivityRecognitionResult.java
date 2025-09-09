package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "ActivityRecognitionResultCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class ActivityRecognitionResult extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<ActivityRecognitionResult> CREATOR = new zzd();

    /* renamed from: a, reason: collision with root package name */
    List f13236a;

    /* renamed from: b, reason: collision with root package name */
    long f13237b;

    /* renamed from: c, reason: collision with root package name */
    long f13238c;

    /* renamed from: d, reason: collision with root package name */
    int f13239d;

    /* renamed from: e, reason: collision with root package name */
    Bundle f13240e;

    @VisibleForTesting
    public ActivityRecognitionResult(@NonNull DetectedActivity detectedActivity, long j2, long j3) {
        this(Collections.singletonList(detectedActivity), j2, j3, 0, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0007  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.location.ActivityRecognitionResult extractResult(@androidx.annotation.NonNull android.content.Intent r3) {
        /*
            boolean r0 = hasResult(r3)
            r1 = 0
            if (r0 != 0) goto L9
        L7:
            r0 = r1
            goto L2b
        L9:
            android.os.Bundle r0 = r3.getExtras()
            if (r0 != 0) goto L10
            goto L7
        L10:
            java.lang.String r2 = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT"
            java.lang.Object r0 = r0.get(r2)
            boolean r2 = r0 instanceof byte[]
            if (r2 == 0) goto L25
            byte[] r0 = (byte[]) r0
            android.os.Parcelable$Creator<com.google.android.gms.location.ActivityRecognitionResult> r2 = com.google.android.gms.location.ActivityRecognitionResult.CREATOR
            com.google.android.gms.common.internal.safeparcel.SafeParcelable r0 = com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer.deserializeFromBytes(r0, r2)
            com.google.android.gms.location.ActivityRecognitionResult r0 = (com.google.android.gms.location.ActivityRecognitionResult) r0
            goto L2b
        L25:
            boolean r2 = r0 instanceof com.google.android.gms.location.ActivityRecognitionResult
            if (r2 == 0) goto L7
            com.google.android.gms.location.ActivityRecognitionResult r0 = (com.google.android.gms.location.ActivityRecognitionResult) r0
        L2b:
            if (r0 == 0) goto L2e
            return r0
        L2e:
            java.util.List r3 = zza(r3)
            if (r3 == 0) goto L48
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L3b
            goto L48
        L3b:
            int r0 = r3.size()
            int r0 = r0 + (-1)
            java.lang.Object r3 = r3.get(r0)
            com.google.android.gms.location.ActivityRecognitionResult r3 = (com.google.android.gms.location.ActivityRecognitionResult) r3
            return r3
        L48:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.location.ActivityRecognitionResult.extractResult(android.content.Intent):com.google.android.gms.location.ActivityRecognitionResult");
    }

    public static boolean hasResult(@Nullable Intent intent) {
        if (intent == null) {
            return false;
        }
        if (intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT")) {
            return true;
        }
        List listZza = zza(intent);
        return (listZza == null || listZza.isEmpty()) ? false : true;
    }

    @Nullable
    public static List zza(@NonNull Intent intent) {
        if (intent != null && intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST")) {
            return SafeParcelableSerializer.deserializeIterableFromIntentExtra(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST", CREATOR);
        }
        return null;
    }

    private static boolean zzb(@Nullable Bundle bundle, @Nullable Bundle bundle2) {
        int length;
        if (bundle == null) {
            return bundle2 == null;
        }
        if (bundle2 == null || bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (!bundle2.containsKey(str)) {
                return false;
            }
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if (obj == null) {
                if (obj2 != null) {
                    return false;
                }
            } else if (obj instanceof Bundle) {
                if (!zzb(bundle.getBundle(str), bundle2.getBundle(str))) {
                    return false;
                }
            } else {
                if (obj.getClass().isArray()) {
                    if (obj2 != null && obj2.getClass().isArray() && (length = Array.getLength(obj)) == Array.getLength(obj2)) {
                        for (int i2 = 0; i2 < length; i2++) {
                            if (Objects.equal(Array.get(obj, i2), Array.get(obj2, i2))) {
                            }
                        }
                    }
                    return false;
                }
                if (!obj.equals(obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    @ShowFirstParty
    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ActivityRecognitionResult activityRecognitionResult = (ActivityRecognitionResult) obj;
            if (this.f13237b == activityRecognitionResult.f13237b && this.f13238c == activityRecognitionResult.f13238c && this.f13239d == activityRecognitionResult.f13239d && Objects.equal(this.f13236a, activityRecognitionResult.f13236a) && zzb(this.f13240e, activityRecognitionResult.f13240e)) {
                return true;
            }
        }
        return false;
    }

    public int getActivityConfidence(int i2) {
        for (DetectedActivity detectedActivity : this.f13236a) {
            if (detectedActivity.getType() == i2) {
                return detectedActivity.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.f13238c;
    }

    @NonNull
    public DetectedActivity getMostProbableActivity() {
        return (DetectedActivity) this.f13236a.get(0);
    }

    @NonNull
    public List<DetectedActivity> getProbableActivities() {
        return this.f13236a;
    }

    public long getTime() {
        return this.f13237b;
    }

    @ShowFirstParty
    public final int hashCode() {
        return Objects.hashCode(Long.valueOf(this.f13237b), Long.valueOf(this.f13238c), Integer.valueOf(this.f13239d), this.f13236a, this.f13240e);
    }

    @NonNull
    public String toString() {
        String strValueOf = String.valueOf(this.f13236a);
        long j2 = this.f13237b;
        long j3 = this.f13238c;
        int length = strValueOf.length();
        StringBuilder sb = new StringBuilder(length + 59 + String.valueOf(j2).length() + 24 + String.valueOf(j3).length() + 1);
        sb.append("ActivityRecognitionResult [probableActivities=");
        sb.append(strValueOf);
        sb.append(", timeMillis=");
        sb.append(j2);
        sb.append(", elapsedRealtimeMillis=");
        sb.append(j3);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.f13236a, false);
        SafeParcelWriter.writeLong(parcel, 2, this.f13237b);
        SafeParcelWriter.writeLong(parcel, 3, this.f13238c);
        SafeParcelWriter.writeInt(parcel, 4, this.f13239d);
        SafeParcelWriter.writeBundle(parcel, 5, this.f13240e, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public ActivityRecognitionResult(@NonNull List<DetectedActivity> list, long j2, long j3) {
        this(list, j2, j3, 0, null);
    }

    @ShowFirstParty
    @SafeParcelable.Constructor
    public ActivityRecognitionResult(@NonNull @SafeParcelable.Param(id = 1) List list, @SafeParcelable.Param(id = 2) long j2, @SafeParcelable.Param(id = 3) long j3, @SafeParcelable.Param(id = 4) int i2, @Nullable @SafeParcelable.Param(id = 5) Bundle bundle) {
        Preconditions.checkArgument((list == null || list.isEmpty()) ? false : true, "Must have at least 1 detected activity");
        Preconditions.checkArgument(j2 > 0 && j3 > 0, "Must set times");
        this.f13236a = list;
        this.f13237b = j2;
        this.f13238c = j3;
        this.f13239d = i2;
        this.f13240e = bundle;
    }
}
