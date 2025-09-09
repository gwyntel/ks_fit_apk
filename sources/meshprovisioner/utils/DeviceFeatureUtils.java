package meshprovisioner.utils;

/* loaded from: classes5.dex */
public class DeviceFeatureUtils {
    public static final boolean supportsFriendFeature(int i2) {
        return (i2 & 4) > 0;
    }

    public static final boolean supportsLowPowerFeature(int i2) {
        return (i2 & 8) > 0;
    }

    public static final boolean supportsProxyFeature(int i2) {
        return (i2 & 2) > 0;
    }

    public static final boolean supportsRelayFeature(int i2) {
        return (i2 & 1) > 0;
    }
}
