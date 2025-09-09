package com.fluttercandies.flutter_image_compress.logger;

import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import kotlin.Metadata;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¨\u0006\u0004"}, d2 = {"log", "", Languages.ANY, "", "flutter_image_compress_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class LogExtKt {
    public static final void log(@Nullable Object obj) {
        String string;
        if (ImageCompressPlugin.INSTANCE.getShowLog()) {
            if (obj == null || (string = obj.toString()) == null) {
                string = TmpConstant.GROUP_ROLE_UNKNOWN;
            }
            Log.i("flutter_image_compress", string);
        }
    }
}
