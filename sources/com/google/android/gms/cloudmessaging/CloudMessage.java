package com.google.android.gms.cloudmessaging;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.health.connect.client.records.OvulationTestRecord;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.messaging.Constants;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

@SafeParcelable.Class(creator = "CloudMessageCreator")
/* loaded from: classes3.dex */
public final class CloudMessage extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<CloudMessage> CREATOR = new zza();
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;

    /* renamed from: a, reason: collision with root package name */
    Intent f12667a;

    @GuardedBy("this")
    private Map<String, String> zzb;

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    @SafeParcelable.Constructor
    public CloudMessage(@NonNull @SafeParcelable.Param(id = 1) Intent intent) {
        this.f12667a = intent;
    }

    private static int zza(@Nullable String str) {
        if (OvulationTestRecord.Result.HIGH.equals(str)) {
            return 1;
        }
        return "normal".equals(str) ? 2 : 0;
    }

    @Nullable
    public String getCollapseKey() {
        return this.f12667a.getStringExtra(Constants.MessagePayloadKeys.COLLAPSE_KEY);
    }

    @NonNull
    public synchronized Map<String, String> getData() {
        try {
            if (this.zzb == null) {
                Bundle extras = this.f12667a.getExtras();
                ArrayMap arrayMap = new ArrayMap();
                if (extras != null) {
                    for (String str : extras.keySet()) {
                        Object obj = extras.get(str);
                        if (obj instanceof String) {
                            String str2 = (String) obj;
                            if (!str.startsWith(Constants.MessagePayloadKeys.RESERVED_PREFIX) && !str.equals("from") && !str.equals("message_type") && !str.equals(Constants.MessagePayloadKeys.COLLAPSE_KEY)) {
                                arrayMap.put(str, str2);
                            }
                        }
                    }
                }
                this.zzb = arrayMap;
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.zzb;
    }

    @Nullable
    public String getFrom() {
        return this.f12667a.getStringExtra("from");
    }

    @NonNull
    public Intent getIntent() {
        return this.f12667a;
    }

    @Nullable
    public String getMessageId() {
        String stringExtra = this.f12667a.getStringExtra(Constants.MessagePayloadKeys.MSGID);
        return stringExtra == null ? this.f12667a.getStringExtra(Constants.MessagePayloadKeys.MSGID_SERVER) : stringExtra;
    }

    @Nullable
    public String getMessageType() {
        return this.f12667a.getStringExtra("message_type");
    }

    public int getOriginalPriority() {
        String stringExtra = this.f12667a.getStringExtra(Constants.MessagePayloadKeys.ORIGINAL_PRIORITY);
        if (stringExtra == null) {
            stringExtra = this.f12667a.getStringExtra(Constants.MessagePayloadKeys.PRIORITY_V19);
        }
        return zza(stringExtra);
    }

    public int getPriority() {
        String stringExtra = this.f12667a.getStringExtra(Constants.MessagePayloadKeys.DELIVERED_PRIORITY);
        if (stringExtra == null) {
            if ("1".equals(this.f12667a.getStringExtra(Constants.MessagePayloadKeys.PRIORITY_REDUCED_V19))) {
                return 2;
            }
            stringExtra = this.f12667a.getStringExtra(Constants.MessagePayloadKeys.PRIORITY_V19);
        }
        return zza(stringExtra);
    }

    @Nullable
    public byte[] getRawData() {
        return this.f12667a.getByteArrayExtra(Constants.MessagePayloadKeys.RAW_DATA);
    }

    @Nullable
    public String getSenderId() {
        return this.f12667a.getStringExtra(Constants.MessagePayloadKeys.SENDER_ID);
    }

    public long getSentTime() {
        Bundle extras = this.f12667a.getExtras();
        Object obj = extras != null ? extras.get(Constants.MessagePayloadKeys.SENT_TIME) : null;
        if (obj instanceof Long) {
            return ((Long) obj).longValue();
        }
        if (!(obj instanceof String)) {
            return 0L;
        }
        try {
            return Long.parseLong((String) obj);
        } catch (NumberFormatException unused) {
            String strValueOf = String.valueOf(obj);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 19);
            sb.append("Invalid sent time: ");
            sb.append(strValueOf);
            Log.w("CloudMessage", sb.toString());
            return 0L;
        }
    }

    @Nullable
    public String getTo() {
        return this.f12667a.getStringExtra(Constants.MessagePayloadKeys.TO);
    }

    public int getTtl() {
        Bundle extras = this.f12667a.getExtras();
        Object obj = extras != null ? extras.get(Constants.MessagePayloadKeys.TTL) : null;
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (!(obj instanceof String)) {
            return 0;
        }
        try {
            return Integer.parseInt((String) obj);
        } catch (NumberFormatException unused) {
            String strValueOf = String.valueOf(obj);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 13);
            sb.append("Invalid TTL: ");
            sb.append(strValueOf);
            Log.w("CloudMessage", sb.toString());
            return 0;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.f12667a, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
