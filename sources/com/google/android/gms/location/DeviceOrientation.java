package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.identity.zzer;
import java.util.Arrays;
import org.checkerframework.dataflow.qual.Pure;

@SafeParcelable.Class(creator = "DeviceOrientationCreator")
@SafeParcelable.Reserved({2, 3})
/* loaded from: classes3.dex */
public class DeviceOrientation extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<DeviceOrientation> CREATOR = new zzm();

    @SafeParcelable.Field(getter = "getAttitude", id = 1)
    private final float[] zza;

    @SafeParcelable.Field(getter = "getHeadingDegrees", id = 4)
    private final float zzb;

    @SafeParcelable.Field(getter = "getHeadingErrorDegrees", id = 5)
    private final float zzc;

    @SafeParcelable.Field(getter = "getElapsedRealtimeNs", id = 6)
    private final long zzd;

    @SafeParcelable.Field(getter = "getFieldMask", id = 7)
    private final byte zze;

    @SafeParcelable.Field(getter = "getConservativeHeadingErrorVonMisesKappa", id = 8)
    private final float zzf;

    @SafeParcelable.Field(getter = "getConservativeHeadingErrorDegrees", id = 9)
    private final float zzg;

    DeviceOrientation(float[] fArr, float f2, float f3, long j2, byte b2, float f4, float f5) {
        zzj(fArr);
        zzer.zza(f2 >= 0.0f && f2 < 360.0f);
        zzer.zza(f3 >= 0.0f && f3 <= 180.0f);
        zzer.zza(f5 >= 0.0f && f5 <= 180.0f);
        zzer.zza(j2 >= 0);
        this.zza = fArr;
        this.zzb = f2;
        this.zzc = f3;
        this.zzf = f4;
        this.zzg = f5;
        this.zzd = j2;
        this.zze = (byte) (((byte) (((byte) (b2 | 16)) | 4)) | 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzj(float[] fArr) {
        zzer.zzb(fArr != null && fArr.length == 4, "Input attitude array should be of length 4.");
        zzer.zzb((Float.isNaN(fArr[0]) || Float.isNaN(fArr[1]) || Float.isNaN(fArr[2]) || Float.isNaN(fArr[3])) ? false : true, "Input attitude cannot contain NaNs.");
    }

    final /* synthetic */ float[] e() {
        return this.zza;
    }

    @Pure
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceOrientation)) {
            return false;
        }
        DeviceOrientation deviceOrientation = (DeviceOrientation) obj;
        return Float.compare(this.zzb, deviceOrientation.zzb) == 0 && Float.compare(this.zzc, deviceOrientation.zzc) == 0 && (zza() == deviceOrientation.zza() && (!zza() || Float.compare(this.zzf, deviceOrientation.zzf) == 0)) && (hasConservativeHeadingErrorDegrees() == deviceOrientation.hasConservativeHeadingErrorDegrees() && (!hasConservativeHeadingErrorDegrees() || Float.compare(getConservativeHeadingErrorDegrees(), deviceOrientation.getConservativeHeadingErrorDegrees()) == 0)) && this.zzd == deviceOrientation.zzd && Arrays.equals(this.zza, deviceOrientation.zza);
    }

    final /* synthetic */ float f() {
        return this.zzb;
    }

    final /* synthetic */ float g() {
        return this.zzc;
    }

    @NonNull
    @Pure
    public float[] getAttitude() {
        return (float[]) this.zza.clone();
    }

    @Pure
    public float getConservativeHeadingErrorDegrees() {
        return this.zzg;
    }

    @Pure
    public long getElapsedRealtimeNs() {
        return this.zzd;
    }

    @Pure
    public float getHeadingDegrees() {
        return this.zzb;
    }

    @Pure
    public float getHeadingErrorDegrees() {
        return this.zzc;
    }

    final /* synthetic */ long h() {
        return this.zzd;
    }

    @Pure
    public boolean hasConservativeHeadingErrorDegrees() {
        return (this.zze & 64) != 0;
    }

    @Pure
    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.zzb), Float.valueOf(this.zzc), Float.valueOf(this.zzg), Long.valueOf(this.zzd), this.zza, Byte.valueOf(this.zze));
    }

    final /* synthetic */ byte i() {
        return this.zze;
    }

    final /* synthetic */ float j() {
        return this.zzf;
    }

    final /* synthetic */ float k() {
        return this.zzg;
    }

    @NonNull
    @Pure
    public String toString() {
        StringBuilder sb = new StringBuilder("DeviceOrientation[");
        sb.append("attitude=");
        sb.append(Arrays.toString(this.zza));
        sb.append(", headingDegrees=");
        sb.append(this.zzb);
        sb.append(", headingErrorDegrees=");
        sb.append(this.zzc);
        if (hasConservativeHeadingErrorDegrees()) {
            sb.append(", conservativeHeadingErrorDegrees=");
            sb.append(this.zzg);
        }
        sb.append(", elapsedRealtimeNs=");
        sb.append(this.zzd);
        sb.append(']');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeFloatArray(parcel, 1, getAttitude(), false);
        SafeParcelWriter.writeFloat(parcel, 4, getHeadingDegrees());
        SafeParcelWriter.writeFloat(parcel, 5, getHeadingErrorDegrees());
        SafeParcelWriter.writeLong(parcel, 6, getElapsedRealtimeNs());
        SafeParcelWriter.writeByte(parcel, 7, this.zze);
        SafeParcelWriter.writeFloat(parcel, 8, this.zzf);
        SafeParcelWriter.writeFloat(parcel, 9, getConservativeHeadingErrorDegrees());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Pure
    public final boolean zza() {
        return (this.zze & 32) != 0;
    }

    public static final class Builder {
        private final float[] zza;
        private float zzb;
        private float zzc;
        private long zzd;
        private byte zze;
        private float zzf;
        private float zzg;

        public Builder(@NonNull DeviceOrientation deviceOrientation) {
            this.zze = (byte) 0;
            DeviceOrientation.zzj(deviceOrientation.e());
            this.zza = Arrays.copyOf(deviceOrientation.e(), deviceOrientation.e().length);
            setHeadingDegrees(deviceOrientation.f());
            setHeadingErrorDegrees(deviceOrientation.g());
            setConservativeHeadingErrorDegrees(deviceOrientation.k());
            setElapsedRealtimeNs(deviceOrientation.h());
            this.zzf = deviceOrientation.j();
            this.zze = deviceOrientation.i();
        }

        @NonNull
        public DeviceOrientation build() {
            return new DeviceOrientation(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg);
        }

        @NonNull
        public Builder clearConservativeHeadingErrorDegrees() {
            this.zzg = 180.0f;
            int i2 = this.zze & (-65);
            this.zzf = 0.0f;
            this.zze = (byte) (((byte) i2) & (-33));
            return this;
        }

        @NonNull
        public Builder setAttitude(@NonNull float[] fArr) {
            DeviceOrientation.zzj(fArr);
            System.arraycopy(fArr, 0, this.zza, 0, fArr.length);
            return this;
        }

        @NonNull
        public Builder setConservativeHeadingErrorDegrees(float f2) {
            boolean z2 = false;
            if (f2 >= 0.0f && f2 <= 180.0f) {
                z2 = true;
            }
            zzer.zzb(z2, "conservativeHeadingErrorDegrees should be between 0 and 180.");
            this.zzg = f2;
            this.zze = (byte) (this.zze | 64);
            Parcelable.Creator<DeviceOrientation> creator = DeviceOrientation.CREATOR;
            this.zzf = f2 < 180.0f ? (float) (2.0d / (1.0d - Math.cos(Math.toRadians(f2)))) : 0.0f;
            this.zze = (byte) (this.zze | 32);
            return this;
        }

        @NonNull
        public Builder setElapsedRealtimeNs(long j2) {
            zzer.zzb(j2 >= 0, "elapsedRealtimeNs should be greater than or equal to 0.");
            this.zzd = j2;
            return this;
        }

        @NonNull
        public Builder setHeadingDegrees(float f2) {
            boolean z2 = false;
            if (f2 >= 0.0f && f2 < 360.0f) {
                z2 = true;
            }
            zzer.zzb(z2, "headingDegrees should be greater than or equal to 0 and less than 360.");
            this.zzb = f2;
            return this;
        }

        @NonNull
        public Builder setHeadingErrorDegrees(float f2) {
            boolean z2 = false;
            if (f2 >= 0.0f && f2 <= 180.0f) {
                z2 = true;
            }
            zzer.zzb(z2, "headingErrorDegrees should be between 0 and 180.");
            this.zzc = f2;
            return this;
        }

        public Builder(@NonNull float[] fArr, float f2, float f3, long j2) {
            this.zze = (byte) 0;
            DeviceOrientation.zzj(fArr);
            this.zza = Arrays.copyOf(fArr, fArr.length);
            setHeadingDegrees(f2);
            setHeadingErrorDegrees(f3);
            setElapsedRealtimeNs(j2);
            this.zzf = 0.0f;
            this.zzg = 180.0f;
            this.zze = (byte) 0;
        }
    }
}
