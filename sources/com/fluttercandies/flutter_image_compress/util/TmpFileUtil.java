package com.fluttercandies.flutter_image_compress.util;

import android.content.Context;
import com.umeng.analytics.pro.f;
import java.io.File;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/util/TmpFileUtil;", "", "()V", "createTmpFile", "Ljava/io/File;", f.X, "Landroid/content/Context;", "flutter_image_compress_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class TmpFileUtil {

    @NotNull
    public static final TmpFileUtil INSTANCE = new TmpFileUtil();

    private TmpFileUtil() {
    }

    @NotNull
    public final File createTmpFile(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new File(context.getCacheDir(), string);
    }
}
