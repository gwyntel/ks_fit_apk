package com.fluttercandies.flutter_image_compress.core;

import android.content.Context;
import androidx.core.app.NotificationCompat;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import com.fluttercandies.flutter_image_compress.core.CompressFileHandler;
import com.fluttercandies.flutter_image_compress.exif.Exif;
import com.fluttercandies.flutter_image_compress.format.FormatRegister;
import com.fluttercandies.flutter_image_compress.handle.FormatHandler;
import com.fluttercandies.flutter_image_compress.logger.LogExtKt;
import com.umeng.analytics.pro.f;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/core/CompressFileHandler;", "Lcom/fluttercandies/flutter_image_compress/core/ResultHandler;", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "(Lio/flutter/plugin/common/MethodCall;Lio/flutter/plugin/common/MethodChannel$Result;)V", "handle", "", f.X, "Landroid/content/Context;", "handleGetFile", "flutter_image_compress_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class CompressFileHandler extends ResultHandler {

    @NotNull
    private final MethodCall call;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompressFileHandler(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        super(result);
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        this.call = call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handle$lambda$0(CompressFileHandler this$0, Context context) throws IOException {
        int i2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(context, "$context");
        Object obj = this$0.call.arguments;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any>");
        List list = (List) obj;
        int rotationDegrees = 0;
        Object obj2 = list.get(0);
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
        String str = (String) obj2;
        Object obj3 = list.get(1);
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) obj3).intValue();
        Object obj4 = list.get(2);
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue2 = ((Integer) obj4).intValue();
        Object obj5 = list.get(3);
        Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue3 = ((Integer) obj5).intValue();
        Object obj6 = list.get(4);
        Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue4 = ((Integer) obj6).intValue();
        Object obj7 = list.get(5);
        Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.Boolean");
        boolean zBooleanValue = ((Boolean) obj7).booleanValue();
        Object obj8 = list.get(6);
        Intrinsics.checkNotNull(obj8, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue5 = ((Integer) obj8).intValue();
        Object obj9 = list.get(7);
        Intrinsics.checkNotNull(obj9, "null cannot be cast to non-null type kotlin.Boolean");
        boolean zBooleanValue2 = ((Boolean) obj9).booleanValue();
        Object obj10 = list.get(8);
        Intrinsics.checkNotNull(obj10, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue6 = ((Integer) obj10).intValue();
        Object obj11 = list.get(9);
        Intrinsics.checkNotNull(obj11, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue7 = ((Integer) obj11).intValue();
        FormatHandler formatHandlerFindFormat = FormatRegister.INSTANCE.findFormat(iIntValue5);
        if (formatHandlerFindFormat == null) {
            LogExtKt.log("No support format.");
            this$0.reply(null);
            return;
        }
        if (zBooleanValue) {
            rotationDegrees = Exif.INSTANCE.getRotationDegrees(FilesKt.readBytes(new File(str)));
        }
        if (rotationDegrees == 90 || rotationDegrees == 270) {
            i2 = iIntValue;
        } else {
            i2 = iIntValue2;
            iIntValue2 = iIntValue;
        }
        int i3 = iIntValue4 + rotationDegrees;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try {
                formatHandlerFindFormat.handleFile(context, str, byteArrayOutputStream, iIntValue2, i2, iIntValue3, i3, zBooleanValue2, iIntValue6, iIntValue7);
                this$0.reply(byteArrayOutputStream.toByteArray());
            } catch (Exception e2) {
                if (ImageCompressPlugin.INSTANCE.getShowLog()) {
                    e2.printStackTrace();
                }
                this$0.reply(null);
            }
        } finally {
            byteArrayOutputStream.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Not initialized variable reg: 15, insn: 0x0107: MOVE (r14 I:??[OBJECT, ARRAY]) = (r15 I:??[OBJECT, ARRAY]) (LINE:264), block:B:21:0x0107 */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void handleGetFile$lambda$1(com.fluttercandies.flutter_image_compress.core.CompressFileHandler r17, android.content.Context r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fluttercandies.flutter_image_compress.core.CompressFileHandler.handleGetFile$lambda$1(com.fluttercandies.flutter_image_compress.core.CompressFileHandler, android.content.Context):void");
    }

    public final void handle(@NotNull final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ResultHandler.INSTANCE.getThreadPool().execute(new Runnable() { // from class: v.a
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                CompressFileHandler.handle$lambda$0(this.f26871a, context);
            }
        });
    }

    public final void handleGetFile(@NotNull final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ResultHandler.INSTANCE.getThreadPool().execute(new Runnable() { // from class: v.b
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                CompressFileHandler.handleGetFile$lambda$1(this.f26873a, context);
            }
        });
    }
}
