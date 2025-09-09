package a.a.a.a.b;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import datasource.MeshConfigCallback;
import datasource.channel.data.DownStreamControlData;
import datasource.channel.data.DownStreamControlDataForTg;

/* loaded from: classes.dex */
public class Q implements MeshConfigCallback<String> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1225a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1226b;

    public Q(MeshService meshService, int i2) {
        this.f1226b = meshService;
        this.f1225a = i2;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(String str) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "reportDevicesStatus request success " + str);
        a.a.a.a.b.l.c.a(this.f1225a, 1, true);
        if (a.a.a.a.b.d.a.f1335b && !"true".equals(str)) {
            try {
                this.f1226b.handleDownStreamControlData((DownStreamControlData) JSON.parseObject(str, DownStreamControlData.class));
                return;
            } catch (JSONException unused) {
                return;
            }
        }
        if (!a.a.a.a.b.d.a.f1334a || "true".equals(str)) {
            return;
        }
        try {
            if (TextUtils.isEmpty(str)) {
                a.a.a.a.b.m.a.d(MeshService.TAG, "reportDevicesStatus request success, but model data is empty. ");
                return;
            }
            JSONArray array = JSON.parseArray(str);
            if (array != null && !array.isEmpty() && array.getJSONObject(0) != null && !TextUtils.isEmpty(array.getJSONObject(0).getString("extensions"))) {
                this.f1226b.handleDownStreamControlDataForTg((DownStreamControlDataForTg) JSON.parseObject(array.getJSONObject(0).getString("extensions"), DownStreamControlDataForTg.class), array.getJSONObject(0).getString("devId"));
                return;
            }
            String str2 = MeshService.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("reportDevicesStatus request success, but model data do not contain extensions key. model=");
            sb.append(str);
            a.a.a.a.b.m.a.d(str2, sb.toString());
        } catch (JSONException e2) {
            a.a.a.a.b.m.a.d(MeshService.TAG, "reportDevicesStatus request success, parse except = " + e2);
        } catch (Exception e3) {
            a.a.a.a.b.m.a.d(MeshService.TAG, "reportDevicesStatus request success, except = " + e3);
        }
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "reportDevicesStatus request failed, errorMessage: " + str2);
        a.a.a.a.b.l.c.a(this.f1225a, 0, true);
    }
}
