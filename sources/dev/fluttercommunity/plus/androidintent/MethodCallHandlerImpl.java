package dev.fluttercommunity.plus.androidintent;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.share.internal.ShareConstants;
import com.taobao.accs.common.Constants;
import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {
    private static final String TAG = "MethodCallHandlerImpl";

    @Nullable
    private MethodChannel methodChannel;
    private final IntentSender sender;

    MethodCallHandlerImpl(IntentSender intentSender) {
        this.sender = intentSender;
    }

    private static String convertAction(String str) {
        if (str == null) {
            return null;
        }
        switch (str) {
            case "action_location_source_settings":
                return "android.settings.LOCATION_SOURCE_SETTINGS";
            case "action_application_details_settings":
                return "android.settings.APPLICATION_DETAILS_SETTINGS";
            case "settings":
                return "android.settings.SETTINGS";
            case "action_view":
                return "android.intent.action.VIEW";
            case "action_voice":
                return "android.intent.action.VOICE_COMMAND";
            default:
                return str;
        }
    }

    private static Bundle convertArguments(Map<String, ?> map) {
        Bundle bundle = new Bundle();
        if (map == null) {
            return bundle;
        }
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof Integer) {
                bundle.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof String) {
                bundle.putString(str, (String) obj);
            } else if (obj instanceof Boolean) {
                bundle.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof byte[]) {
                bundle.putByteArray(str, (byte[]) obj);
            } else if (obj instanceof int[]) {
                bundle.putIntArray(str, (int[]) obj);
            } else if (obj instanceof long[]) {
                bundle.putLongArray(str, (long[]) obj);
            } else if (obj instanceof double[]) {
                bundle.putDoubleArray(str, (double[]) obj);
            } else if (isTypedArrayList(obj, Integer.class)) {
                bundle.putIntegerArrayList(str, (ArrayList) obj);
            } else if (isTypedArrayList(obj, String.class)) {
                bundle.putStringArrayList(str, (ArrayList) obj);
            } else {
                if (!isStringKeyedMap(obj)) {
                    throw new UnsupportedOperationException("Unsupported type " + obj);
                }
                bundle.putBundle(str, convertArguments((Map) obj));
            }
        }
        return bundle;
    }

    private static Bundle convertArrayArguments(Map<String, ?> map) {
        Bundle bundle = new Bundle();
        if (map == null) {
            return bundle;
        }
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            int i2 = 0;
            if (isTypedArrayList(obj, Boolean.class)) {
                ArrayList arrayList = (ArrayList) obj;
                boolean[] zArr = new boolean[arrayList.size()];
                while (i2 < arrayList.size()) {
                    zArr[i2] = ((Boolean) arrayList.get(i2)).booleanValue();
                    i2++;
                }
                bundle.putBooleanArray(str, zArr);
            } else if (isTypedArrayList(obj, Integer.class)) {
                ArrayList arrayList2 = (ArrayList) obj;
                int[] iArr = new int[arrayList2.size()];
                while (i2 < arrayList2.size()) {
                    iArr[i2] = ((Integer) arrayList2.get(i2)).intValue();
                    i2++;
                }
                bundle.putIntArray(str, iArr);
            } else if (isTypedArrayList(obj, Long.class)) {
                ArrayList arrayList3 = (ArrayList) obj;
                long[] jArr = new long[arrayList3.size()];
                while (i2 < arrayList3.size()) {
                    jArr[i2] = ((Long) arrayList3.get(i2)).longValue();
                    i2++;
                }
                bundle.putLongArray(str, jArr);
            } else if (isTypedArrayList(obj, Double.class)) {
                ArrayList arrayList4 = (ArrayList) obj;
                double[] dArr = new double[arrayList4.size()];
                while (i2 < arrayList4.size()) {
                    dArr[i2] = ((Double) arrayList4.get(i2)).doubleValue();
                    i2++;
                }
                bundle.putDoubleArray(str, dArr);
            } else {
                if (!isTypedArrayList(obj, String.class)) {
                    throw new UnsupportedOperationException("Unsupported type " + obj);
                }
                ArrayList arrayList5 = (ArrayList) obj;
                bundle.putStringArray(str, (String[]) arrayList5.toArray(new String[arrayList5.size()]));
            }
        }
        return bundle;
    }

    private static boolean isStringKeyedMap(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        for (Object obj2 : ((Map) obj).keySet()) {
            if (obj2 != null && !(obj2 instanceof String)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTypedArrayList(Object obj, Class<?> cls) {
        if (!(obj instanceof ArrayList)) {
            return false;
        }
        Iterator it = ((ArrayList) obj).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != null && !cls.isInstance(next)) {
                return false;
            }
        }
        return true;
    }

    void a(BinaryMessenger binaryMessenger) {
        if (this.methodChannel != null) {
            Log.wtf(TAG, "Setting a method call handler before the last was disposed.");
            b();
        }
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "dev.fluttercommunity.plus/android_intent");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    void b() {
        MethodChannel methodChannel = this.methodChannel;
        if (methodChannel == null) {
            Log.d(TAG, "Tried to stop listening when no methodChannel had been initialized.");
        } else {
            methodChannel.setMethodCallHandler(null);
            this.methodChannel = null;
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        try {
            String strConvertAction = convertAction((String) methodCall.argument("action"));
            Integer num = (Integer) methodCall.argument(Constants.KEY_FLAGS);
            String str = (String) methodCall.argument("category");
            Uri uri = methodCall.argument("data") != null ? Uri.parse((String) methodCall.argument("data")) : null;
            Bundle bundleConvertArguments = convertArguments((Map) methodCall.argument(Constant.PARAM_SQL_ARGUMENTS));
            bundleConvertArguments.putAll(convertArrayArguments((Map) methodCall.argument("arrayArguments")));
            String str2 = (String) methodCall.argument("package");
            Intent intentA = this.sender.a(strConvertAction, num, str, uri, bundleConvertArguments, str2, (TextUtils.isEmpty(str2) || TextUtils.isEmpty((String) methodCall.argument("componentName"))) ? null : new ComponentName(str2, (String) methodCall.argument("componentName")), (String) methodCall.argument("type"));
            if ("parseAndLaunch".equalsIgnoreCase(methodCall.method)) {
                try {
                    this.sender.d(this.sender.parse((String) methodCall.argument(ShareConstants.MEDIA_URI)));
                    result.success(null);
                    return;
                } catch (URISyntaxException e2) {
                    result.error("parse_error", "Failed to parse URI", e2.getMessage());
                    return;
                }
            }
            if ("launch".equalsIgnoreCase(methodCall.method)) {
                if (intentA != null && !this.sender.b(intentA)) {
                    Log.i(TAG, "Cannot resolve explicit intent, falling back to implicit");
                    intentA.setPackage(null);
                }
                this.sender.d(intentA);
                result.success(null);
                return;
            }
            if ("launchChooser".equalsIgnoreCase(methodCall.method)) {
                this.sender.launchChooser(intentA, (String) methodCall.argument("chooserTitle"));
                result.success(null);
                return;
            }
            if ("sendBroadcast".equalsIgnoreCase(methodCall.method)) {
                this.sender.sendBroadcast(intentA);
                result.success(null);
                return;
            }
            if ("sendService".equalsIgnoreCase(methodCall.method)) {
                this.sender.e(intentA);
                result.success(null);
            } else if ("canResolveActivity".equalsIgnoreCase(methodCall.method)) {
                result.success(Boolean.valueOf(this.sender.b(intentA)));
            } else if ("getResolvedActivity".equalsIgnoreCase(methodCall.method)) {
                result.success(this.sender.c(intentA));
            } else {
                result.notImplemented();
            }
        } catch (Throwable th) {
            result.error("error", th.getMessage(), null);
        }
    }
}
