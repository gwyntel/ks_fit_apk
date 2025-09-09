package androidx.health.connect.client.records.metadata;

import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;

@kotlin.Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u0000 \u00132\u00020\u0001:\u0002\u0013\u0014B'\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0006H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0017\u0010\u0005\u001a\u00020\u0006¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e¨\u0006\u0015"}, d2 = {"Landroidx/health/connect/client/records/metadata/Device;", "", "manufacturer", "", "model", "type", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getManufacturer", "()Ljava/lang/String;", "getModel", "getType$annotations", "()V", "getType", "()I", "equals", "", "other", "hashCode", "Companion", "DeviceType", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Device {
    public static final int TYPE_CHEST_STRAP = 7;
    public static final int TYPE_FITNESS_BAND = 6;
    public static final int TYPE_HEAD_MOUNTED = 5;
    public static final int TYPE_PHONE = 2;
    public static final int TYPE_RING = 4;
    public static final int TYPE_SCALE = 3;
    public static final int TYPE_SMART_DISPLAY = 8;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WATCH = 1;

    @Nullable
    private final String manufacturer;

    @Nullable
    private final String model;
    private final int type;

    @kotlin.Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/health/connect/client/records/metadata/Device$DeviceType;", "", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface DeviceType {
    }

    public Device() {
        this(null, null, 0, 7, null);
    }

    public static /* synthetic */ void getType$annotations() {
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(Device.class, other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type androidx.health.connect.client.records.metadata.Device");
        Device device = (Device) other;
        return Intrinsics.areEqual(this.manufacturer, device.manufacturer) && Intrinsics.areEqual(this.model, device.model) && this.type == device.type;
    }

    @Nullable
    public final String getManufacturer() {
        return this.manufacturer;
    }

    @Nullable
    public final String getModel() {
        return this.model;
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        String str = this.manufacturer;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.model;
        return ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + this.type;
    }

    public Device(@Nullable String str, @Nullable String str2, int i2) {
        this.manufacturer = str;
        this.model = str2;
        this.type = i2;
    }

    public /* synthetic */ Device(String str, String str2, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : str, (i3 & 2) != 0 ? null : str2, (i3 & 4) != 0 ? 0 : i2);
    }
}
