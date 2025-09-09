package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import androidx.media3.common.C;
import androidx.media3.extractor.text.ttml.TtmlNode;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.xiaomi.push.fo;
import com.xiaomi.push.fp;
import com.xiaomi.push.fq;
import com.xiaomi.push.fr;
import com.xiaomi.push.g;
import com.xiaomi.push.in;
import com.xiaomi.push.ja;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.av;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.message.MessageService;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class al {

    /* renamed from: a, reason: collision with root package name */
    public static long f24459a;

    /* renamed from: a, reason: collision with other field name */
    private static volatile ar f1000a;

    /* renamed from: a, reason: collision with other field name */
    private static final LinkedList<Pair<Integer, jj>> f1001a = new LinkedList<>();

    /* renamed from: a, reason: collision with other field name */
    private static ExecutorService f1002a = Executors.newCachedThreadPool();

    private static class a implements Callable<Bitmap> {

        /* renamed from: a, reason: collision with root package name */
        private Context f24460a;

        /* renamed from: a, reason: collision with other field name */
        private String f1003a;

        /* renamed from: a, reason: collision with other field name */
        private boolean f1004a;

        public a(String str, Context context, boolean z2) {
            this.f24460a = context;
            this.f1003a = str;
            this.f1004a = z2;
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Bitmap call() throws Throwable {
            if (TextUtils.isEmpty(this.f1003a)) {
                com.xiaomi.channel.commonutils.logger.b.m91a("Failed get online picture/icon resource cause picUrl is empty");
                return null;
            }
            if (this.f1003a.startsWith("http")) {
                av.b bVarA = av.a(this.f24460a, this.f1003a, this.f1004a);
                if (bVarA != null) {
                    return bVarA.f1018a;
                }
                com.xiaomi.channel.commonutils.logger.b.m91a("Failed get online picture/icon resource");
                return null;
            }
            Bitmap bitmapA = av.a(this.f24460a, this.f1003a);
            if (bitmapA != null) {
                return bitmapA;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("Failed get online picture/icon resource");
            return bitmapA;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        long f24461a = 0;

        /* renamed from: a, reason: collision with other field name */
        Notification f1005a;
    }

    public static class c {

        /* renamed from: a, reason: collision with other field name */
        public String f1006a;

        /* renamed from: a, reason: collision with root package name */
        public long f24462a = 0;

        /* renamed from: a, reason: collision with other field name */
        public boolean f1007a = false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m725a(Context context, String str) {
        return com.xiaomi.push.g.m427b(context, str);
    }

    /* renamed from: b, reason: collision with other method in class */
    private static boolean m732b(Map<String, String> map) {
        if (map != null) {
            return "6".equals(map.get("notification_style_type"));
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("meta extra is null");
        return false;
    }

    static void c(Context context, String str) {
        context.getSharedPreferences("pref_notify_type", 0).edit().remove(str).commit();
    }

    public static boolean d(jj jjVar) {
        return jjVar.a() == in.Registration;
    }

    public static boolean e(jj jjVar) {
        return m727a(jjVar) || c(jjVar) || m731b(jjVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m726a(Context context, String str, boolean z2) {
        return com.xiaomi.push.j.m549a() && !z2 && m725a(context, str);
    }

    public static boolean c(jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        return a(jaVarM593a) && jaVarM593a.f658b == 0 && !m727a(jjVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static c m723a(Context context, jj jjVar, byte[] bArr) {
        int iC;
        Map<String, String> mapM560a;
        int i2;
        c cVar = new c();
        g.b bVarA = com.xiaomi.push.g.a(context, a(jjVar), true);
        ja jaVarM593a = jjVar.m593a();
        if (jaVarM593a != null) {
            iC = jaVarM593a.c();
            mapM560a = jaVarM593a.m560a();
        } else {
            iC = 0;
            mapM560a = null;
        }
        int iB = com.xiaomi.push.s.b(a(jjVar), iC);
        if (com.xiaomi.push.j.m550a(context) && bVarA == g.b.NOT_ALLOWED) {
            if (jaVarM593a != null) {
                fo.a(context.getApplicationContext()).a(jjVar.b(), b(jjVar), jaVarM593a.m559a(), "10:" + a(jjVar));
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("Do not notify because user block " + a(jjVar) + "‘s notification");
            return cVar;
        }
        if (com.xiaomi.push.j.m550a(context) && f1000a != null && f1000a.m734a(context, iB, a(jjVar), mapM560a)) {
            if (jaVarM593a != null) {
                fo.a(context.getApplicationContext()).a(jjVar.b(), b(jjVar), jaVarM593a.m559a(), "14:" + a(jjVar));
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("Do not notify because card notification is canceled or sequence incorrect");
            return cVar;
        }
        RemoteViews remoteViewsA = a(context, jjVar, bArr);
        PendingIntent pendingIntentA = a(context, jjVar, jjVar.b(), bArr, iB);
        if (pendingIntentA == null) {
            if (jaVarM593a != null) {
                fo.a(context.getApplicationContext()).a(jjVar.b(), b(jjVar), jaVarM593a.m559a(), AgooConstants.ACK_BODY_NULL);
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("The click PendingIntent is null. ");
            return cVar;
        }
        b bVarA2 = a(context, jjVar, bArr, remoteViewsA, pendingIntentA, iB);
        cVar.f24462a = bVarA2.f24461a;
        cVar.f1006a = a(jjVar);
        Notification notification = bVarA2.f1005a;
        if (com.xiaomi.push.j.m549a()) {
            if (!TextUtils.isEmpty(jaVarM593a.m559a())) {
                notification.extras.putString(Constants.MessagePayloadKeys.MSGID_SERVER, jaVarM593a.m559a());
            }
            notification.extras.putString("local_paid", jjVar.m594a());
            ax.a(mapM560a, notification.extras, "msg_busi_type");
            ax.a(mapM560a, notification.extras, "disable_notification_flags");
            String str = jaVarM593a.m565b() == null ? null : jaVarM593a.m565b().get("score_info");
            if (!TextUtils.isEmpty(str)) {
                notification.extras.putString("score_info", str);
            }
            notification.extras.putString("pushUid", a(jaVarM593a.f656a, "n_stats_expose"));
            if (c(jjVar)) {
                i2 = 1000;
            } else {
                i2 = m727a(jjVar) ? 3000 : -1;
            }
            notification.extras.putString("eventMessageType", String.valueOf(i2));
            notification.extras.putString(HiAnalyticsConstant.BI_KEY_TARGET_PACKAGE, a(jjVar));
        }
        String str2 = jaVarM593a.m560a() != null ? jaVarM593a.m560a().get("message_count") : null;
        if (com.xiaomi.push.j.m549a() && str2 != null) {
            try {
                ax.a(notification, Integer.parseInt(str2));
            } catch (NumberFormatException e2) {
                fo.a(context.getApplicationContext()).b(jjVar.b(), b(jjVar), jaVarM593a.m559a(), MessageService.MSG_ACCS_NOTIFY_CLICK);
                com.xiaomi.channel.commonutils.logger.b.d("fail to set message count. " + e2);
            }
        }
        String strA = a(jjVar);
        ax.m750a(notification, strA);
        aw awVarA = aw.a(context, strA);
        if (com.xiaomi.push.j.m550a(context) && f1000a != null) {
            f1000a.a(jjVar, jaVarM593a.m560a(), iB, notification);
        }
        if (com.xiaomi.push.j.m550a(context) && f1000a != null && f1000a.a(jaVarM593a.m560a(), iB, notification)) {
            com.xiaomi.channel.commonutils.logger.b.b("consume this notificaiton by agent");
        } else {
            awVarA.a(iB, notification);
            cVar.f1007a = true;
            com.xiaomi.channel.commonutils.logger.b.m91a("notification: " + jaVarM593a.m559a() + " is notifyied");
        }
        if (com.xiaomi.push.j.m549a() && com.xiaomi.push.j.m550a(context)) {
            at.a().a(context, iB, notification);
            cd.m775a(context, strA, iB, jaVarM593a.m559a(), notification);
        }
        if (m727a(jjVar)) {
            fo.a(context.getApplicationContext()).a(jjVar.b(), b(jjVar), jaVarM593a.m559a(), 3002, null);
        }
        if (c(jjVar)) {
            fo.a(context.getApplicationContext()).a(jjVar.b(), b(jjVar), jaVarM593a.m559a(), 1002, null);
        }
        if (Build.VERSION.SDK_INT < 26) {
            String strM559a = jaVarM593a.m559a();
            com.xiaomi.push.ah ahVarA = com.xiaomi.push.ah.a(context);
            int iA = a(jaVarM593a.m560a());
            if (iA > 0 && !TextUtils.isEmpty(strM559a)) {
                String str3 = "n_timeout_" + strM559a;
                ahVarA.m174a(str3);
                ahVarA.b(new am(str3, awVarA, iB), iA);
            }
        }
        Pair<Integer, jj> pair = new Pair<>(Integer.valueOf(iB), jjVar);
        LinkedList<Pair<Integer, jj>> linkedList = f1001a;
        synchronized (linkedList) {
            try {
                linkedList.add(pair);
                if (linkedList.size() > 100) {
                    linkedList.remove();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cVar;
    }

    private static int b(Context context, String str) {
        int iA = a(context, str, "mipush_notification");
        int iA2 = a(context, str, "mipush_small_notification");
        if (iA <= 0) {
            iA = iA2 > 0 ? iA2 : context.getApplicationInfo().icon;
        }
        return iA == 0 ? context.getApplicationInfo().logo : iA;
    }

    private static int c(Map<String, String> map) {
        if (map == null) {
            return 0;
        }
        String str = map.get("notification_priority");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            com.xiaomi.channel.commonutils.logger.b.c("priority=" + str);
            return Integer.parseInt(str);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("parsing notification priority error: " + e2);
            return 0;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public static void m729b(Context context, String str) {
        if (!com.xiaomi.push.j.m550a(context) || f1000a == null || TextUtils.isEmpty(str)) {
            return;
        }
        f1000a.a(str);
    }

    /* renamed from: b, reason: collision with other method in class */
    static boolean m730b(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).contains(str);
    }

    static void b(Context context, String str, int i2) {
        context.getSharedPreferences("pref_notify_type", 0).edit().putInt(str, i2).commit();
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m731b(jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        return a(jaVarM593a) && jaVarM593a.f658b == 1 && !m727a(jjVar);
    }

    public static String b(jj jjVar) {
        if (m727a(jjVar)) {
            return "E100002";
        }
        if (c(jjVar)) {
            return "E100000";
        }
        if (m731b(jjVar)) {
            return "E100001";
        }
        if (d(jjVar)) {
            return "E100003";
        }
        return "";
    }

    private static int b(Map<String, String> map) {
        if (map == null) {
            return 3;
        }
        String str = map.get("channel_importance");
        if (TextUtils.isEmpty(str)) {
            return 3;
        }
        try {
            com.xiaomi.channel.commonutils.logger.b.c("importance=" + str);
            return Integer.parseInt(str);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("parsing channel importance error: " + e2);
            return 3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x014f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.Intent b(android.content.Context r5, java.lang.String r6, java.util.Map<java.lang.String, java.lang.String> r7, int r8) throws java.net.URISyntaxException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.al.b(android.content.Context, java.lang.String, java.util.Map, int):android.content.Intent");
    }

    private static void b(Context context, String str, fq fqVar, Map<String, String> map) {
        int iA;
        if (!com.xiaomi.push.j.m550a(context)) {
            String strA = a(map, "fcm_icon_uri");
            String strA2 = a(map, "fcm_icon_color");
            if (!TextUtils.isEmpty(strA) && !TextUtils.isEmpty(strA2) && (iA = a(context, str, strA)) > 0) {
                fqVar.setSmallIcon(iA);
                fqVar.mo409a(strA2);
                return;
            }
        }
        fqVar.setSmallIcon(Icon.createWithResource(str, ax.a(context, str)));
    }

    private static PendingIntent a(Context context, jj jjVar, String str, byte[] bArr, int i2) {
        return a(context, jjVar, str, bArr, i2, 0, a(context, jjVar, str));
    }

    private static PendingIntent a(Context context, jj jjVar, String str, byte[] bArr, int i2, int i3, boolean z2) {
        int i4;
        String strM559a;
        Intent intent;
        if (c(jjVar)) {
            i4 = 1000;
        } else {
            i4 = m727a(jjVar) ? 3000 : -1;
        }
        ja jaVarM593a = jjVar.m593a();
        if (jaVarM593a != null) {
            strM559a = jaVarM593a.m559a();
        } else {
            strM559a = "";
        }
        boolean zM727a = m727a(jjVar);
        if (jaVarM593a != null && !TextUtils.isEmpty(jaVarM593a.f665e)) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setData(Uri.parse(jaVarM593a.f665e));
            try {
                String protocol = new URL(jaVarM593a.f665e).getProtocol();
                if (!"http".equals(protocol) && !"https".equals(protocol)) {
                    intent2.setPackage(str);
                } else {
                    ax.a(context, str, intent2);
                }
            } catch (MalformedURLException unused) {
                com.xiaomi.channel.commonutils.logger.b.m91a("meet URL exception : " + jaVarM593a.f665e);
                intent2.setPackage(str);
            }
            intent2.addFlags(268435456);
            intent2.putExtra(TmpConstant.RRESPONSE_MESSAGEID, strM559a);
            intent2.putExtra("eventMessageType", i4);
            if (Build.VERSION.SDK_INT >= 31) {
                return PendingIntent.getActivity(context, 0, intent2, 167772160);
            }
            return PendingIntent.getActivity(context, 0, intent2, C.BUFFER_FLAG_FIRST_SAMPLE);
        }
        if (zM727a) {
            intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaomi.xmsf", "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent.putExtra("mipush_payload", bArr);
            intent.putExtra("mipush_notified", true);
            intent.addCategory(String.valueOf(i2));
            intent.addCategory(String.valueOf(strM559a));
        } else {
            intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
            intent.setComponent(new ComponentName(str, "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent.putExtra("mipush_payload", bArr);
            intent.putExtra("mipush_notified", true);
            intent.addCategory(String.valueOf(i2));
            intent.addCategory(String.valueOf(strM559a));
        }
        intent.putExtra("notification_click_button", i3);
        intent.putExtra(TmpConstant.RRESPONSE_MESSAGEID, strM559a);
        intent.putExtra("eventMessageType", i4);
        if (!zM727a && z2) {
            Intent intent3 = new Intent();
            intent3.setComponent(a(str));
            intent3.addFlags(276824064);
            intent3.putExtra("mipush_serviceIntent", intent);
            intent3.addCategory(String.valueOf(i2));
            intent3.addCategory(String.valueOf(strM559a));
            intent3.addCategory(String.valueOf(i3));
            a(context, intent3, jjVar, jaVarM593a, strM559a, i3);
            if (Build.VERSION.SDK_INT >= 31) {
                return PendingIntent.getActivity(context, 0, intent3, 167772160);
            }
            return PendingIntent.getActivity(context, 0, intent3, C.BUFFER_FLAG_FIRST_SAMPLE);
        }
        a(context, intent, jjVar, jaVarM593a, strM559a, i3);
        if (Build.VERSION.SDK_INT >= 31) {
            return PendingIntent.getService(context, 0, intent, 167772160);
        }
        return PendingIntent.getService(context, 0, intent, C.BUFFER_FLAG_FIRST_SAMPLE);
    }

    private static void a(Context context, Intent intent, jj jjVar, ja jaVar, String str, int i2) {
        if (jjVar == null || jaVar == null || TextUtils.isEmpty(str)) {
            return;
        }
        String strA = a(jaVar.m560a(), i2);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        if (bj.f24517a.equals(strA) || bj.f24518b.equals(strA) || bj.f24519c.equals(strA)) {
            intent.putExtra(TmpConstant.RRESPONSE_MESSAGEID, str);
            intent.putExtra("local_paid", jjVar.f744a);
            if (!TextUtils.isEmpty(jjVar.f748b)) {
                intent.putExtra(HiAnalyticsConstant.BI_KEY_TARGET_PACKAGE, jjVar.f748b);
            }
            intent.putExtra("job_key", a(jaVar.m560a(), "jobkey"));
            intent.putExtra(i2 + OpenAccountUIConstants.UNDER_LINE + "target_component", a(context, jjVar.f748b, jaVar.m560a(), i2));
        }
    }

    private static boolean a(Context context, jj jjVar, String str) {
        if (jjVar != null && jjVar.m593a() != null && jjVar.m593a().m560a() != null && !TextUtils.isEmpty(str)) {
            return Boolean.parseBoolean(jjVar.m593a().m560a().get("use_clicked_activity")) && m.a(context, a(str));
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("should clicked activity params are null.");
        return false;
    }

    public static ComponentName a(String str) {
        return new ComponentName(str, "com.xiaomi.mipush.sdk.NotificationClickedActivity");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0072 A[PHI: r0 r3
      0x0072: PHI (r0v4 java.lang.String) = (r0v2 java.lang.String), (r0v5 java.lang.String) binds: [B:18:0x0070, B:10:0x004e] A[DONT_GENERATE, DONT_INLINE]
      0x0072: PHI (r3v14 java.lang.String) = (r3v13 java.lang.String), (r3v20 java.lang.String) binds: [B:18:0x0070, B:10:0x004e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String[] a(android.content.Context r3, com.xiaomi.push.ja r4) {
        /*
            java.lang.String r0 = r4.m567c()
            java.lang.String r1 = r4.d()
            java.util.Map r4 = r4.m560a()
            if (r4 == 0) goto L73
            android.content.res.Resources r2 = r3.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.content.res.Resources r3 = r3.getResources()
            android.util.DisplayMetrics r3 = r3.getDisplayMetrics()
            float r3 = r3.density
            float r2 = (float) r2
            float r2 = r2 / r3
            r3 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r3
            java.lang.Float r3 = java.lang.Float.valueOf(r2)
            int r3 = r3.intValue()
            r2 = 320(0x140, float:4.48E-43)
            if (r3 > r2) goto L51
            java.lang.String r3 = "title_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L42
            r0 = r3
        L42:
            java.lang.String r3 = "description_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L73
            goto L72
        L51:
            r2 = 360(0x168, float:5.04E-43)
            if (r3 <= r2) goto L73
            java.lang.String r3 = "title_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L64
            r0 = r3
        L64:
            java.lang.String r3 = "description_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L73
        L72:
            r1 = r3
        L73:
            java.lang.String[] r3 = new java.lang.String[]{r0, r1}
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.al.a(android.content.Context, com.xiaomi.push.ja):java.lang.String[]");
    }

    private static String a(Map<String, String> map, String str) {
        if (map != null) {
            return map.get(str);
        }
        return null;
    }

    private static int a(Context context, String str, Map<String, String> map, int i2) throws URISyntaxException, NumberFormatException {
        ComponentName componentNameA;
        Intent intentB = b(context, str, map, i2);
        if (intentB == null || (componentNameA = m.a(context, intentB)) == null) {
            return 0;
        }
        return componentNameA.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x027a  */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v12 */
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.xiaomi.push.service.al.b a(android.content.Context r29, com.xiaomi.push.jj r30, byte[] r31, android.widget.RemoteViews r32, android.app.PendingIntent r33, int r34) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1134
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.al.a(android.content.Context, com.xiaomi.push.jj, byte[], android.widget.RemoteViews, android.app.PendingIntent, int):com.xiaomi.push.service.al$b");
    }

    @TargetApi(16)
    private static void a(fq fqVar, Context context, String str, jj jjVar, byte[] bArr, int i2) {
        PendingIntent pendingIntentA;
        PendingIntent pendingIntentA2;
        PendingIntent pendingIntentA3;
        PendingIntent pendingIntentA4;
        Map<String, String> mapM560a = jjVar.m593a().m560a();
        if (TextUtils.equals("3", mapM560a.get("notification_style_type")) || TextUtils.equals("4", mapM560a.get("notification_style_type"))) {
            return;
        }
        if (m732b(mapM560a)) {
            for (int i3 = 1; i3 <= 3; i3++) {
                String str2 = mapM560a.get(String.format("cust_btn_%s_n", Integer.valueOf(i3)));
                if (!TextUtils.isEmpty(str2) && (pendingIntentA4 = a(context, str, jjVar, bArr, i2, i3)) != null) {
                    fqVar.addAction(0, str2, pendingIntentA4);
                }
            }
            return;
        }
        if (!TextUtils.isEmpty(mapM560a.get("notification_style_button_left_name")) && (pendingIntentA3 = a(context, str, jjVar, bArr, i2, 1)) != null) {
            fqVar.addAction(0, mapM560a.get("notification_style_button_left_name"), pendingIntentA3);
        }
        if (!TextUtils.isEmpty(mapM560a.get("notification_style_button_mid_name")) && (pendingIntentA2 = a(context, str, jjVar, bArr, i2, 2)) != null) {
            fqVar.addAction(0, mapM560a.get("notification_style_button_mid_name"), pendingIntentA2);
        }
        if (TextUtils.isEmpty(mapM560a.get("notification_style_button_right_name")) || (pendingIntentA = a(context, str, jjVar, bArr, i2, 3)) == null) {
            return;
        }
        fqVar.addAction(0, mapM560a.get("notification_style_button_right_name"), pendingIntentA);
    }

    private static PendingIntent a(Context context, String str, jj jjVar, byte[] bArr, int i2, int i3) {
        Map<String, String> mapM560a = jjVar.m593a().m560a();
        if (mapM560a == null) {
            return null;
        }
        boolean zA = a(context, jjVar, str);
        if (zA) {
            return a(context, jjVar, str, bArr, i2, i3, zA);
        }
        Intent intentM722a = m722a(context, str, mapM560a, i3);
        if (intentM722a == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return PendingIntent.getActivity(context, 0, intentM722a, 167772160);
        }
        return PendingIntent.getActivity(context, 0, intentM722a, C.BUFFER_FLAG_FIRST_SAMPLE);
    }

    public static String a(Map<String, String> map, int i2) {
        String str;
        if (i2 == 0) {
            str = "notify_effect";
        } else if (m732b(map)) {
            str = String.format("cust_btn_%s_ne", Integer.valueOf(i2));
        } else if (i2 == 1) {
            str = "notification_style_button_left_notify_effect";
        } else if (i2 == 2) {
            str = "notification_style_button_mid_notify_effect";
        } else if (i2 == 3) {
            str = "notification_style_button_right_notify_effect";
        } else {
            str = i2 == 4 ? "notification_colorful_button_notify_effect" : null;
        }
        if (map == null || str == null) {
            return null;
        }
        return map.get(str);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Intent m722a(Context context, String str, Map<String, String> map, int i2) {
        if (m732b(map)) {
            return a(context, str, map, String.format("cust_btn_%s_ne", Integer.valueOf(i2)), String.format("cust_btn_%s_iu", Integer.valueOf(i2)), String.format("cust_btn_%s_ic", Integer.valueOf(i2)), String.format("cust_btn_%s_wu", Integer.valueOf(i2)));
        }
        if (i2 == 1) {
            return a(context, str, map, "notification_style_button_left_notify_effect", "notification_style_button_left_intent_uri", "notification_style_button_left_intent_class", "notification_style_button_left_web_uri");
        }
        if (i2 == 2) {
            return a(context, str, map, "notification_style_button_mid_notify_effect", "notification_style_button_mid_intent_uri", "notification_style_button_mid_intent_class", "notification_style_button_mid_web_uri");
        }
        if (i2 == 3) {
            return a(context, str, map, "notification_style_button_right_notify_effect", "notification_style_button_right_intent_uri", "notification_style_button_right_intent_class", "notification_style_button_right_web_uri");
        }
        if (i2 != 4) {
            return null;
        }
        return a(context, str, map, "notification_colorful_button_notify_effect", "notification_colorful_button_intent_uri", "notification_colorful_button_intent_class", "notification_colorful_button_web_uri");
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.Intent a(android.content.Context r3, java.lang.String r4, java.util.Map<java.lang.String, java.lang.String> r5, java.lang.String r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) throws java.net.URISyntaxException {
        /*
            Method dump skipped, instructions count: 367
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.al.a(android.content.Context, java.lang.String, java.util.Map, java.lang.String, java.lang.String, java.lang.String, java.lang.String):android.content.Intent");
    }

    @TargetApi(16)
    private static fq a(Context context, jj jjVar, byte[] bArr, String str, int i2) {
        PendingIntent pendingIntentA;
        String strA = a(jjVar);
        Map<String, String> mapM560a = jjVar.m593a().m560a();
        String str2 = mapM560a.get("notification_style_type");
        fq fqVarA = (!com.xiaomi.push.j.m550a(context) || f1000a == null) ? null : f1000a.a(context, i2, strA, mapM560a);
        if (fqVarA != null) {
            fqVarA.a(mapM560a);
            return fqVarA;
        }
        if ("2".equals(str2)) {
            fq fqVar = new fq(context);
            Bitmap bitmapA = TextUtils.isEmpty(mapM560a.get("notification_bigPic_uri")) ? null : a(context, mapM560a.get("notification_bigPic_uri"), false);
            if (bitmapA == null) {
                com.xiaomi.channel.commonutils.logger.b.m91a("can not get big picture.");
                return fqVar;
            }
            Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle(fqVar);
            bigPictureStyle.bigPicture(bitmapA);
            bigPictureStyle.setSummaryText(str);
            bigPictureStyle.bigLargeIcon((Bitmap) null);
            fqVar.setStyle(bigPictureStyle);
            return fqVar;
        }
        if ("1".equals(str2)) {
            fq fqVar2 = new fq(context);
            fqVar2.setStyle(new Notification.BigTextStyle().bigText(str));
            return fqVar2;
        }
        if ("4".equals(str2) && com.xiaomi.push.j.m549a()) {
            fp fpVar = new fp(context, strA);
            if (!TextUtils.isEmpty(mapM560a.get("notification_banner_image_uri"))) {
                fpVar.setLargeIcon(a(context, mapM560a.get("notification_banner_image_uri"), false));
            }
            if (!TextUtils.isEmpty(mapM560a.get("notification_banner_icon_uri"))) {
                fpVar.b(a(context, mapM560a.get("notification_banner_icon_uri"), false));
            }
            fpVar.a(mapM560a);
            return fpVar;
        }
        if ("3".equals(str2) && com.xiaomi.push.j.m549a()) {
            fr frVar = new fr(context, i2, strA);
            if (!TextUtils.isEmpty(mapM560a.get("notification_colorful_button_text")) && (pendingIntentA = a(context, strA, jjVar, bArr, i2, 4)) != null) {
                frVar.a(mapM560a.get("notification_colorful_button_text"), pendingIntentA).mo409a(mapM560a.get("notification_colorful_button_bg_color"));
            }
            if (!TextUtils.isEmpty(mapM560a.get("notification_colorful_bg_color"))) {
                frVar.b(mapM560a.get("notification_colorful_bg_color"));
            } else if (!TextUtils.isEmpty(mapM560a.get("notification_colorful_bg_image_uri"))) {
                frVar.setLargeIcon(a(context, mapM560a.get("notification_colorful_bg_image_uri"), false));
            }
            frVar.a(mapM560a);
            return frVar;
        }
        return new fq(context);
    }

    private static int a(Map<String, String> map) {
        String str = map == null ? null : map.get("timeout");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    private static RemoteViews a(Context context, jj jjVar, byte[] bArr) throws JSONException, PackageManager.NameNotFoundException {
        ja jaVarM593a = jjVar.m593a();
        String strA = a(jjVar);
        if (jaVarM593a != null && jaVarM593a.m560a() != null) {
            Map<String, String> mapM560a = jaVarM593a.m560a();
            String str = mapM560a.get("layout_name");
            String str2 = mapM560a.get("layout_value");
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                try {
                    Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(strA);
                    int identifier = resourcesForApplication.getIdentifier(str, TtmlNode.TAG_LAYOUT, strA);
                    if (identifier == 0) {
                        return null;
                    }
                    RemoteViews remoteViews = new RemoteViews(strA, identifier);
                    try {
                        JSONObject jSONObject = new JSONObject(str2);
                        if (jSONObject.has("text")) {
                            JSONObject jSONObject2 = jSONObject.getJSONObject("text");
                            Iterator<String> itKeys = jSONObject2.keys();
                            while (itKeys.hasNext()) {
                                String next = itKeys.next();
                                String string = jSONObject2.getString(next);
                                int identifier2 = resourcesForApplication.getIdentifier(next, "id", strA);
                                if (identifier2 > 0) {
                                    remoteViews.setTextViewText(identifier2, string);
                                }
                            }
                        }
                        if (jSONObject.has("image")) {
                            JSONObject jSONObject3 = jSONObject.getJSONObject("image");
                            Iterator<String> itKeys2 = jSONObject3.keys();
                            while (itKeys2.hasNext()) {
                                String next2 = itKeys2.next();
                                String string2 = jSONObject3.getString(next2);
                                int identifier3 = resourcesForApplication.getIdentifier(next2, "id", strA);
                                int identifier4 = resourcesForApplication.getIdentifier(string2, "drawable", strA);
                                if (identifier3 > 0) {
                                    remoteViews.setImageViewResource(identifier3, identifier4);
                                }
                            }
                        }
                        if (jSONObject.has("time")) {
                            JSONObject jSONObject4 = jSONObject.getJSONObject("time");
                            Iterator<String> itKeys3 = jSONObject4.keys();
                            while (itKeys3.hasNext()) {
                                String next3 = itKeys3.next();
                                String string3 = jSONObject4.getString(next3);
                                if (string3.length() == 0) {
                                    string3 = "yy-MM-dd hh:mm";
                                }
                                int identifier5 = resourcesForApplication.getIdentifier(next3, "id", strA);
                                if (identifier5 > 0) {
                                    remoteViews.setTextViewText(identifier5, new SimpleDateFormat(string3).format(new Date(System.currentTimeMillis())));
                                }
                            }
                        }
                        return remoteViews;
                    } catch (JSONException e2) {
                        com.xiaomi.channel.commonutils.logger.b.a(e2);
                        return null;
                    }
                } catch (PackageManager.NameNotFoundException e3) {
                    com.xiaomi.channel.commonutils.logger.b.a(e3);
                }
            }
        }
        return null;
    }

    private static Bitmap a(Context context, int i2) {
        return a(context.getResources().getDrawable(i2));
    }

    private static int a(Context context, String str, String str2) {
        if (str.equals(context.getPackageName())) {
            return context.getResources().getIdentifier(str2, "drawable", str);
        }
        return 0;
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight > 0 ? intrinsicHeight : 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private static Notification a(Notification notification) {
        Object objA = com.xiaomi.push.bk.a(notification, "extraNotification");
        if (objA != null) {
            com.xiaomi.push.bk.a(objA, "setCustomizedIcon", Boolean.TRUE);
        }
        return notification;
    }

    public static String a(jj jjVar) {
        ja jaVarM593a;
        if ("com.xiaomi.xmsf".equals(jjVar.f748b) && (jaVarM593a = jjVar.m593a()) != null && jaVarM593a.m560a() != null) {
            String str = jaVarM593a.m560a().get("miui_package_name");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return jjVar.f748b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m724a(Context context, String str) {
        a(context, str, -1);
    }

    public static void a(Context context, String str, int i2) {
        a(context, str, i2, -1);
    }

    public static void a(Context context, String str, int i2, int i3) {
        int iHashCode;
        if (context == null || TextUtils.isEmpty(str) || i2 < -1) {
            return;
        }
        aw awVarA = aw.a(context, str);
        List<StatusBarNotification> listM749b = awVarA.m749b();
        if (com.xiaomi.push.s.a(listM749b)) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        boolean z2 = false;
        if (i2 == -1) {
            z2 = true;
            iHashCode = 0;
        } else {
            iHashCode = ((str.hashCode() / 10) * 10) + i2;
        }
        Iterator<StatusBarNotification> it = listM749b.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StatusBarNotification next = it.next();
            if (!TextUtils.isEmpty(String.valueOf(next.getId()))) {
                int id = next.getId();
                if (z2) {
                    linkedList.add(next);
                    awVarA.a(id);
                } else if (iHashCode == id) {
                    d.a(context, next, i3);
                    linkedList.add(next);
                    awVarA.a(id);
                    break;
                }
            }
        }
        a(context, (LinkedList<? extends Object>) linkedList);
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        aw awVarA = aw.a(context, str);
        List<StatusBarNotification> listM749b = awVarA.m749b();
        if (com.xiaomi.push.s.a(listM749b)) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (StatusBarNotification statusBarNotification : listM749b) {
            Notification notification = statusBarNotification.getNotification();
            if (notification != null && !TextUtils.isEmpty(String.valueOf(statusBarNotification.getId()))) {
                int id = statusBarNotification.getId();
                String strA = ax.a(notification);
                String strB = ax.b(notification);
                if (!TextUtils.isEmpty(strA) && !TextUtils.isEmpty(strB) && a(strA, str2) && a(strB, str3)) {
                    linkedList.add(statusBarNotification);
                    awVarA.a(id);
                }
            }
        }
        a(context, (LinkedList<? extends Object>) linkedList);
    }

    private static boolean a(String str, String str2) {
        return TextUtils.isEmpty(str) || str2.contains(str);
    }

    public static void a(Context context, LinkedList<? extends Object> linkedList) {
        if (linkedList == null || linkedList.size() <= 0) {
            return;
        }
        ca.a(context, "category_clear_notification", "clear_notification", linkedList.size(), "");
    }

    static int a(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).getInt(str, Integer.MAX_VALUE);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m728a(Map<String, String> map) {
        if (map == null || !map.containsKey("notify_foreground")) {
            return true;
        }
        return "1".equals(map.get("notify_foreground"));
    }

    private static boolean a(ja jaVar) {
        if (jaVar == null) {
            return false;
        }
        String strM559a = jaVar.m559a();
        return !TextUtils.isEmpty(strM559a) && strM559a.length() == 22 && "satuigmo".indexOf(strM559a.charAt(0)) >= 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m727a(jj jjVar) {
        ja jaVarM593a = jjVar.m593a();
        return a(jaVarM593a) && jaVarM593a.l();
    }

    private static Bitmap a(Context context, String str, boolean z2) {
        Future futureSubmit = f1002a.submit(new a(str, context, z2));
        try {
            try {
                try {
                    Bitmap bitmap = (Bitmap) futureSubmit.get(180L, TimeUnit.SECONDS);
                    return bitmap == null ? bitmap : bitmap;
                } catch (InterruptedException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    return null;
                } catch (TimeoutException e3) {
                    com.xiaomi.channel.commonutils.logger.b.a(e3);
                    return null;
                }
            } catch (ExecutionException e4) {
                com.xiaomi.channel.commonutils.logger.b.a(e4);
                return null;
            }
        } finally {
            futureSubmit.cancel(true);
        }
    }

    private static String a(Context context, String str, Map<String, String> map) {
        if (map != null && !TextUtils.isEmpty(map.get("channel_name"))) {
            return map.get("channel_name");
        }
        return com.xiaomi.push.g.m426b(context, str);
    }

    private static void a(Intent intent) {
        if (intent == null) {
            return;
        }
        intent.setFlags(intent.getFlags() & (-196));
    }

    private static void a(Context context, String str, fq fqVar, Map<String, String> map) {
        int iA = a(context, str, "mipush_small_notification");
        int iA2 = a(context, str, "mipush_notification");
        if (com.xiaomi.push.j.m550a(context)) {
            if (iA > 0 && iA2 > 0) {
                fqVar.setSmallIcon(iA);
                fqVar.setLargeIcon(a(context, iA2));
                return;
            } else {
                b(context, str, fqVar, map);
                return;
            }
        }
        if (iA > 0) {
            fqVar.setSmallIcon(iA);
        } else {
            b(context, str, fqVar, map);
        }
        if (iA2 > 0) {
            fqVar.setLargeIcon(a(context, iA2));
        }
    }
}
