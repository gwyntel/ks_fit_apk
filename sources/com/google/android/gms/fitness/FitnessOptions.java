package com.google.android.gms.fitness;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public final class FitnessOptions implements GoogleSignInOptionsExtension {
    public static final int ACCESS_READ = 0;
    public static final int ACCESS_WRITE = 1;
    private final Set zza;

    public static final class Builder {
        private final Set zza = new HashSet();

        private Builder() {
        }

        @NonNull
        public Builder accessActivitySessions(int i2) {
            if (i2 == 0) {
                this.zza.add(new Scope("https://www.googleapis.com/auth/fitness.activity.read"));
            } else {
                if (i2 != 1) {
                    throw new IllegalArgumentException("valid access types are FitnessOptions.ACCESS_READ or FitnessOptions.ACCESS_WRITE");
                }
                this.zza.add(new Scope("https://www.googleapis.com/auth/fitness.activity.write"));
            }
            return this;
        }

        @NonNull
        public Builder accessSleepSessions(int i2) {
            boolean z2;
            if (i2 == 0) {
                z2 = true;
            } else if (i2 == 1) {
                i2 = 1;
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "valid access types are FitnessOptions.ACCESS_READ or FitnessOptions.ACCESS_WRITE");
            if (i2 == 0) {
                this.zza.add(new Scope("https://www.googleapis.com/auth/fitness.sleep.read"));
            } else if (i2 == 1) {
                this.zza.add(new Scope("https://www.googleapis.com/auth/fitness.sleep.write"));
            }
            return this;
        }

        @NonNull
        public Builder addDataType(@NonNull DataType dataType) {
            addDataType(dataType, 0);
            return this;
        }

        @NonNull
        public FitnessOptions build() {
            return new FitnessOptions(this, null);
        }

        /* synthetic */ Builder(zzg zzgVar) {
        }

        @NonNull
        public Builder addDataType(@NonNull DataType dataType, int i2) {
            boolean z2;
            if (i2 == 0) {
                z2 = true;
            } else if (i2 == 1) {
                i2 = 1;
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "valid access types are FitnessOptions.ACCESS_READ or FitnessOptions.ACCESS_WRITE");
            String strZza = dataType.zza();
            String strZzb = dataType.zzb();
            if (i2 == 0) {
                if (strZza != null) {
                    this.zza.add(new Scope(strZza));
                }
            } else if (i2 == 1 && strZzb != null) {
                this.zza.add(new Scope(strZzb));
            }
            return this;
        }
    }

    /* synthetic */ FitnessOptions(Builder builder, zzh zzhVar) {
        this.zza = builder.zza;
    }

    @NonNull
    public static Builder builder() {
        return new Builder(null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FitnessOptions) {
            return this.zza.equals(((FitnessOptions) obj).zza);
        }
        return false;
    }

    @Override // com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
    public int getExtensionType() {
        return 3;
    }

    @Override // com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
    @NonNull
    public List<Scope> getImpliedScopes() {
        return new ArrayList(this.zza);
    }

    public int hashCode() {
        return Objects.hashCode(this.zza);
    }

    @Override // com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
    @NonNull
    public Bundle toBundle() {
        return new Bundle();
    }
}
