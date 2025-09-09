package com.baseflow.geolocator.location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Map;

/* loaded from: classes3.dex */
public class ForegroundNotificationOptions {

    @Nullable
    private final Integer color;
    private final boolean enableWakeLock;
    private final boolean enableWifiLock;

    @NonNull
    private final String notificationChannelName;

    @NonNull
    private final AndroidIconResource notificationIcon;

    @NonNull
    private final String notificationText;

    @NonNull
    private final String notificationTitle;
    private final boolean setOngoing;

    private ForegroundNotificationOptions(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull AndroidIconResource androidIconResource, boolean z2, boolean z3, boolean z4, @Nullable Integer num) {
        this.notificationTitle = str;
        this.notificationText = str2;
        this.notificationChannelName = str3;
        this.notificationIcon = androidIconResource;
        this.enableWifiLock = z2;
        this.enableWakeLock = z3;
        this.setOngoing = z4;
        this.color = num;
    }

    public static ForegroundNotificationOptions parseArguments(@Nullable Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        AndroidIconResource arguments = AndroidIconResource.parseArguments((Map) map.get("notificationIcon"));
        String str = (String) map.get("notificationTitle");
        String str2 = (String) map.get("notificationChannelName");
        String str3 = (String) map.get("notificationText");
        Boolean bool = (Boolean) map.get("enableWifiLock");
        Boolean bool2 = (Boolean) map.get("enableWakeLock");
        Boolean bool3 = (Boolean) map.get("setOngoing");
        Object obj = map.get("color");
        return new ForegroundNotificationOptions(str, str3, str2, arguments, bool.booleanValue(), bool2.booleanValue(), bool3.booleanValue(), obj != null ? Integer.valueOf(((Number) obj).intValue()) : null);
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    @NonNull
    public String getNotificationChannelName() {
        return this.notificationChannelName;
    }

    @NonNull
    public AndroidIconResource getNotificationIcon() {
        return this.notificationIcon;
    }

    @NonNull
    public String getNotificationText() {
        return this.notificationText;
    }

    @NonNull
    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public boolean isEnableWakeLock() {
        return this.enableWakeLock;
    }

    public boolean isEnableWifiLock() {
        return this.enableWifiLock;
    }

    public boolean isSetOngoing() {
        return this.setOngoing;
    }
}
