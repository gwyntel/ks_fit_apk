package android.support.v4.media;

import androidx.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    static {
        try {
            sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(List.class);
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            e2.printStackTrace();
        }
    }

    private ParceledListSliceAdapterApi21() {
    }
}
