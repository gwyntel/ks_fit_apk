package kotlin.io.path;

import java.nio.file.FileSystemLoopException;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class v {
    public static /* synthetic */ FileSystemLoopException a(String str) {
        return new FileSystemLoopException(str);
    }
}
