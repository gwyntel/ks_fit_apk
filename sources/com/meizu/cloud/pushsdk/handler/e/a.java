package com.meizu.cloud.pushsdk.handler.e;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.handler.e.j.e;
import com.meizu.cloud.pushsdk.notification.g.b;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a<T> implements com.meizu.cloud.pushsdk.handler.c {

    /* renamed from: a, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.handler.a f19681a;

    /* renamed from: b, reason: collision with root package name */
    private Context f19682b;

    /* renamed from: c, reason: collision with root package name */
    private SparseArray<String> f19683c;

    protected a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.f19682b = context.getApplicationContext();
        this.f19681a = aVar;
        SparseArray<String> sparseArray = new SparseArray<>();
        this.f19683c = sparseArray;
        sparseArray.put(2, "MESSAGE_TYPE_PUSH_SERVICE_V2");
        this.f19683c.put(4, "MESSAGE_TYPE_PUSH_SERVICE_V3");
        this.f19683c.put(16, "MESSAGE_TYPE_REGISTER");
        this.f19683c.put(32, "MESSAGE_TYPE_UNREGISTER");
        this.f19683c.put(8, "MESSAGE_TYPE_THROUGH");
        this.f19683c.put(64, "MESSAGE_TYPE_NOTIFICATION_CLICK");
        this.f19683c.put(128, "MESSAGE_TYPE_NOTIFICATION_DELETE");
        this.f19683c.put(256, "MESSAGE_TYPE_PUSH_SWITCH_STATUS");
        this.f19683c.put(512, "MESSAGE_TYPE_PUSH_REGISTER_STATUS");
        this.f19683c.put(2048, "MESSAGE_TYPE_PUSH_SUBTAGS_STATUS");
        this.f19683c.put(1024, "MESSAGE_TYPE_PUSH_UNREGISTER_STATUS");
        this.f19683c.put(4096, "MESSAGE_TYPE_PUSH_SUBALIAS_STATUS");
        this.f19683c.put(8192, "MESSAGE_TYPE_SCHEDULE_NOTIFICATION");
        this.f19683c.put(16384, "MESSAGE_TYPE_RECEIVE_NOTIFY_MESSAGE");
        this.f19683c.put(32768, "MESSAGE_TYPE_NOTIFICATION_STATE");
        this.f19683c.put(65536, "MESSAGE_TYPE_UPLOAD_FILE_LOG");
        this.f19683c.put(131072, "MESSAGE_TYPE_NOTIFICATION_ARRIVED");
        this.f19683c.put(262144, "MESSAGE_TYPE_NOTIFICATION_WITHDRAW");
        this.f19683c.put(524288, "MESSAGE_TYPE_BRIGHT_NOTIFICATION");
        this.f19683c.put(1048576, "MESSAGE_TYPE_NOTIFICATION_CLOSE");
    }

    private String a(int i2) {
        return this.f19683c.get(i2);
    }

    private String d() {
        String strE = null;
        for (int i2 = 0; i2 < 2; i2++) {
            strE = e();
            if (!TextUtils.isEmpty(strE)) {
                break;
            }
        }
        return strE;
    }

    protected abstract void a(T t2, com.meizu.cloud.pushsdk.notification.c cVar);

    protected com.meizu.cloud.pushsdk.handler.a b() {
        return this.f19681a;
    }

    protected long c(Intent intent) {
        long longExtra = intent.getLongExtra(PushConstants.MZ_PUSH_DELAYED_REPORT_MILLIS, 0L);
        DebugLogger.i("AbstractMessageHandler", "receive push delayedReportMillis from pushservice " + longExtra);
        return longExtra;
    }

    protected com.meizu.cloud.pushsdk.notification.c e(T t2) {
        return null;
    }

    protected int f(T t2) {
        return 0;
    }

    protected abstract T f(Intent intent);

    protected String g(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SERVICE_DEFAULT_PACKAGE_NAME);
        return TextUtils.isEmpty(stringExtra) ? c().getPackageName() : stringExtra;
    }

    protected String h(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_TIMES_TAMP);
        DebugLogger.i("AbstractMessageHandler", "receive push timestamp from pushservice " + stringExtra);
        return TextUtils.isEmpty(stringExtra) ? String.valueOf(System.currentTimeMillis() / 1000) : stringExtra;
    }

    protected String i(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
    }

    protected String j(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
    }

    protected boolean k(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(PushConstants.MZ_PUSH_WHITE_LIST, false);
        DebugLogger.i("AbstractMessageHandler", "receive push whiteList from pushservice " + booleanExtra);
        return booleanExtra;
    }

    public String a(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("launcher");
            if (jSONObject.has("pkg") && !TextUtils.isEmpty(jSONObject.getString("pkg"))) {
                return jSONObject.getString("pkg");
            }
        } catch (Exception unused) {
            DebugLogger.e("AbstractMessageHandler", "parse desk top json error");
        }
        return "";
    }

    protected void b(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.notification.model.a aVarB = com.meizu.cloud.pushsdk.notification.model.a.b(messageV3);
        if (aVarB != null) {
            DebugLogger.i("AbstractMessageHandler", "delete notifyKey " + aVarB.b() + " notifyId " + aVarB.a());
            if (TextUtils.isEmpty(aVarB.b())) {
                b.b(c(), messageV3.getUploadDataPackageName(), aVarB.a());
            } else {
                b.a(c(), messageV3.getUploadDataPackageName(), aVarB.b());
            }
        }
    }

    protected Context c() {
        return this.f19682b;
    }

    protected String d(Intent intent) {
        String stringExtra = intent != null ? intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY) : null;
        if (!TextUtils.isEmpty(stringExtra)) {
            return stringExtra;
        }
        String strA = com.meizu.cloud.pushsdk.d.c.a(c());
        DebugLogger.e("AbstractMessageHandler", "force get deviceId " + strA);
        return strA;
    }

    protected String e() {
        return new e.a((String) com.meizu.cloud.pushsdk.e.a.a(PushConstants.GET_PUBLIC_KEY).a().d().b()).a();
    }

    protected void g(T t2) {
    }

    protected void a(Context context, MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.handler.e.b.a aVarA;
        com.meizu.cloud.pushsdk.notification.model.a aVarB;
        if (messageV3.getAdvertisementOption() == null || TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage()) || (aVarA = com.meizu.cloud.pushsdk.b.a(context).a()) == null || (aVarB = com.meizu.cloud.pushsdk.notification.model.a.b(messageV3)) == null) {
            return;
        }
        aVarA.a(aVarB.a());
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00aa A[ADDED_TO_REGION] */
    @Override // com.meizu.cloud.pushsdk.handler.c
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean b(android.content.Intent r8) {
        /*
            r7 = this;
            boolean r0 = r7.a(r8)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "current message Type "
            r0.append(r2)
            int r2 = r7.a()
            java.lang.String r2 = r7.a(r2)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "AbstractMessageHandler"
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r0)
            java.lang.Object r0 = r7.f(r8)
            java.lang.String r8 = r7.e(r8)
            boolean r8 = r7.a(r0, r8)
            if (r8 != 0) goto L3a
            java.lang.String r8 = "invalid push message"
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r8)
            return r1
        L3a:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r3 = "current Handler message "
            r8.append(r3)
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r8)
            r7.d(r0)
            int r8 = r7.f(r0)
            r3 = 1
            if (r8 == 0) goto L8d
            if (r8 == r3) goto L8a
            r4 = 2
            if (r8 == r4) goto L83
            r4 = 3
            if (r8 == r4) goto L7a
            r4 = 4
            if (r8 == r4) goto L6e
            r3 = 5
            if (r8 == r3) goto L68
        L66:
            r3 = r1
            goto L90
        L68:
            java.lang.String r8 = "ad cannot show message"
        L6a:
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r8)
            goto L66
        L6e:
            java.lang.String r8 = "bright notification"
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r8)
            r7.a(r0)
        L76:
            r6 = r3
            r3 = r1
            r1 = r6
            goto L90
        L7a:
            java.lang.String r8 = "schedule notification"
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r8)
            r7.g(r0)
            goto L76
        L83:
            java.lang.String r8 = "notification on time ,show message"
        L85:
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r8)
            r1 = r3
            goto L90
        L8a:
            java.lang.String r8 = "expire notification, don't show message"
            goto L6a
        L8d:
            java.lang.String r8 = "schedule send message off, send message directly"
            goto L85
        L90:
            boolean r8 = r7.b(r0)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "can send message "
            r4.append(r5)
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r4)
            if (r1 == 0) goto Lbd
            if (r3 == 0) goto Lbd
            if (r8 == 0) goto Lbd
            com.meizu.cloud.pushsdk.notification.c r8 = r7.e(r0)
            r7.a(r0, r8)
            r7.c(r0)
            java.lang.String r8 = "send message end "
            com.meizu.cloud.pushinternal.DebugLogger.i(r2, r8)
        Lbd:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.handler.e.a.b(android.content.Intent):boolean");
    }

    protected void c(MessageV3 messageV3) {
        if (messageV3 == null || messageV3.getAdvertisementOption() == null || TextUtils.isEmpty(messageV3.getAdvertisementOption().getAdPackage())) {
            if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
                b().b(c(), MzPushMessage.fromMessageV3(messageV3));
                return;
            }
            if (a(c(), messageV3.getUploadDataPackageName())) {
                DebugLogger.i("AbstractMessageHandler", "send notification arrived message to " + messageV3.getUploadDataPackageName());
                Intent intent = new Intent();
                if (MinSdkChecker.isSupportTransmitMessageValue(this.f19682b, messageV3.getUploadDataPackageName())) {
                    intent.putExtra(PushConstants.MZ_MESSAGE_VALUE, com.meizu.cloud.pushsdk.handler.d.a(messageV3));
                } else {
                    intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
                }
                intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_ARRIVED);
                MzSystemUtils.sendMessageFromBroadcast(c(), intent, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getUploadDataPackageName());
            }
        }
    }

    protected void d(T t2) {
    }

    protected String e(Intent intent) {
        return intent.getStringExtra("method");
    }

    protected void a(MessageV3 messageV3) {
        if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            b(messageV3);
            return;
        }
        com.meizu.cloud.pushsdk.notification.model.a aVarB = com.meizu.cloud.pushsdk.notification.model.a.b(messageV3);
        if (aVarB != null) {
            DebugLogger.e("AbstractMessageHandler", "delete notifyId " + aVarB.a() + " notifyKey " + aVarB.b());
            if (TextUtils.isEmpty(aVarB.b())) {
                com.meizu.cloud.pushsdk.platform.c.b.a(c()).a(messageV3.getUploadDataPackageName(), aVarB.a());
            } else {
                com.meizu.cloud.pushsdk.platform.c.b.a(c()).a(messageV3.getUploadDataPackageName(), aVarB.b());
            }
        }
    }

    protected boolean b(T t2) {
        return true;
    }

    protected void c(T t2) {
    }

    protected void a(T t2) {
    }

    protected boolean b(String str) {
        try {
            return c().getPackageName().equals(new JSONObject(str).getString("appId"));
        } catch (Exception unused) {
            DebugLogger.e("AbstractMessageHandler", "parse notification error");
            return false;
        }
    }

    private boolean b(String str, MessageV3 messageV3, String str2) {
        String str3;
        if (!TextUtils.isEmpty(str)) {
            str3 = "sa, public key not empty";
        } else if (!PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE.equals(str2)) {
            str3 = "sa, message not click method";
        } else {
            if (com.meizu.cloud.pushsdk.util.b.d(c(), messageV3.getPackageName())) {
                com.meizu.cloud.pushsdk.util.b.a(c(), messageV3.getPackageName(), false);
                return true;
            }
            str3 = "sa, not first request";
        }
        DebugLogger.i("AbstractMessageHandler", str3);
        return false;
    }

    protected boolean a(int i2, String str) {
        boolean zK;
        if (i2 == 0) {
            zK = com.meizu.cloud.pushsdk.util.b.f(c(), str);
        } else {
            zK = true;
            if (i2 == 1) {
                zK = com.meizu.cloud.pushsdk.util.b.k(c(), str);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(i2 == 0 ? " canNotificationMessage " : " canThroughMessage ");
        sb.append(zK);
        DebugLogger.i("AbstractMessageHandler", sb.toString());
        return zK;
    }

    public static boolean a(Context context, String str) {
        try {
            return ((Boolean) Class.forName("com.meizu.cloud.utils.ProcessUtils").getDeclaredMethod("isRunningProcess", Context.class, String.class).invoke(null, context, str)).booleanValue();
        } catch (Exception e2) {
            DebugLogger.e("AbstractMessageHandler", "getDeviceId error " + e2.getMessage());
            return true;
        }
    }

    protected final boolean a(MessageV3 messageV3, String str) throws JSONException {
        String strA = com.meizu.cloud.pushsdk.handler.e.j.e.a(messageV3);
        if (TextUtils.isEmpty(strA)) {
            DebugLogger.i("AbstractMessageHandler", "message does not contain signature field");
            return false;
        }
        String strG = com.meizu.cloud.pushsdk.util.b.g(c(), messageV3.getPackageName());
        DebugLogger.i("AbstractMessageHandler", "local public key is: " + strG);
        if (b(strG, messageV3, str)) {
            DebugLogger.i("AbstractMessageHandler", "message special approval no check");
            return true;
        }
        if (a(strG, messageV3, strA)) {
            DebugLogger.i("AbstractMessageHandler", "security check passed");
            return true;
        }
        String strD = d();
        DebugLogger.i("AbstractMessageHandler", "network request public key: " + strD);
        if (!a(strD, messageV3, strA)) {
            DebugLogger.e("AbstractMessageHandler", "security check fail");
            return false;
        }
        com.meizu.cloud.pushsdk.util.b.f(c(), messageV3.getPackageName(), strD);
        DebugLogger.i("AbstractMessageHandler", "security check passed");
        return true;
    }

    protected boolean a(T t2, String str) {
        return true;
    }

    private boolean a(String str, MessageV3 messageV3, String str2) {
        if (TextUtils.isEmpty(str)) {
            DebugLogger.e("AbstractMessageHandler", "security check fail, public key is null");
            return false;
        }
        String strA = com.meizu.cloud.pushsdk.util.c.a(str, str2);
        DebugLogger.i("AbstractMessageHandler", "decrypt sign: " + strA);
        boolean zA = com.meizu.cloud.pushsdk.handler.e.j.e.a(strA, messageV3);
        DebugLogger.i("AbstractMessageHandler", "check public key result: " + zA);
        return zA;
    }
}
