package me.andisemler.nfc_in_flutter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.media3.common.C;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.yc.utesdk.ble.close.KeyType;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/* loaded from: classes5.dex */
public class NfcInFlutterPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler, PluginRegistry.NewIntentListener, NfcAdapter.ReaderCallback, ActivityAware {
    private static final String DISPATCH_READER_MODE = "dispatch";
    private static final String LOG_TAG = "NfcInFlutterPlugin";
    private static final String NORMAL_READER_MODE = "normal";
    private Activity activity;
    private NfcAdapter adapter;

    /* renamed from: e, reason: collision with root package name */
    MethodChannel f26026e;
    private EventChannel.EventSink events;

    /* renamed from: f, reason: collision with root package name */
    EventChannel f26027f;
    private final int DEFAULT_READER_FLAGS = 15;
    private String currentReaderMode = null;
    private Tag lastTag = null;

    /* renamed from: a, reason: collision with root package name */
    boolean f26022a = false;

    /* renamed from: b, reason: collision with root package name */
    boolean f26023b = false;

    /* renamed from: c, reason: collision with root package name */
    Handler f26024c = new Handler();

    /* renamed from: d, reason: collision with root package name */
    Runnable f26025d = new Runnable() { // from class: me.andisemler.nfc_in_flutter.NfcInFlutterPlugin.1
        @Override // java.lang.Runnable
        public void run() throws IOException {
            if (NfcInFlutterPlugin.this.events != null) {
                NfcInFlutterPlugin nfcInFlutterPlugin = NfcInFlutterPlugin.this;
                nfcInFlutterPlugin.f26022a = true;
                if (nfcInFlutterPlugin.activity.getIntent().getAction() == null) {
                    Log.e(NfcInFlutterPlugin.LOG_TAG, NfcInFlutterPlugin.this.activity + "");
                } else if ("android.nfc.action.NDEF_DISCOVERED".equals(NfcInFlutterPlugin.this.activity.getIntent().getAction())) {
                    Tag tag = (Tag) NfcInFlutterPlugin.this.activity.getIntent().getParcelableExtra("android.nfc.extra.TAG");
                    NfcInFlutterPlugin.this.lastTag = tag;
                    NfcInFlutterPlugin.this.handleNDEFTagFromIntent(tag);
                }
            }
            NfcInFlutterPlugin nfcInFlutterPlugin2 = NfcInFlutterPlugin.this;
            if (nfcInFlutterPlugin2.f26022a) {
                nfcInFlutterPlugin2.f26024c.removeCallbacks(nfcInFlutterPlugin2.f26025d);
            } else {
                nfcInFlutterPlugin2.f26024c.postDelayed(nfcInFlutterPlugin2.f26025d, 1000L);
            }
        }
    };

    private static class FormatRequest {

        /* renamed from: a, reason: collision with root package name */
        final NdefFormatable f26035a;

        /* renamed from: b, reason: collision with root package name */
        final NdefMessage f26036b;

        FormatRequest(NdefFormatable ndefFormatable, NdefMessage ndefMessage) {
            this.f26035a = ndefFormatable;
            this.f26036b = ndefMessage;
        }
    }

    private static class FormatTask extends AsyncTask<FormatRequest, Void, NfcInFlutterException> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public NfcInFlutterException doInBackground(FormatRequest... formatRequestArr) throws IOException, FormatException {
            for (FormatRequest formatRequest : formatRequestArr) {
                try {
                    formatRequest.f26035a.connect();
                    formatRequest.f26035a.format(formatRequest.f26036b);
                    formatRequest.f26035a.close();
                } catch (FormatException e2) {
                    return new NfcInFlutterException("NDEFBadFormatError", e2.getMessage(), null);
                } catch (IOException e3) {
                    return new NfcInFlutterException("IOError", e3.getMessage(), null);
                }
            }
            return null;
        }

