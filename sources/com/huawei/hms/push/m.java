package com.huawei.hms.push;

import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.support.log.HMSLog;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class m {
    private int B;
    private String D;

    /* renamed from: b, reason: collision with root package name */
    private int f16693b;

    /* renamed from: c, reason: collision with root package name */
    private String f16694c;

    /* renamed from: d, reason: collision with root package name */
    private String f16695d;

    /* renamed from: l, reason: collision with root package name */
    private String f16703l;

    /* renamed from: m, reason: collision with root package name */
    private String f16704m;

    /* renamed from: n, reason: collision with root package name */
    private String f16705n;

    /* renamed from: o, reason: collision with root package name */
    private String f16706o;

    /* renamed from: p, reason: collision with root package name */
    private String f16707p;

    /* renamed from: r, reason: collision with root package name */
    private String f16709r;

    /* renamed from: s, reason: collision with root package name */
    private String f16710s;

    /* renamed from: z, reason: collision with root package name */
    private String f16717z;

    /* renamed from: a, reason: collision with root package name */
    private String f16692a = "";

    /* renamed from: e, reason: collision with root package name */
    private String f16696e = "";

    /* renamed from: f, reason: collision with root package name */
    private String f16697f = "";

    /* renamed from: g, reason: collision with root package name */
    private String f16698g = "";

    /* renamed from: h, reason: collision with root package name */
    private String f16699h = "";

    /* renamed from: i, reason: collision with root package name */
    private String f16700i = "";

    /* renamed from: j, reason: collision with root package name */
    private String f16701j = "";

    /* renamed from: k, reason: collision with root package name */
    private String f16702k = "";

    /* renamed from: q, reason: collision with root package name */
    private String f16708q = "";

    /* renamed from: t, reason: collision with root package name */
    private int f16711t = i.STYLE_DEFAULT.ordinal();

    /* renamed from: u, reason: collision with root package name */
    private String f16712u = "";

    /* renamed from: v, reason: collision with root package name */
    private String f16713v = "";

    /* renamed from: w, reason: collision with root package name */
    private String f16714w = "";

    /* renamed from: x, reason: collision with root package name */
    private int f16715x = 0;

    /* renamed from: y, reason: collision with root package name */
    private int f16716y = 0;
    private String A = "";
    private String C = "";
    private String E = "";
    private String F = "";

    public m(byte[] bArr, byte[] bArr2) {
        Charset charset = k.f16690a;
        this.f16709r = new String(bArr, charset);
        this.f16710s = new String(bArr2, charset);
    }

    private JSONObject a(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(RemoteMessageConst.MessageBody.MSG_CONTENT, jSONObject);
        jSONObject2.put("group", this.f16692a);
        jSONObject2.put("tag", this.A);
        jSONObject2.put(RemoteMessageConst.Notification.AUTO_CANCEL, this.f16715x);
        jSONObject2.put("visibility", this.f16716y);
        jSONObject2.put(RemoteMessageConst.Notification.WHEN, this.f16717z);
        return jSONObject2;
    }

    private JSONObject b(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(com.taobao.agoo.a.a.b.JSON_CMD, this.f16698g);
        jSONObject2.put("content", this.f16699h);
        jSONObject2.put(RemoteMessageConst.Notification.NOTIFY_ICON, this.f16700i);
        jSONObject2.put(RemoteMessageConst.Notification.NOTIFY_TITLE, this.f16701j);
        jSONObject2.put("notifySummary", this.f16702k);
        jSONObject2.put(RemoteMessageConst.MessageBody.PARAM, jSONObject);
        return jSONObject2;
    }

    private void c(JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("ap")) {
            String string = jSONObject.getString("ap");
            StringBuilder sb = new StringBuilder();
            if (TextUtils.isEmpty(string) || string.length() >= 48) {
                this.f16695d = string.substring(0, 48);
                return;
            }
            int length = 48 - string.length();
            for (int i2 = 0; i2 < length; i2++) {
                sb.append("0");
            }
            sb.append(string);
            this.f16695d = sb.toString();
        }
    }

    private boolean d(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return false;
        }
        if (jSONObject.has(RemoteMessageConst.Notification.CLICK_ACTION)) {
            this.f16704m = jSONObject.getString(RemoteMessageConst.Notification.CLICK_ACTION);
        }
        if (jSONObject.has(RemoteMessageConst.Notification.INTENT_URI)) {
            this.f16694c = jSONObject.getString(RemoteMessageConst.Notification.INTENT_URI);
        }
        if (jSONObject.has("appPackageName")) {
            this.f16703l = jSONObject.getString("appPackageName");
            return true;
        }
        HMSLog.d("PushSelfShowLog", "appPackageName is null");
        return false;
    }

    private boolean e(JSONObject jSONObject) throws JSONException {
        if (!jSONObject.has(RemoteMessageConst.MSGID)) {
            HMSLog.i("PushSelfShowLog", "msgId == null");
            return false;
        }
        Object obj = jSONObject.get(RemoteMessageConst.MSGID);
        if (obj instanceof String) {
            this.f16696e = (String) obj;
            return true;
        }
        if (!(obj instanceof Integer)) {
            return true;
        }
        this.f16696e = String.valueOf(((Integer) obj).intValue());
        return true;
    }

    private boolean f(JSONObject jSONObject) throws JSONException {
        HMSLog.d("PushSelfShowLog", "enter parseNotifyParam");
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.NOTIFY_DETAIL);
            if (jSONObject2.has("style")) {
                this.f16711t = jSONObject2.getInt("style");
            }
            this.f16712u = jSONObject2.optString("bigTitle");
            this.f16713v = jSONObject2.optString("bigContent");
            this.E = jSONObject2.optString(RemoteMessageConst.Notification.ICON);
            return true;
        } catch (JSONException e2) {
            HMSLog.i("PushSelfShowLog", e2.toString());
            return false;
        }
    }

    private void g(JSONObject jSONObject) {
        this.f16692a = jSONObject.optString("group");
        HMSLog.d("PushSelfShowLog", "NOTIFY_GROUP:" + this.f16692a);
        this.f16715x = jSONObject.optInt(RemoteMessageConst.Notification.AUTO_CANCEL, 1);
        HMSLog.d("PushSelfShowLog", "autoCancel: " + this.f16715x);
        this.f16716y = jSONObject.optInt("visibility", 0);
        this.f16717z = jSONObject.optString(RemoteMessageConst.Notification.WHEN);
        this.A = jSONObject.optString("tag");
    }

    private boolean h(JSONObject jSONObject) throws JSONException {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.PARAM);
            if (jSONObject2.has("autoClear")) {
                this.f16693b = jSONObject2.getInt("autoClear");
            } else {
                this.f16693b = 0;
            }
            if (!PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(this.f16698g) && !"cosa".equals(this.f16698g)) {
                if ("url".equals(this.f16698g)) {
                    k(jSONObject2);
                    return true;
                }
                if (!"rp".equals(this.f16698g)) {
                    return true;
                }
                j(jSONObject2);
                return true;
            }
            d(jSONObject2);
            return true;
        } catch (Exception e2) {
            HMSLog.e("PushSelfShowLog", "ParseParam error ", e2);
            return false;
        }
    }

    private boolean i(JSONObject jSONObject) throws JSONException {
        if (jSONObject.has(RemoteMessageConst.MessageBody.PS_CONTENT)) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
            this.f16698g = jSONObject2.getString(com.taobao.agoo.a.a.b.JSON_CMD);
            this.f16699h = jSONObject2.optString("content");
            this.f16700i = jSONObject2.optString(RemoteMessageConst.Notification.NOTIFY_ICON);
            this.f16701j = jSONObject2.optString(RemoteMessageConst.Notification.NOTIFY_TITLE);
            this.f16702k = jSONObject2.optString("notifySummary");
            this.D = jSONObject2.optString(RemoteMessageConst.Notification.TICKER);
            if ((!jSONObject2.has(RemoteMessageConst.MessageBody.NOTIFY_DETAIL) || f(jSONObject2)) && jSONObject2.has(RemoteMessageConst.MessageBody.PARAM)) {
                return h(jSONObject2);
            }
        }
        return false;
    }

    private boolean j(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return false;
        }
        if (jSONObject.has("appPackageName")) {
            this.f16703l = jSONObject.getString("appPackageName");
        }
        if (!jSONObject.has("rpt") || !jSONObject.has("rpl")) {
            HMSLog.d("PushSelfShowLog", "rpl or rpt is null");
            return false;
        }
        this.f16706o = jSONObject.getString("rpl");
        this.f16707p = jSONObject.getString("rpt");
        if (!jSONObject.has("rpct")) {
            return true;
        }
        this.f16708q = jSONObject.getString("rpct");
        return true;
    }

    private boolean k(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return false;
        }
        if (!jSONObject.has("url")) {
            HMSLog.d("PushSelfShowLog", "url is null");
            return false;
        }
        this.f16705n = jSONObject.getString("url");
        if (jSONObject.has("appPackageName")) {
            this.f16703l = jSONObject.getString("appPackageName");
        }
        if (!jSONObject.has("rpt") || !jSONObject.has("rpl")) {
            return true;
        }
        this.f16706o = jSONObject.getString("rpl");
        this.f16707p = jSONObject.getString("rpt");
        if (!jSONObject.has("rpct")) {
            return true;
        }
        this.f16708q = jSONObject.getString("rpct");
        return true;
    }

    private JSONObject r() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("style", this.f16711t);
        jSONObject.put("bigTitle", this.f16712u);
        jSONObject.put("bigContent", this.f16713v);
        jSONObject.put("bigPic", this.f16714w);
        return jSONObject;
    }

    private JSONObject v() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("autoClear", this.f16693b);
        jSONObject.put("url", this.f16705n);
        jSONObject.put("rpl", this.f16706o);
        jSONObject.put("rpt", this.f16707p);
        jSONObject.put("rpct", this.f16708q);
        jSONObject.put("appPackageName", this.f16703l);
        jSONObject.put(RemoteMessageConst.Notification.CLICK_ACTION, this.f16704m);
        jSONObject.put(RemoteMessageConst.Notification.INTENT_URI, this.f16694c);
        return jSONObject;
    }

    public String l() {
        return this.f16692a;
    }

    public String m() {
        return this.E;
    }

    public String n() {
        return this.f16694c;
    }

    public byte[] o() {
        try {
            return a(a(b(v()), r())).toString().getBytes(k.f16690a);
        } catch (JSONException e2) {
            HMSLog.e("PushSelfShowLog", "getMsgData failed JSONException:", e2);
            return new byte[0];
        }
    }

    public String p() {
        HMSLog.d("PushSelfShowLog", "msgId =" + this.f16696e);
        return this.f16696e;
    }

    public String q() {
        return this.A;
    }

    public int s() {
        return this.B;
    }

    public String t() {
        return this.f16702k;
    }

    public String u() {
        return this.f16701j;
    }

    public int w() {
        return this.f16711t;
    }

    public String x() {
        return this.D;
    }

    public byte[] y() {
        return this.f16710s.getBytes(k.f16690a);
    }

    public boolean z() throws JSONException {
        try {
            if (TextUtils.isEmpty(this.f16709r)) {
                HMSLog.d("PushSelfShowLog", "msg is null");
                return false;
            }
            JSONObject jSONObject = new JSONObject(this.f16709r);
            g(jSONObject);
            JSONObject jSONObject2 = jSONObject.getJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
            if (!e(jSONObject2)) {
                return false;
            }
            this.f16697f = jSONObject2.optString("dispPkgName");
            c(jSONObject2);
            this.B = jSONObject2.optInt(RemoteMessageConst.Notification.NOTIFY_ID, -1);
            this.C = jSONObject2.optString("data");
            this.F = jSONObject2.optString(RemoteMessageConst.ANALYTIC_INFO);
            return i(jSONObject2);
        } catch (JSONException unused) {
            HMSLog.d("PushSelfShowLog", "parse message exception.");
            return false;
        } catch (Exception e2) {
            HMSLog.d("PushSelfShowLog", e2.toString());
            return false;
        }
    }

    private JSONObject a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("dispPkgName", this.f16697f);
        jSONObject3.put(RemoteMessageConst.MSGID, this.f16696e);
        jSONObject3.put("ap", this.f16695d);
        jSONObject3.put(RemoteMessageConst.Notification.NOTIFY_ID, this.B);
        jSONObject3.put(RemoteMessageConst.MessageBody.PS_CONTENT, jSONObject);
        jSONObject3.put(RemoteMessageConst.MessageBody.NOTIFY_DETAIL, jSONObject2);
        jSONObject3.put(RemoteMessageConst.Notification.TICKER, this.D);
        jSONObject3.put("data", this.C);
        return jSONObject3;
    }

    public String b() {
        return this.F;
    }

    public String d() {
        return this.f16703l;
    }

    public String g() {
        return this.f16713v;
    }

    public int e() {
        return this.f16715x;
    }

    public int f() {
        return this.f16693b;
    }

    public String j() {
        return this.f16699h;
    }

    public String c() {
        return this.f16695d;
    }

    public String k() {
        return this.f16697f;
    }

    public String h() {
        return this.f16712u;
    }

    public String i() {
        return this.f16698g;
    }

    public String a() {
        return this.f16704m;
    }

    public void a(int i2) {
        this.B = i2;
    }
}
