package androidx.core.location;

import android.location.Location;
import android.os.Bundle;
import java.util.List;

/* loaded from: classes.dex */
public abstract /* synthetic */ class m {
    public static void b(LocationListenerCompat locationListenerCompat, List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            locationListenerCompat.onLocationChanged((Location) list.get(i2));
        }
    }

    public static void a(LocationListenerCompat locationListenerCompat, int i2) {
    }

    public static void c(LocationListenerCompat locationListenerCompat, String str) {
    }

    public static void d(LocationListenerCompat locationListenerCompat, String str) {
    }

    public static void e(LocationListenerCompat locationListenerCompat, String str, int i2, Bundle bundle) {
    }
}
