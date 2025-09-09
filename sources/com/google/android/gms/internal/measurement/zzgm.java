package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes3.dex */
public final class zzgm {

    @GuardedBy("PhenotypeConstants.class")
    private static final ArrayMap<String, Uri> zza = new ArrayMap<>();

    public static synchronized Uri zza(String str) {
        Uri uri;
        ArrayMap<String, Uri> arrayMap = zza;
        uri = arrayMap.get(str);
        if (uri == null) {
            uri = Uri.parse("content://com.google.android.gms.phenotype/" + Uri.encode(str));
            arrayMap.put(str, uri);
        }
        return uri;
    }

    public static String zza(Context context, String str) {
        if (!str.contains(MqttTopic.MULTI_LEVEL_WILDCARD)) {
            return str + MqttTopic.MULTI_LEVEL_WILDCARD + "" + context.getPackageName();
        }
        throw new IllegalArgumentException("The passed in package cannot already have a subpackage: " + str);
    }
}
