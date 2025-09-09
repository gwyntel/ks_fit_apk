package com.example.flutter_social_share;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.core.net.MailTo;
import androidx.webkit.internal.AssetHelper;
import com.facebook.CallbackManager;
import com.luck.picture.lib.config.SelectMimeType;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/* loaded from: classes3.dex */
public class FlutterSocialSharePlugin implements MethodChannel.MethodCallHandler, FlutterPlugin, ActivityAware {
    private static final String TAG = "FlutterSocialSharePlugin";
    private Activity activity;
    private CallbackManager callbackManager;
    private MethodChannel methodChannel;

    private void shareInstagramStory(String str, MethodChannel.Result result) {
        try {
            File file = new File(str);
            Uri uriForFile = FileProvider.getUriForFile(this.activity, this.activity.getPackageName() + ".provider", file);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(SelectMimeType.SYSTEM_IMAGE);
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setPackage("com.instagram.android");
            this.activity.startActivity(intent);
            result.success("success");
        } catch (ActivityNotFoundException unused) {
            result.error("INSTAGRAM_NOT_INSTALLED", "Instagram is not installed on this device", null);
        } catch (Exception e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    private void shareSystem(String str, MethodChannel.Result result) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            intent.putExtra("android.intent.extra.TEXT", str);
            this.activity.startActivity(Intent.createChooser(intent, "Share via"));
            result.success("success");
        } catch (Exception e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    private void shareToFacebook(String str, String str2, MethodChannel.Result result) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            if (str == null || str.isEmpty()) {
                intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            } else {
                File file = new File(str);
                Uri uriForFile = FileProvider.getUriForFile(this.activity, this.activity.getPackageName() + ".provider", file);
                intent.setType(SelectMimeType.SYSTEM_IMAGE);
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
            }
            if (str2 != null && !str2.isEmpty()) {
                intent.putExtra("android.intent.extra.TEXT", str2);
            }
            intent.setPackage("com.facebook.katana");
            this.activity.startActivity(intent);
            result.success("success");
        } catch (ActivityNotFoundException unused) {
            result.error("FACEBOOK_NOT_INSTALLED", "Facebook is not installed on this device", null);
        } catch (Exception e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    private void shareToMail(String str, String str2, List<String> list, MethodChannel.Result result) {
        try {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse(MailTo.MAILTO_SCHEME));
            intent.putExtra("android.intent.extra.SUBJECT", str2);
            intent.putExtra("android.intent.extra.TEXT", str);
            if (list != null && !list.isEmpty()) {
                intent.putExtra("android.intent.extra.EMAIL", (String[]) list.toArray(new String[0]));
            }
            this.activity.startActivity(Intent.createChooser(intent, "Choose an email client"));
            result.success("Email app opened successfully.");
        } catch (Exception e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    private void shareToSms(String str, MethodChannel.Result result) {
        try {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:"));
            intent.putExtra("sms_body", str);
            this.activity.startActivity(intent);
            result.success("SMS app opened successfully.");
        } catch (Exception e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    private void shareToTelegram(String str, MethodChannel.Result result) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            intent.setPackage("org.telegram.messenger");
            intent.putExtra("android.intent.extra.TEXT", str);
            this.activity.startActivity(intent);
            result.success("success");
        } catch (ActivityNotFoundException unused) {
            result.error("TELEGRAM_NOT_INSTALLED", "Telegram is not installed on this device", null);
        }
    }

    private void shareToTwitter(String str, String str2, MethodChannel.Result result) {
        try {
            this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(new URL("https://twitter.com/intent/tweet?text=" + str2 + "&url=" + str).toString())));
            result.success("success");
        } catch (MalformedURLException e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    private void shareWhatsApp(String str, String str2, MethodChannel.Result result, boolean z2) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setPackage(z2 ? "com.whatsapp.w4b" : "com.whatsapp");
            intent.putExtra("android.intent.extra.TEXT", str2);
            if (TextUtils.isEmpty(str)) {
                Log.d(TAG, "shareWhatsAppText: " + str);
                intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            } else {
                Log.d(TAG, "shareWhatsApp: " + str);
                File file = new File(str);
                Uri uriForFile = FileProvider.getUriForFile(this.activity, this.activity.getPackageName() + ".provider", file);
                Log.d(TAG, "shareWhatsAppFileURl: " + uriForFile);
                intent.setType(SelectMimeType.SYSTEM_IMAGE);
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                intent.putExtra("android.intent.extra.TEXT", str2);
                intent.addFlags(1);
            }
            this.activity.startActivity(intent);
            result.success("success");
        } catch (Exception e2) {
            result.error("ERROR", e2.toString(), null);
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        onAttachedToEngine(flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.activity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.methodChannel.setMethodCallHandler(null);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, @NonNull MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "whatsapp_business_share":
                shareWhatsApp((String) methodCall.argument("url"), (String) methodCall.argument("msg"), result, true);
                break;
            case "facebook_share":
                shareToFacebook((String) methodCall.argument("imagePath"), (String) methodCall.argument("msg"), result);
                break;
            case "mail_share":
                shareToMail((String) methodCall.argument("msg"), (String) methodCall.argument("subject"), (List) methodCall.argument("recipients"), result);
                break;
            case "twitter_share":
                shareToTwitter((String) methodCall.argument("url"), (String) methodCall.argument("msg"), result);
                break;
            case "instagram_share":
                shareInstagramStory((String) methodCall.argument("url"), result);
                break;
            case "system_share":
                shareSystem((String) methodCall.argument("msg"), result);
                break;
            case "sms_share":
                shareToSms((String) methodCall.argument("msg"), result);
                break;
            case "telegram_share":
                shareToTelegram((String) methodCall.argument("msg"), result);
                break;
            case "whatsapp_share":
                shareWhatsApp((String) methodCall.argument("url"), (String) methodCall.argument("msg"), result, false);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
    }

    private void onAttachedToEngine(BinaryMessenger binaryMessenger) {
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "flutter_social_share");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.callbackManager = CallbackManager.Factory.create();
    }
}