        private FormatTask() {
        }
    }

    private void eventError(final String str, final String str2, final Object obj) {
        new Handler(this.activity.getMainLooper()).post(new Runnable() { // from class: me.andisemler.nfc_in_flutter.NfcInFlutterPlugin.3
            @Override // java.lang.Runnable
            public void run() {
                if (NfcInFlutterPlugin.this.events != null) {
                    NfcInFlutterPlugin.this.events.error(str, str2, obj);
                }
            }
        });
    }

    private void eventSuccess(final Object obj) {
        new Handler(this.activity.getMainLooper()).post(new Runnable() { // from class: me.andisemler.nfc_in_flutter.NfcInFlutterPlugin.2
            @Override // java.lang.Runnable
            public void run() {
                if (NfcInFlutterPlugin.this.events != null) {
                    NfcInFlutterPlugin.this.events.success(obj);
                }
            }
        });
    }

    private Map<String, Object> formatEmptyNDEFMessage(Ndef ndef) {
        Map<String, Object> emptyWritableNDEFMessage = formatEmptyWritableNDEFMessage();
        emptyWritableNDEFMessage.put("id", getNDEFTagID(ndef));
        emptyWritableNDEFMessage.put("writable", Boolean.valueOf(ndef.isWritable()));
        return emptyWritableNDEFMessage;
    }

    private Map<String, Object> formatEmptyWritableNDEFMessage() {
        HashMap map = new HashMap();
        map.put("id", "");
        map.put("message_type", "ndef");
        map.put("type", "");
        map.put("writable", Boolean.TRUE);
        ArrayList arrayList = new ArrayList();
        HashMap map2 = new HashMap();
        map2.put("tnf", "empty");
        map2.put("id", "");
        map2.put("type", "");
        map2.put(MessengerShareContentUtility.ATTACHMENT_PAYLOAD, "");
        map2.put("data", "");
        map2.put("languageCode", "");
        arrayList.add(map2);
        map.put("records", arrayList);
        return map;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0122 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.nfc.NdefMessage formatMapToNDEFMessage(java.util.Map r20) throws java.lang.IllegalArgumentException {
        /*
            Method dump skipped, instructions count: 378
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: me.andisemler.nfc_in_flutter.NfcInFlutterPlugin.formatMapToNDEFMessage(java.util.Map):android.nfc.NdefMessage");
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x017c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.Map<java.lang.String, java.lang.Object> formatNDEFMessageToResult(android.nfc.tech.Ndef r21, android.nfc.NdefMessage r22) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: me.andisemler.nfc_in_flutter.NfcInFlutterPlugin.formatNDEFMessageToResult(android.nfc.tech.Ndef, android.nfc.NdefMessage):java.util.Map");
    }

    private String getNDEFTagID(Ndef ndef) {
        byte[] id = ndef.getTag().getId();
        return String.format("%0" + (id.length * 2) + "X", new BigInteger(1, id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNDEFTagFromIntent(Tag tag) throws IOException {
        Map<String, Object> emptyWritableNDEFMessage;
        Ndef ndef = Ndef.get(tag);
        NdefFormatable ndefFormatable = NdefFormatable.get(tag);
        if (ndef != null) {
            NdefMessage cachedNdefMessage = ndef.getCachedNdefMessage();
            if (Build.VERSION.SDK_INT <= 33) {
                try {
                    ndef.close();
                } catch (IOException e2) {
                    Log.e(LOG_TAG, "close NDEF tag error: " + e2.getMessage());
                }
            }
            emptyWritableNDEFMessage = formatNDEFMessageToResult(ndef, cachedNdefMessage);
        } else if (ndefFormatable == null) {
            return;
        } else {
            emptyWritableNDEFMessage = formatEmptyWritableNDEFMessage();
        }
        eventSuccess(emptyWritableNDEFMessage);
    }

    private Boolean nfcIsEnabled() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.activity);
        return defaultAdapter == null ? Boolean.FALSE : Boolean.valueOf(defaultAdapter.isEnabled());
    }

    private void startReading(boolean z2) {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.activity);
        this.adapter = defaultAdapter;
        if (defaultAdapter == null) {
            return;
        }
        this.adapter.enableReaderMode(this.activity, this, z2 ? KeyType.QUERY_HEART_RATE_PARAM : 15, new Bundle());
    }

    private void startReadingWithForegroundDispatch() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.activity);
        this.adapter = defaultAdapter;
        if (defaultAdapter == null) {
            return;
        }
        Intent intent = new Intent(this.activity.getApplicationContext(), this.activity.getClass());
        intent.setFlags(C.BUFFER_FLAG_LAST_SAMPLE);
        this.adapter.enableForegroundDispatch(this.activity, PendingIntent.getActivity(this.activity.getApplicationContext(), 0, intent, AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL), null, new String[0][]);
    }

    private void writeNDEF(NdefMessage ndefMessage, MethodChannel.Result result) throws ExecutionException, InterruptedException, IOException, NfcInFlutterException {
        Ndef ndef = Ndef.get(this.lastTag);
        NdefFormatable ndefFormatable = NdefFormatable.get(this.lastTag);
        Log.e(LOG_TAG, ndefMessage.getByteArrayLength() + "");
        if (this.f26023b) {
            try {
                Thread.sleep(1000L);
                this.f26023b = false;
                Log.e(LOG_TAG, this.f26023b + "");
                return;
            } catch (InterruptedException unused) {
                Log.e(LOG_TAG, "InterruptedException");
                return;
            }
        }
        try {
            if (ndef == null) {
                if (ndefFormatable == null) {
                    throw new NfcInFlutterException("NDEFUnsupported", "tag doesn't support NDEF", null);
                }
                try {
                    NfcInFlutterException nfcInFlutterException = new FormatTask().execute(new FormatRequest(ndefFormatable, ndefMessage)).get();
                    if (nfcInFlutterException == null) {
                        return;
                    } else {
                        throw nfcInFlutterException;
                    }
                } catch (InterruptedException e2) {
                    throw new NfcInFlutterException("InterruptedException", e2.getMessage(), null);
                } catch (ExecutionException e3) {
                    throw new NfcInFlutterException("ExecutionError", e3.getMessage(), null);
                }
            }
            try {
                ndef.connect();
                if (ndef.getMaxSize() < ndefMessage.getByteArrayLength()) {
                    HashMap map = new HashMap();
                    map.put("maxSize", Integer.valueOf(ndef.getMaxSize()));
                    Log.e(LOG_TAG, ndef.getMaxSize() + "");
                    throw new NfcInFlutterException("NFCTagSizeTooSmallError", "message is too large for this tag", map);
                }
                try {
                    ndef.writeNdefMessage(ndefMessage);
                    this.f26023b = true;
                    Log.e(LOG_TAG, "写入成功");
                    result.success(null);
                    try {
                        ndef.close();
                        Log.e(LOG_TAG, "finally");
                    } catch (IOException e4) {
                        Log.e(LOG_TAG, "close NDEF tag error: " + e4.getMessage());
                    }
                } catch (FormatException e5) {
                    Log.e(LOG_TAG, e5.getMessage() + "FormatException");
                    throw new NfcInFlutterException("NDEFBadFormatError", e5.getMessage(), null);
                } catch (IOException e6) {
                    Log.e(LOG_TAG, e6.getMessage() + "IOException2");
                    throw new NfcInFlutterException("IOError", "write to tag error: " + e6.getMessage(), null);
                }
            } catch (IOException e7) {
                Log.e(LOG_TAG, e7.getMessage() + "NfcInFlutterException");
                throw new NfcInFlutterException("IOError", e7.getMessage(), null);
            }
        } catch (Throwable th) {
            try {
                ndef.close();
                Log.e(LOG_TAG, "finally");
            } catch (IOException e8) {
                Log.e(LOG_TAG, "close NDEF tag error: " + e8.getMessage());
            }
            throw th;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        this.activity = activityPluginBinding.getActivity();
        this.f26026e.setMethodCallHandler(this);
        this.f26027f.setStreamHandler(this);
        this.f26024c.postDelayed(this.f26025d, 1000L);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.f26026e = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "nfc_in_flutter");
        this.f26027f = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "nfc_in_flutter/tags");
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        String str = this.currentReaderMode;
        str.hashCode();
        if (str.equals(NORMAL_READER_MODE)) {
            this.adapter.disableReaderMode(this.activity);
        } else if (str.equals(DISPATCH_READER_MODE)) {
            this.adapter.disableForegroundDispatch(this.activity);
        } else {
            Log.e(LOG_TAG, "unknown reader mode: " + this.currentReaderMode);
        }
        this.events = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.events = eventSink;
        Log.e("events", "eventSink");
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) throws ExecutionException, InterruptedException, IOException, IllegalArgumentException {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "startNDEFReading":
                Object obj = methodCall.arguments;
                if (!(obj instanceof HashMap)) {
                    result.error("MissingArguments", "startNDEFReading was called with no arguments", "");
                    break;
                } else {
                    HashMap map = (HashMap) obj;
                    String str2 = (String) map.get("reader_mode");
                    if (str2 != null) {
                        String str3 = this.currentReaderMode;
                        if (str3 != null && !str2.equals(str3)) {
                            result.error("NFCMultipleReaderModes", "multiple reader modes", "");
                            break;
                        } else {
                            this.currentReaderMode = str2;
                            if (str2.equals(NORMAL_READER_MODE)) {
                                startReading(((Boolean) map.get("no_platform_sounds")).booleanValue());
                            } else if (!str2.equals(DISPATCH_READER_MODE)) {
                                result.error("NFCUnknownReaderMode", "unknown reader mode: " + str2, "");
                                break;
                            } else {
                                startReadingWithForegroundDispatch();
                            }
                            result.success(null);
                            break;
                        }
                    } else {
                        result.error("MissingReaderMode", "startNDEFReading was called without a reader mode", "");
                        break;
                    }
                }
                break;
            case "writeNDEF":
                HashMap map2 = (HashMap) methodCall.arguments();
                if (map2 == null) {
                    result.error("NFCMissingArguments", "missing arguments", null);
                    break;
                } else {
                    try {
                        Map map3 = (Map) map2.get("message");
                        boolean zBooleanValue = ((Boolean) map2.get("isUserWrite")).booleanValue();
                        if (map3 == null) {
                            result.error("NFCMissingNDEFMessage", "a ndef message was not given", null);
                        } else {
                            NdefMessage mapToNDEFMessage = formatMapToNDEFMessage(map3);
                            Log.e(LOG_TAG, "writeNDEFwriteNDEF");
                            if (zBooleanValue) {
                                writeNDEF(mapToNDEFMessage, result);
                            }
                        }
                        break;
                    } catch (NfcInFlutterException e2) {
                        result.error(e2.code, e2.message, e2.details);
                        return;
                    }
                }
            case "readNDEFSupported":
                result.success(nfcIsEnabled());
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override // io.flutter.plugin.common.PluginRegistry.NewIntentListener
    public boolean onNewIntent(Intent intent) throws IOException {
        String action = intent.getAction();
        Log.e(LOG_TAG, "=========onNewIntent: " + action);
        if (!"android.nfc.action.NDEF_DISCOVERED".equals(action)) {
            return false;
        }
        Tag tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
        this.lastTag = tag;
        handleNDEFTagFromIntent(tag);
        return true;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
    }

    @Override // android.nfc.NfcAdapter.ReaderCallback
    public void onTagDiscovered(Tag tag) throws IOException {
        StringBuilder sb;
        this.lastTag = tag;
        Ndef ndef = Ndef.get(tag);
        NdefFormatable ndefFormatable = NdefFormatable.get(tag);
        if (ndef == null) {
            if (ndefFormatable != null) {
                eventSuccess(formatEmptyWritableNDEFMessage());
                return;
            }
            return;
        }
        boolean z2 = false;
        try {
            try {
                try {
                    ndef.connect();
                    NdefMessage ndefMessage = ndef.getNdefMessage();
                    if (ndefMessage == null) {
                        eventSuccess(formatEmptyNDEFMessage(ndef));
                        try {
                            ndef.close();
                            return;
                        } catch (IOException e2) {
                            Log.e(LOG_TAG, "close NDEF tag error: " + e2.getMessage());
                            return;
                        }
                    }
                    try {
                        ndef.close();
                        z2 = true;
                    } catch (IOException e3) {
                        Log.e(LOG_TAG, "close NDEF tag error: " + e3.getMessage());
                    }
                    eventSuccess(formatNDEFMessageToResult(ndef, ndefMessage));
                    if (z2) {
                        return;
                    }
                    try {
                        ndef.close();
                    } catch (IOException e4) {
                        e = e4;
                        sb = new StringBuilder();
                        sb.append("close NDEF tag error: ");
                        sb.append(e.getMessage());
                        Log.e(LOG_TAG, sb.toString());
                    }
                } catch (IOException e5) {
                    HashMap map = new HashMap();
                    map.put("fatal", Boolean.TRUE);
                    eventError("IOError", e5.getMessage(), map);
                    if (0 == 0) {
                        try {
                            ndef.close();
                        } catch (IOException e6) {
                            e = e6;
                            sb = new StringBuilder();
                            sb.append("close NDEF tag error: ");
                            sb.append(e.getMessage());
                            Log.e(LOG_TAG, sb.toString());
                        }
                    }
                }
            } catch (FormatException e7) {
                eventError("NDEFBadFormatError", e7.getMessage(), null);
                if (0 == 0) {
                    try {
                        ndef.close();
                    } catch (IOException e8) {
                        e = e8;
                        sb = new StringBuilder();
                        sb.append("close NDEF tag error: ");
                        sb.append(e.getMessage());
                        Log.e(LOG_TAG, sb.toString());
                    }
                }
            }
        } catch (Throwable th) {
            if (0 == 0) {
                try {
                    ndef.close();
                } catch (IOException e9) {
                    Log.e(LOG_TAG, "close NDEF tag error: " + e9.getMessage());
                }
            }
            throw th;
        }
    }
}
