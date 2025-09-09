package androidx.health.connect.client.records;

/* loaded from: classes.dex */
public abstract /* synthetic */ class c {
    public static /* synthetic */ int a(double d2) {
        long jDoubleToLongBits = Double.doubleToLongBits(d2);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }
}
