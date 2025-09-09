package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MimeTypes;
import com.aliyun.alink.linksdk.tools.ut.AUserTrack;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.internal.fitness.zzfv;
import com.google.android.gms.internal.fitness.zzfw;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.concurrent.TimeUnit;

@SafeParcelable.Class(creator = "SessionCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class Session extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<Session> CREATOR = new zzac();

    @NonNull
    public static final String EXTRA_SESSION = "vnd.google.fitness.session";

    @NonNull
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.session/";

    @SafeParcelable.Field(getter = "getStartTimeMillis", id = 1)
    private final long zza;

    @SafeParcelable.Field(getter = "getEndTimeMillis", id = 2)
    private final long zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getName", id = 3)
    private final String zzc;

    @SafeParcelable.Field(getter = "getIdentifier", id = 4)
    private final String zzd;

    @SafeParcelable.Field(getter = "getDescription", id = 5)
    private final String zze;

    @SafeParcelable.Field(getter = "getActivityType", id = 7)
    private final int zzf;

    @SafeParcelable.Field(getter = "getApplication", id = 8)
    private final zzb zzg;

    @Nullable
    @SafeParcelable.Field(getter = "getActiveTimeMillis", id = 9)
    private final Long zzh;

    public static class Builder {
        private String zzd;

        @Nullable
        private Long zzg;
        private long zza = 0;
        private long zzb = 0;

        @Nullable
        private String zzc = null;
        private String zze = "";
        private int zzf = 4;

        @NonNull
        public Session build() {
            Preconditions.checkState(this.zza > 0, "Start time should be specified.");
            long j2 = this.zzb;
            Preconditions.checkState(j2 == 0 || j2 > this.zza, "End time should be later than start time.");
            if (this.zzd == null) {
                String str = this.zzc;
                if (str == null) {
                    str = "";
                }
                this.zzd = str + this.zza;
            }
            return new Session(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, null, this.zzg);
        }

        @NonNull
        public Builder setActiveTime(long j2, @NonNull TimeUnit timeUnit) {
            this.zzg = Long.valueOf(timeUnit.toMillis(j2));
            return this;
        }

        @NonNull
        public Builder setActivity(@NonNull String str) {
            int iZza = zzfv.zza(str);
            zzfw zzfwVarZza = zzfw.zza(iZza, zzfw.UNKNOWN);
            Preconditions.checkArgument(!(zzfwVarZza.zzb() && !zzfwVarZza.equals(zzfw.SLEEP)), "Unsupported session activity type %s.", Integer.valueOf(iZza));
            this.zzf = iZza;
            return this;
        }

        @NonNull
        public Builder setDescription(@NonNull String str) {
            Preconditions.checkArgument(str.length() <= 1000, "Session description cannot exceed %d characters", 1000);
            this.zze = str;
            return this;
        }

        @NonNull
        public Builder setEndTime(long j2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkState(j2 >= 0, "End time should be positive.");
            this.zzb = timeUnit.toMillis(j2);
            return this;
        }

        @NonNull
        public Builder setIdentifier(@NonNull String str) {
            boolean z2 = false;
            if (str != null && TextUtils.getTrimmedLength(str) > 0) {
                z2 = true;
            }
            Preconditions.checkArgument(z2);
            this.zzd = str;
            return this;
        }

        @NonNull
        public Builder setName(@NonNull String str) {
            Preconditions.checkArgument(str.length() <= 100, "Session name cannot exceed %d characters", 100);
            this.zzc = str;
            return this;
        }

        @NonNull
        public Builder setStartTime(long j2, @NonNull TimeUnit timeUnit) {
            Preconditions.checkState(j2 > 0, "Start time should be positive.");
            this.zza = timeUnit.toMillis(j2);
            return this;
        }
    }

    @SafeParcelable.Constructor
    public Session(@SafeParcelable.Param(id = 1) long j2, @SafeParcelable.Param(id = 2) long j3, @Nullable @SafeParcelable.Param(id = 3) String str, @SafeParcelable.Param(id = 4) String str2, @SafeParcelable.Param(id = 5) String str3, @SafeParcelable.Param(id = 7) int i2, @SafeParcelable.Param(id = 8) zzb zzbVar, @Nullable @SafeParcelable.Param(id = 9) Long l2) {
        this.zza = j2;
        this.zzb = j3;
        this.zzc = str;
        this.zzd = str2;
        this.zze = str3;
        this.zzf = i2;
        this.zzg = zzbVar;
        this.zzh = l2;
    }

    @Nullable
    public static Session extract(@NonNull Intent intent) {
        if (intent == null) {
            return null;
        }
        return (Session) SafeParcelableSerializer.deserializeFromIntentExtra(intent, EXTRA_SESSION, CREATOR);
    }

    @NonNull
    public static String getMimeType(@NonNull String str) {
        return MIME_TYPE_PREFIX.concat(String.valueOf(str));
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Session)) {
            return false;
        }
        Session session = (Session) obj;
        return this.zza == session.zza && this.zzb == session.zzb && Objects.equal(this.zzc, session.zzc) && Objects.equal(this.zzd, session.zzd) && Objects.equal(this.zze, session.zze) && Objects.equal(this.zzg, session.zzg) && this.zzf == session.zzf;
    }

    public long getActiveTime(@NonNull TimeUnit timeUnit) {
        Long l2 = this.zzh;
        if (l2 != null) {
            return timeUnit.convert(l2.longValue(), TimeUnit.MILLISECONDS);
        }
        throw new IllegalStateException("Active time is not set");
    }

    @NonNull
    public String getActivity() {
        return zzfv.zzb(this.zzf);
    }

    @Nullable
    public String getAppPackageName() {
        zzb zzbVar = this.zzg;
        if (zzbVar == null) {
            return null;
        }
        return zzbVar.zzb();
    }

    @NonNull
    public String getDescription() {
        return this.zze;
    }

    public long getEndTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zzb, TimeUnit.MILLISECONDS);
    }

    @NonNull
    public String getIdentifier() {
        return this.zzd;
    }

    @Nullable
    public String getName() {
        return this.zzc;
    }

    public long getStartTime(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.zza, TimeUnit.MILLISECONDS);
    }

    public boolean hasActiveTime() {
        return this.zzh != null;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zza), Long.valueOf(this.zzb), this.zzd);
    }

    public boolean isOngoing() {
        return this.zzb == 0;
    }

    @NonNull
    public String toString() {
        return Objects.toStringHelper(this).add("startTime", Long.valueOf(this.zza)).add(AUserTrack.UTKEY_END_TIME, Long.valueOf(this.zzb)).add("name", this.zzc).add("identifier", this.zzd).add("description", this.zze).add(PushConstants.INTENT_ACTIVITY_NAME, Integer.valueOf(this.zzf)).add(MimeTypes.BASE_TYPE_APPLICATION, this.zzg).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, this.zza);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeString(parcel, 3, getName(), false);
        SafeParcelWriter.writeString(parcel, 4, getIdentifier(), false);
        SafeParcelWriter.writeString(parcel, 5, getDescription(), false);
        SafeParcelWriter.writeInt(parcel, 7, this.zzf);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzg, i2, false);
        SafeParcelWriter.writeLongObject(parcel, 9, this.zzh, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
