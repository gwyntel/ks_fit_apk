package com.alibaba.sdk.android.httpdns.a;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.beacon.Beacon;
import com.alibaba.sdk.android.httpdns.d.b;
import com.alibaba.sdk.android.httpdns.i;
import com.heytap.mcssdk.constant.Constants;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f8790a;
    private Context mContext = null;

    /* renamed from: a, reason: collision with other field name */
    private b f3a = null;

    /* renamed from: a, reason: collision with other field name */
    private Beacon f2a = null;

    /* renamed from: m, reason: collision with root package name */
    private boolean f8791m = true;

    /* renamed from: a, reason: collision with other field name */
    private final Beacon.OnUpdateListener f1a = new Beacon.OnUpdateListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.1
        @Override // com.alibaba.sdk.android.beacon.Beacon.OnUpdateListener
        public void onUpdate(List<Beacon.Config> list) {
            try {
                a.this.b(list);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    };

    /* renamed from: a, reason: collision with other field name */
    private final Beacon.OnServiceErrListener f0a = new Beacon.OnServiceErrListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.2
        @Override // com.alibaba.sdk.android.beacon.Beacon.OnServiceErrListener
        public void onErr(Beacon.Error error) {
            Log.e("HTTPDNS:BeaconManager", "beacon error. errorCode:" + error.errCode + ", errorMsg:" + error.errMsg);
        }
    };

    private a() {
    }

    public static a a() {
        if (f8790a == null) {
            synchronized (a.class) {
                try {
                    if (f8790a == null) {
                        f8790a = new a();
                    }
                } finally {
                }
            }
        }
        return f8790a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(List<Beacon.Config> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (Beacon.Config config : list) {
            if (config.key.equalsIgnoreCase("___httpdns_service___")) {
                a(config);
            }
        }
    }

    private void i(String str) {
        if (str != null) {
            Log.d("HTTPDNS:BeaconManager", "is IP probe enabled:" + str);
            this.f8791m = !str.equalsIgnoreCase("disabled");
        }
    }

    private void j(String str) {
        if (str != null) {
            com.alibaba.sdk.android.httpdns.b.a(!"disabled".equals(str));
            i.e("[beacon] httpdns enable: " + com.alibaba.sdk.android.httpdns.b.a());
        }
    }

    public void c(Context context, String str) {
        this.mContext = context;
        if (context != null) {
            HashMap map = new HashMap();
            map.put("sdkId", "httpdns");
            map.put("accountId", str);
            Beacon beaconBuild = new Beacon.Builder().appKey("24657847").appSecret("f30fc0937f2b1e9e50a1b7134f1ddb10").loopInterval(Constants.MILLS_OF_WATCH_DOG).extras(map).build();
            this.f2a = beaconBuild;
            beaconBuild.addUpdateListener(this.f1a);
            this.f2a.addServiceErrListener(this.f0a);
            this.f2a.start(this.mContext.getApplicationContext());
        }
    }

    public boolean f() {
        return this.f8791m;
    }

    private boolean f(String str) {
        if (str == null || this.f3a == null) {
            return false;
        }
        Log.d("HTTPDNS:BeaconManager", "is report enabled:" + str);
        if (str.equalsIgnoreCase("disabled")) {
            this.f3a.e(false);
        } else {
            this.f3a.e(true);
        }
        return true;
    }

    public void a(b bVar) {
        this.f3a = bVar;
    }

    private boolean a(Beacon.Config config) {
        if (config == null || !config.key.equalsIgnoreCase("___httpdns_service___")) {
            return false;
        }
        String str = config.value;
        if (str != null) {
            Log.d("HTTPDNS:BeaconManager", "httpdns configs:" + str);
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ut")) {
                    f(jSONObject.getString("ut"));
                }
                if (jSONObject.has("ip-ranking")) {
                    i(jSONObject.getString("ip-ranking"));
                }
                if (jSONObject.has("status")) {
                    j(jSONObject.getString("status"));
                }
            } catch (Exception e2) {
                Log.e("HTTPDNS:BeaconManager", "parse push configs failed.", e2);
                return false;
            }
        }
        return true;
    }
}
