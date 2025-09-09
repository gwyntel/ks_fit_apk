package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public class COSEAlgorithmIdentifier implements Parcelable {

    @NonNull
    public static final Parcelable.Creator<COSEAlgorithmIdentifier> CREATOR = new zzp();

    @NonNull
    private final Algorithm zza;

    public static class UnsupportedAlgorithmIdentifierException extends Exception {
        public UnsupportedAlgorithmIdentifierException(int i2) {
            super("Algorithm with COSE value " + i2 + " not supported");
        }
    }

    COSEAlgorithmIdentifier(Algorithm algorithm) {
        this.zza = (Algorithm) Preconditions.checkNotNull(algorithm);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public static COSEAlgorithmIdentifier fromCoseValue(int i2) throws UnsupportedAlgorithmIdentifierException {
        RSAAlgorithm rSAAlgorithm;
        if (i2 == RSAAlgorithm.LEGACY_RS1.getAlgoValue()) {
            rSAAlgorithm = RSAAlgorithm.RS1;
        } else {
            RSAAlgorithm[] rSAAlgorithmArrValues = RSAAlgorithm.values();
            int length = rSAAlgorithmArrValues.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    for (RSAAlgorithm rSAAlgorithm2 : EC2Algorithm.values()) {
                        if (rSAAlgorithm2.getAlgoValue() == i2) {
                            rSAAlgorithm = rSAAlgorithm2;
                        }
                    }
                    throw new UnsupportedAlgorithmIdentifierException(i2);
                }
                RSAAlgorithm rSAAlgorithm3 = rSAAlgorithmArrValues[i3];
                if (rSAAlgorithm3.getAlgoValue() == i2) {
                    rSAAlgorithm = rSAAlgorithm3;
                    break;
                }
                i3++;
            }
        }
        return new COSEAlgorithmIdentifier(rSAAlgorithm);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(@NonNull Object obj) {
        return (obj instanceof COSEAlgorithmIdentifier) && this.zza.getAlgoValue() == ((COSEAlgorithmIdentifier) obj).zza.getAlgoValue();
    }

    public int hashCode() {
        return Objects.hashCode(this.zza);
    }

    public int toCoseValue() {
        return this.zza.getAlgoValue();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        parcel.writeInt(this.zza.getAlgoValue());
    }
}
