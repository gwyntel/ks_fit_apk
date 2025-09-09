package com.example.imagegallerysaver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.facebook.share.internal.ShareConstants;
import com.umeng.analytics.pro.f;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J \u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u000f2\b\b\u0001\u0010\u0010\u001a\u00020\u0011H\u0016J\u001c\u0010\u0013\u001a\u00020\u000f2\b\b\u0001\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\u0016\u001a\u00020\u0017H\u0016J<\u0010\u0018\u001a\"\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0019j\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u001a`\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0002JK\u0010\u001d\u001a\"\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0019j\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u001a`\u001b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0002¢\u0006\u0002\u0010\"J\u001a\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u00052\b\u0010%\u001a\u0004\u0018\u00010\tH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/example/imagegallerysaver/ImageGallerySaverPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "()V", "applicationContext", "Landroid/content/Context;", "methodChannel", "Lio/flutter/plugin/common/MethodChannel;", "generateUri", "Landroid/net/Uri;", ShareConstants.MEDIA_EXTENSION, "", "name", "getMIMEType", "onAttachedToEngine", "", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "saveFileToGallery", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "filePath", "saveImageToGallery", "bmp", "Landroid/graphics/Bitmap;", "quality", "", "(Landroid/graphics/Bitmap;Ljava/lang/Integer;Ljava/lang/String;)Ljava/util/HashMap;", "sendBroadcast", f.X, "fileUri", "image_gallery_saver_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nImageGallerySaverPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ImageGallerySaverPlugin.kt\ncom/example/imagegallerysaver/ImageGallerySaverPlugin\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,248:1\n1#2:249\n*E\n"})
/* loaded from: classes3.dex */
public final class ImageGallerySaverPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    @Nullable
    private Context applicationContext;
    private MethodChannel methodChannel;

    private final Uri generateUri(String extension, String name) {
        ContentResolver contentResolver;
        if (name == null) {
            name = String.valueOf(System.currentTimeMillis());
        }
        String mIMEType = getMIMEType(extension);
        boolean z2 = false;
        if (mIMEType != null && StringsKt.startsWith$default(mIMEType, "video", false, 2, (Object) null)) {
            z2 = true;
        }
        if (Build.VERSION.SDK_INT < 29) {
            File file = new File(Environment.getExternalStoragePublicDirectory(z2 ? Environment.DIRECTORY_MOVIES : Environment.DIRECTORY_PICTURES).getAbsolutePath());
            if (!file.exists()) {
                file.mkdir();
            }
            if (extension.length() > 0) {
                name = ((Object) name) + "." + extension;
            }
            return Uri.fromFile(new File(file, name));
        }
        Uri uri = z2 ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", name);
        contentValues.put("relative_path", z2 ? Environment.DIRECTORY_MOVIES : Environment.DIRECTORY_PICTURES);
        if (!TextUtils.isEmpty(mIMEType)) {
            contentValues.put("mime_type", mIMEType);
        }
        Context context = this.applicationContext;
        if (context == null || (contentResolver = context.getContentResolver()) == null) {
            return null;
        }
        return contentResolver.insert(uri, contentValues);
    }

    private final String getMIMEType(String extension) {
        if (TextUtils.isEmpty(extension)) {
            return null;
        }
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        String lowerCase = extension.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return singleton.getMimeTypeFromExtension(lowerCase);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.util.HashMap<java.lang.String, java.lang.Object> saveFileToGallery(java.lang.String r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.imagegallerysaver.ImageGallerySaverPlugin.saveFileToGallery(java.lang.String, java.lang.String):java.util.HashMap");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final java.util.HashMap<java.lang.String, java.lang.Object> saveImageToGallery(android.graphics.Bitmap r8, java.lang.Integer r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            if (r8 == 0) goto Lb1
            if (r9 != 0) goto L8
            goto Lb1
        L8:
            android.content.Context r2 = r7.applicationContext
            if (r2 != 0) goto L18
            com.example.imagegallerysaver.SaveResultModel r8 = new com.example.imagegallerysaver.SaveResultModel
            java.lang.String r9 = "applicationContext null"
            r8.<init>(r0, r1, r9)
            java.util.HashMap r8 = r8.toHashMap()
            return r8
        L18:
            r3 = 1
            java.lang.String r4 = "jpg"
            android.net.Uri r10 = r7.generateUri(r4, r10)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L66
            if (r10 == 0) goto L5b
            android.content.ContentResolver r4 = r2.getContentResolver()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            java.io.OutputStream r4 = r4.openOutputStream(r10)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L58
            if (r4 == 0) goto L54
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            r5.<init>()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            java.lang.String r6 = "ImageGallerySaverPlugin "
            r5.append(r6)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            r5.append(r9)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            java.io.PrintStream r6 = java.lang.System.out     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            r6.println(r5)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            android.graphics.Bitmap$CompressFormat r5 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            int r9 = r9.intValue()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            r8.compress(r5, r9, r4)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            r4.flush()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52
            r9 = r3
            goto L5d
        L4f:
            r9 = move-exception
            r1 = r4
            goto La8
        L52:
            r9 = move-exception
            goto L69
        L54:
            r9 = r0
            goto L5d
        L56:
            r9 = move-exception
            goto La8
        L58:
            r9 = move-exception
            r4 = r1
            goto L69
        L5b:
            r9 = r0
            r4 = r1
        L5d:
            if (r4 == 0) goto L62
            r4.close()
        L62:
            r8.recycle()
            goto L7e
        L66:
            r9 = move-exception
            r10 = r1
            r4 = r10
        L69:
            com.example.imagegallerysaver.SaveResultModel r5 = new com.example.imagegallerysaver.SaveResultModel     // Catch: java.lang.Throwable -> L4f
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L4f
            r5.<init>(r0, r1, r9)     // Catch: java.lang.Throwable -> L4f
            r5.toHashMap()     // Catch: java.lang.Throwable -> L4f
            if (r4 == 0) goto L7a
            r4.close()
        L7a:
            r8.recycle()
            r9 = r0
        L7e:
            if (r9 == 0) goto L9c
            r7.sendBroadcast(r2, r10)
            com.example.imagegallerysaver.SaveResultModel r8 = new com.example.imagegallerysaver.SaveResultModel
            java.lang.String r9 = java.lang.String.valueOf(r10)
            int r9 = r9.length()
            if (r9 <= 0) goto L90
            r0 = r3
        L90:
            java.lang.String r9 = java.lang.String.valueOf(r10)
            r8.<init>(r0, r9, r1)
            java.util.HashMap r8 = r8.toHashMap()
            goto La7
        L9c:
            com.example.imagegallerysaver.SaveResultModel r8 = new com.example.imagegallerysaver.SaveResultModel
            java.lang.String r9 = "saveImageToGallery fail"
            r8.<init>(r0, r1, r9)
            java.util.HashMap r8 = r8.toHashMap()
        La7:
            return r8
        La8:
            if (r1 == 0) goto Lad
            r1.close()
        Lad:
            r8.recycle()
            throw r9
        Lb1:
            com.example.imagegallerysaver.SaveResultModel r8 = new com.example.imagegallerysaver.SaveResultModel
            java.lang.String r9 = "parameters error"
            r8.<init>(r0, r1, r9)
            java.util.HashMap r8 = r8.toHashMap()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.imagegallerysaver.ImageGallerySaverPlugin.saveImageToGallery(android.graphics.Bitmap, java.lang.Integer, java.lang.String):java.util.HashMap");
    }

    private final void sendBroadcast(Context context, Uri fileUri) {
        if (Build.VERSION.SDK_INT < 29) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(fileUri);
            context.sendBroadcast(intent);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.applicationContext = binding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(binding.getBinaryMessenger(), "image_gallery_saver");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.applicationContext = null;
        MethodChannel methodChannel = this.methodChannel;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (Intrinsics.areEqual(str, "saveImageToGallery")) {
            byte[] bArr = (byte[]) call.argument("imageBytes");
            result.success(saveImageToGallery(BitmapFactory.decodeByteArray(bArr == null ? new byte[0] : bArr, 0, bArr != null ? bArr.length : 0), (Integer) call.argument("quality"), (String) call.argument("name")));
        } else if (Intrinsics.areEqual(str, "saveFileToGallery")) {
            result.success(saveFileToGallery((String) call.argument("file"), (String) call.argument("name")));
        } else {
            result.notImplemented();
        }
    }
}
