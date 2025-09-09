package org.apache.commons.io;

import java.io.File;

@Deprecated
/* loaded from: classes5.dex */
public class FileCleaner {

    /* renamed from: a, reason: collision with root package name */
    static final FileCleaningTracker f26593a = new FileCleaningTracker();

    @Deprecated
    public static synchronized void exitWhenFinished() {
        f26593a.exitWhenFinished();
    }

    public static FileCleaningTracker getInstance() {
        return f26593a;
    }

    @Deprecated
    public static int getTrackCount() {
        return f26593a.getTrackCount();
    }

    @Deprecated
    public static void track(File file, Object obj) {
        f26593a.track(file, obj);
    }

    @Deprecated
    public static void track(File file, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        f26593a.track(file, obj, fileDeleteStrategy);
    }

    @Deprecated
    public static void track(String str, Object obj) {
        f26593a.track(str, obj);
    }

    @Deprecated
    public static void track(String str, Object obj, FileDeleteStrategy fileDeleteStrategy) {
        f26593a.track(str, obj, fileDeleteStrategy);
    }
}
