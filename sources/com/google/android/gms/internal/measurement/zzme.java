package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes3.dex */
public class zzme extends IOException {
    private zznj zza;

    public zzme(String str) {
        super(str);
        this.zza = null;
    }

    static zzmh zza() {
        return new zzmh("Protocol message tag had invalid wire type.");
    }

    static zzme zzb() {
        return new zzme("Protocol message end-group tag did not match expected tag.");
    }

    static zzme zzc() {
        return new zzme("Protocol message contained an invalid tag (zero).");
    }

    static zzme zzd() {
        return new zzme("Protocol message had invalid UTF-8.");
    }

    static zzme zze() {
        return new zzme("CodedInputStream encountered a malformed varint.");
    }

    static zzme zzf() {
        return new zzme("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzme zzg() {
        return new zzme("Failed to parse the message.");
    }

    static zzme zzh() {
        return new zzme("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }
}
