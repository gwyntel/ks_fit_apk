package anet.channel.strategy;

import androidx.health.connect.client.records.CervicalMucusRecord;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.taobao.accs.utl.BaseMonitor;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class l {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final int f7021a;

        /* renamed from: b, reason: collision with root package name */
        public final String f7022b;

        /* renamed from: c, reason: collision with root package name */
        public final int f7023c;

        /* renamed from: d, reason: collision with root package name */
        public final int f7024d;

        /* renamed from: e, reason: collision with root package name */
        public final int f7025e;

        /* renamed from: f, reason: collision with root package name */
        public final int f7026f;

        /* renamed from: g, reason: collision with root package name */
        public final String f7027g;

        /* renamed from: h, reason: collision with root package name */
        public final String f7028h;

        public a(JSONObject jSONObject) {
            this.f7021a = jSONObject.optInt("port");
            this.f7022b = jSONObject.optString("protocol");
            this.f7023c = jSONObject.optInt("cto");
            this.f7024d = jSONObject.optInt("rto");
            this.f7025e = jSONObject.optInt("retry");
            this.f7026f = jSONObject.optInt("heartbeat");
            this.f7027g = jSONObject.optString("rtt", "");
            this.f7028h = jSONObject.optString("publickey");
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public final String f7029a;

        /* renamed from: b, reason: collision with root package name */
        public final int f7030b;

        /* renamed from: c, reason: collision with root package name */
        public final String f7031c;

        /* renamed from: d, reason: collision with root package name */
        public final String f7032d;

        /* renamed from: e, reason: collision with root package name */
        public final String f7033e;

        /* renamed from: f, reason: collision with root package name */
        public final String[] f7034f;

        /* renamed from: g, reason: collision with root package name */
        public final String[] f7035g;

        /* renamed from: h, reason: collision with root package name */
        public final a[] f7036h;

        /* renamed from: i, reason: collision with root package name */
        public final e[] f7037i;

        /* renamed from: j, reason: collision with root package name */
        public final boolean f7038j;

        /* renamed from: k, reason: collision with root package name */
        public final boolean f7039k;

        /* renamed from: l, reason: collision with root package name */
        public final int f7040l;

        public b(JSONObject jSONObject) {
            this.f7029a = jSONObject.optString("host");
            this.f7030b = jSONObject.optInt(RemoteMessageConst.TTL);
            this.f7031c = jSONObject.optString("safeAisles");
            this.f7032d = jSONObject.optString("cname", null);
            this.f7033e = jSONObject.optString("unit", null);
            this.f7038j = jSONObject.optInt(CervicalMucusRecord.Appearance.CLEAR) == 1;
            this.f7039k = jSONObject.optBoolean("effectNow");
            this.f7040l = jSONObject.optInt("version");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("ips");
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                this.f7034f = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    this.f7034f[i2] = jSONArrayOptJSONArray.optString(i2);
                }
            } else {
                this.f7034f = null;
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("sips");
            if (jSONArrayOptJSONArray2 == null || jSONArrayOptJSONArray2.length() <= 0) {
                this.f7035g = null;
            } else {
                int length2 = jSONArrayOptJSONArray2.length();
                this.f7035g = new String[length2];
                for (int i3 = 0; i3 < length2; i3++) {
                    this.f7035g[i3] = jSONArrayOptJSONArray2.optString(i3);
                }
            }
            JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("aisles");
            if (jSONArrayOptJSONArray3 != null) {
                int length3 = jSONArrayOptJSONArray3.length();
                this.f7036h = new a[length3];
                for (int i4 = 0; i4 < length3; i4++) {
                    this.f7036h[i4] = new a(jSONArrayOptJSONArray3.optJSONObject(i4));
                }
            } else {
                this.f7036h = null;
            }
            JSONArray jSONArrayOptJSONArray4 = jSONObject.optJSONArray("strategies");
            if (jSONArrayOptJSONArray4 == null || jSONArrayOptJSONArray4.length() <= 0) {
                this.f7037i = null;
                return;
            }
            int length4 = jSONArrayOptJSONArray4.length();
            this.f7037i = new e[length4];
            for (int i5 = 0; i5 < length4; i5++) {
                this.f7037i[i5] = new e(jSONArrayOptJSONArray4.optJSONObject(i5));
            }
        }
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public final String f7041a;

        /* renamed from: b, reason: collision with root package name */
        public final e[] f7042b;

        public c(JSONObject jSONObject) {
            this.f7041a = jSONObject.optString("host");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("strategies");
            if (jSONArrayOptJSONArray == null) {
                this.f7042b = null;
                return;
            }
            int length = jSONArrayOptJSONArray.length();
            this.f7042b = new e[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.f7042b[i2] = new e(jSONArrayOptJSONArray.optJSONObject(i2));
            }
        }
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        public final String f7043a;

        /* renamed from: b, reason: collision with root package name */
        public final b[] f7044b;

        /* renamed from: c, reason: collision with root package name */
        public final c[] f7045c;

        /* renamed from: d, reason: collision with root package name */
        public final String f7046d;

        /* renamed from: e, reason: collision with root package name */
        public final String f7047e;

        /* renamed from: f, reason: collision with root package name */
        public final int f7048f;

        /* renamed from: g, reason: collision with root package name */
        public final int f7049g;

        /* renamed from: h, reason: collision with root package name */
        public final int f7050h;

        public d(JSONObject jSONObject) {
            this.f7043a = jSONObject.optString("ip");
            this.f7046d = jSONObject.optString("uid", null);
            this.f7047e = jSONObject.optString("utdid", null);
            this.f7048f = jSONObject.optInt(DispatchConstants.CONFIG_VERSION);
            this.f7049g = jSONObject.optInt("fcl");
            this.f7050h = jSONObject.optInt("fct");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(BaseMonitor.COUNT_POINT_DNS);
            if (jSONArrayOptJSONArray != null) {
                int length = jSONArrayOptJSONArray.length();
                this.f7044b = new b[length];
                for (int i2 = 0; i2 < length; i2++) {
                    this.f7044b[i2] = new b(jSONArrayOptJSONArray.optJSONObject(i2));
                }
            } else {
                this.f7044b = null;
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("hrTask");
            if (jSONArrayOptJSONArray2 == null) {
                this.f7045c = null;
                return;
            }
            int length2 = jSONArrayOptJSONArray2.length();
            this.f7045c = new c[length2];
            for (int i3 = 0; i3 < length2; i3++) {
                this.f7045c[i3] = new c(jSONArrayOptJSONArray2.optJSONObject(i3));
            }
        }
    }

    public static class e {

        /* renamed from: a, reason: collision with root package name */
        public final String f7051a;

        /* renamed from: b, reason: collision with root package name */
        public final a f7052b;

        /* renamed from: c, reason: collision with root package name */
        public final String f7053c;

        public e(JSONObject jSONObject) {
            this.f7051a = jSONObject.optString("ip");
            this.f7053c = jSONObject.optString("path");
            this.f7052b = new a(jSONObject);
        }
    }

    public static d a(JSONObject jSONObject) {
        try {
            return new d(jSONObject);
        } catch (Exception e2) {
            ALog.e("StrategyResultParser", "Parse HttpDns response failed.", null, e2, "JSON Content", jSONObject.toString());
            return null;
        }
    }
}
