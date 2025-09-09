package io.github.ponnamkarthik.toast.fluttertoast;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast$Callback;
import androidx.core.app.NotificationCompat;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.tekartik.sqflite.Constant;
import com.umeng.analytics.pro.f;
import io.flutter.FlutterInjector;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.github.ponnamkarthik.toast.fluttertoast.MethodCallHandlerImpl;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import q0.a;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lio/github/ponnamkarthik/toast/fluttertoast/MethodCallHandlerImpl;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", f.X, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mToast", "Landroid/widget/Toast;", "onMethodCall", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "result", "Lio/flutter/plugin/common/MethodChannel$Result;", "fluttertoast_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {

    @NotNull
    private Context context;

    @Nullable
    private Toast mToast;

    public MethodCallHandlerImpl(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onMethodCall$lambda$0(MethodCallHandlerImpl this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast toast = this$0.mToast;
        if (toast != null) {
            toast.show();
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NotNull MethodCall call, @NotNull MethodChannel.Result result) {
        Toast toast;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (!Intrinsics.areEqual(str, "showToast")) {
            if (!Intrinsics.areEqual(str, Constant.PARAM_CANCEL)) {
                result.notImplemented();
                return;
            }
            Toast toast2 = this.mToast;
            if (toast2 != null) {
                if (toast2 != null) {
                    toast2.cancel();
                }
                this.mToast = null;
            }
            result.success(Boolean.TRUE);
            return;
        }
        String strValueOf = String.valueOf(call.argument("msg"));
        String strValueOf2 = String.valueOf(call.argument(SessionDescription.ATTR_LENGTH));
        String strValueOf3 = String.valueOf(call.argument("gravity"));
        Number number = (Number) call.argument("bgcolor");
        Number number2 = (Number) call.argument("textcolor");
        Number number3 = (Number) call.argument(TtmlNode.ATTR_TTS_FONT_SIZE);
        String str2 = (String) call.argument("fontAsset");
        int i2 = Intrinsics.areEqual(strValueOf3, ViewHierarchyConstants.DIMENSION_TOP_KEY) ? 48 : Intrinsics.areEqual(strValueOf3, TtmlNode.CENTER) ? 17 : 80;
        boolean zAreEqual = Intrinsics.areEqual(strValueOf2, "long");
        if (number != null) {
            Object systemService = this.context.getSystemService("layout_inflater");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.LayoutInflater");
            View viewInflate = ((LayoutInflater) systemService).inflate(R.layout.toast_custom, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.text);
            textView.setText(strValueOf);
            Drawable drawable = this.context.getDrawable(R.drawable.corner);
            Intrinsics.checkNotNull(drawable);
            Intrinsics.checkNotNull(drawable);
            drawable.setColorFilter(number.intValue(), PorterDuff.Mode.SRC_IN);
            textView.setBackground(drawable);
            if (number3 != null) {
                textView.setTextSize(number3.floatValue());
            }
            if (number2 != null) {
                textView.setTextColor(number2.intValue());
            }
            Toast toast3 = new Toast(this.context);
            this.mToast = toast3;
            toast3.setDuration(zAreEqual ? 1 : 0);
            if (str2 != null) {
                AssetManager assets = this.context.getAssets();
                Intrinsics.checkNotNullExpressionValue(assets, "getAssets(...)");
                String lookupKeyForAsset = FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(str2);
                Intrinsics.checkNotNullExpressionValue(lookupKeyForAsset, "getLookupKeyForAsset(...)");
                textView.setTypeface(Typeface.createFromAsset(assets, lookupKeyForAsset));
            }
            Toast toast4 = this.mToast;
            if (toast4 != null) {
                toast4.setView(viewInflate);
            }
        } else {
            Log.d("KARTHIK", "showToast: " + number + " " + number2 + " " + number3 + " " + str2);
            Toast toastMakeText = Toast.makeText(this.context, strValueOf, zAreEqual ? 1 : 0);
            this.mToast = toastMakeText;
            if (Build.VERSION.SDK_INT < 30) {
                View view = toastMakeText != null ? toastMakeText.getView() : null;
                Intrinsics.checkNotNull(view);
                View viewFindViewById = view.findViewById(android.R.id.message);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
                TextView textView2 = (TextView) viewFindViewById;
                if (number3 != null) {
                    textView2.setTextSize(number3.floatValue());
                }
                if (number2 != null) {
                    textView2.setTextColor(number2.intValue());
                }
                if (str2 != null) {
                    AssetManager assets2 = this.context.getAssets();
                    Intrinsics.checkNotNullExpressionValue(assets2, "getAssets(...)");
                    String lookupKeyForAsset2 = FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(str2);
                    Intrinsics.checkNotNullExpressionValue(lookupKeyForAsset2, "getLookupKeyForAsset(...)");
                    textView2.setTypeface(Typeface.createFromAsset(assets2, lookupKeyForAsset2));
                }
            }
        }
        try {
            if (i2 == 17) {
                Toast toast5 = this.mToast;
                if (toast5 != null) {
                    toast5.setGravity(i2, 0, 0);
                }
            } else if (i2 != 48) {
                Toast toast6 = this.mToast;
                if (toast6 != null) {
                    toast6.setGravity(i2, 0, 100);
                }
            } else {
                Toast toast7 = this.mToast;
                if (toast7 != null) {
                    toast7.setGravity(i2, 0, 100);
                }
            }
        } catch (Exception unused) {
        }
        Context context = this.context;
        if (context instanceof Activity) {
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
            ((Activity) context).runOnUiThread(new Runnable() { // from class: q0.c
                @Override // java.lang.Runnable
                public final void run() {
                    MethodCallHandlerImpl.onMethodCall$lambda$0(this.f26814a);
                }
            });
        } else {
            Toast toast8 = this.mToast;
            if (toast8 != null) {
                toast8.show();
            }
        }
        if (Build.VERSION.SDK_INT >= 30 && (toast = this.mToast) != null) {
            toast.addCallback(a.a(new Toast$Callback() { // from class: io.github.ponnamkarthik.toast.fluttertoast.MethodCallHandlerImpl.onMethodCall.2
                public void onToastHidden() {
                    super.onToastHidden();
                    MethodCallHandlerImpl.this.mToast = null;
                }
            }));
        }
        result.success(Boolean.TRUE);
    }
}
