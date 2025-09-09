package androidx.media3.common;

import androidx.media3.common.util.UnstableApi;
import java.util.HashSet;

@UnstableApi
/* loaded from: classes.dex */
public final class MediaLibraryInfo {
    public static final boolean ASSERTIONS_ENABLED = true;
    public static final String TAG = "AndroidXMedia3";
    public static final boolean TRACE_ENABLED = true;
    public static final String VERSION = "1.4.0";
    public static final int VERSION_INT = 1004000300;
    public static final String VERSION_SLASHY = "AndroidXMedia3/1.4.0";
    private static final HashSet<String> registeredModules = new HashSet<>();
    private static String registeredModulesString = "media3.common";

    private MediaLibraryInfo() {
    }

    public static synchronized void registerModule(String str) {
        if (registeredModules.add(str)) {
            registeredModulesString += ", " + str;
        }
    }

    public static synchronized String registeredModules() {
        return registeredModulesString;
    }
}
