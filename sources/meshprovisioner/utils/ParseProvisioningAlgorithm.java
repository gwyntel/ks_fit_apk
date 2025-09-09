package meshprovisioner.utils;

/* loaded from: classes5.dex */
public class ParseProvisioningAlgorithm {
    public static final int FIPS_P_256_ELLIPTIC_CURVE = 1;

    public static String getAlgorithmType(int i2) {
        return i2 != 1 ? "NONE" : "FIPS P-256 ELLIPTIC CURVE";
    }

    public static byte getAlgorithmValue(int i2) {
        return i2 != 1 ? (byte) 1 : (byte) 0;
    }
}
