package com.umeng.message.proguard;

import android.app.Application;
import android.content.ContentProviderOperation;
import android.content.Context;
import androidx.core.view.InputDeviceCompat;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UPLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class v implements UMLogDataProtocol {

    /* renamed from: a, reason: collision with root package name */
    private static final UMLogDataProtocol f22944a = new v();

    private v() {
    }

    public static UMLogDataProtocol a() {
        return f22944a;
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public final void removeCacheData(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = new JSONObject(obj.toString()).optJSONObject("content");
            if (jSONObjectOptJSONObject == null) {
                return;
            }
            Application applicationA = x.a();
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("push");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                    JSONObject jSONObject = (JSONObject) jSONArrayOptJSONArray.get(i2);
                    arrayList.add(ContentProviderOperation.newDelete(h.d(applicationA)).withSelection("MsgId=? And ActionType=?", new String[]{jSONObject.optString("msg_id"), String.valueOf(jSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE))}).build());
                }
            }
            applicationA.getContentResolver().applyBatch(h.f(applicationA), arrayList);
        } catch (Throwable th) {
            UPLog.e("LogDataProtocol", th);
        }
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public final JSONObject setupReportData(long j2) {
        return null;
    }

    @Override // com.umeng.commonsdk.framework.UMLogDataProtocol
    public final void workEvent(Object obj, int i2) {
        int iOptInt;
        JSONObject jSONObjectBuildEnvelopeWithExtHeader;
        JSONArray jSONArrayOptJSONArray;
        JSONObject jSONObjectBuildEnvelopeWithExtHeader2;
        JSONObject jSONObjectBuildEnvelopeWithExtHeader3;
        JSONObject jSONObject;
        JSONObject jSONObjectBuildEnvelopeWithExtHeader4;
        JSONObject jSONObjectBuildEnvelopeWithExtHeader5;
        int iOptInt2 = 0;
        if (obj == null) {
            return;
        }
        try {
            switch (i2) {
                case 16385:
                    Application applicationA = x.a();
                    JSONObject jSONObject2 = new JSONObject(obj.toString());
                    JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("content");
                    if (jSONObjectOptJSONObject == null || (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("push")) == null || jSONArrayOptJSONArray.length() <= 0) {
                        iOptInt = -1;
                    } else {
                        JSONObject jSONObject3 = (JSONObject) jSONArrayOptJSONArray.get(0);
                        iOptInt = jSONObject3.optInt(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE);
                        if (iOptInt != 70 && iOptInt != 71) {
                            if (iOptInt == 0) {
                                ((aj) PushAgent.getInstance(applicationA).getMessageNotifyApi()).a(jSONObject3);
                            }
                            ao.a(applicationA).a(jSONObject3.optString("msg_id"), iOptInt, jSONObject3.optLong("ts"));
                        }
                    }
                    int i3 = iOptInt;
                    JSONObject jSONObjectOptJSONObject2 = jSONObject2.optJSONObject("header");
                    if (jSONObjectOptJSONObject2 != null && jSONObjectOptJSONObject != null && (jSONObjectBuildEnvelopeWithExtHeader = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA, jSONObjectOptJSONObject2, jSONObjectOptJSONObject, "umpx_push_logs", "p", MsgConstant.SDK_VERSION)) != null && !jSONObjectBuildEnvelopeWithExtHeader.has("exception") && i3 != 70 && i3 != 71) {
                        try {
                            removeCacheData(jSONObject2);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            break;
                        }
                    } else {
                        return;
                    }
                    break;
                case InputDeviceCompat.SOURCE_STYLUS /* 16386 */:
                    Application applicationA2 = x.a();
                    JSONObject jSONObject4 = new JSONObject(obj.toString());
                    JSONObject jSONObjectOptJSONObject3 = jSONObject4.optJSONObject("header");
                    JSONObject jSONObjectOptJSONObject4 = jSONObject4.optJSONObject("content");
                    if (jSONObjectOptJSONObject3 != null && jSONObjectOptJSONObject4 != null && (jSONObjectBuildEnvelopeWithExtHeader2 = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA2, jSONObjectOptJSONObject3, jSONObjectOptJSONObject4, "umpx_push_launch", "p", MsgConstant.SDK_VERSION)) != null && !jSONObjectBuildEnvelopeWithExtHeader2.has("exception")) {
                        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(applicationA2);
                        messageSharedPrefs.f22601b.a(com.umeng.analytics.pro.f.f21694p, System.currentTimeMillis());
                        try {
                            int i4 = Integer.parseInt(UMEnvelopeBuild.imprintProperty(applicationA2, "launch_policy", "-1"));
                            UPLog.i("LogDataProtocol", "launch policy:", Integer.valueOf(i4));
                            if (i4 > 0) {
                                messageSharedPrefs.f22601b.a("launch_send_policy", i4);
                            }
                        } catch (Throwable unused) {
                        }
                        try {
                            int i5 = Integer.parseInt(UMEnvelopeBuild.imprintProperty(applicationA2, "tag_policy", "-1"));
                            UPLog.i("LogDataProtocol", "tag policy:", Integer.valueOf(i5));
                            if (i5 > 0) {
                                messageSharedPrefs.f22601b.a("tag_send_policy", i5);
                            }
                        } catch (Throwable unused2) {
                        }
                    }
                    y.f22948a = false;
                    return;
                case 16387:
                    Application applicationA3 = x.a();
                    JSONObject jSONObject5 = new JSONObject(obj.toString());
                    JSONObject jSONObjectOptJSONObject5 = jSONObject5.optJSONObject("header");
                    JSONObject jSONObjectOptJSONObject6 = jSONObject5.optJSONObject("content");
                    if (jSONObjectOptJSONObject5 != null && jSONObjectOptJSONObject6 != null && (jSONObjectBuildEnvelopeWithExtHeader3 = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA3, jSONObjectOptJSONObject5, jSONObjectOptJSONObject6, "umpx_push_register", "p", MsgConstant.SDK_VERSION)) != null && !jSONObjectBuildEnvelopeWithExtHeader3.has("exception")) {
                        MessageSharedPrefs.getInstance(applicationA3).f22601b.a("has_register", true);
                    }
                    y.f22949b = false;
                    return;
                case 16388:
                    Application applicationA4 = x.a();
                    JSONObject jSONObject6 = new JSONObject(obj.toString());
                    JSONObject jSONObjectOptJSONObject7 = jSONObject6.optJSONObject("jsonHeader");
                    JSONObject jSONObjectOptJSONObject8 = jSONObject6.optJSONObject("jsonBody");
                    String strOptString = jSONObject6.optString("um_px_path");
                    if (jSONObjectOptJSONObject7 == null || jSONObjectOptJSONObject8 == null) {
                        return;
                    }
                    jSONObjectOptJSONObject7.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
                    jSONObjectOptJSONObject7.put("din", d.c(x.a()));
                    jSONObjectOptJSONObject7.put("push_switch", d.p(x.a()));
                    JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject8.optJSONArray("push");
                    if (jSONArrayOptJSONArray2 != null && jSONArrayOptJSONArray2.length() > 0 && (iOptInt2 = (jSONObject = (JSONObject) jSONArrayOptJSONArray2.get(0)).optInt(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE)) == 0) {
                        ((aj) PushAgent.getInstance(applicationA4).getMessageNotifyApi()).a(jSONObject);
                    }
                    int i6 = iOptInt2;
                    JSONObject jSONObjectBuildEnvelopeWithExtHeader6 = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA4, jSONObjectOptJSONObject7, jSONObjectOptJSONObject8, strOptString, "p", MsgConstant.SDK_VERSION);
                    if (jSONObjectBuildEnvelopeWithExtHeader6 == null || jSONObjectBuildEnvelopeWithExtHeader6.has("exception") || i6 == 6 || i6 == 7) {
                        return;
                    }
                    a(applicationA4, jSONObjectOptJSONObject8.getJSONArray("push"));
                    return;
                case 16389:
                    Application applicationA5 = x.a();
                    JSONObject jSONObject7 = new JSONObject(obj.toString());
                    JSONObject jSONObjectOptJSONObject9 = jSONObject7.optJSONObject("header");
                    JSONObject jSONObjectOptJSONObject10 = jSONObject7.optJSONObject("content");
                    if (jSONObjectOptJSONObject9 == null || jSONObjectOptJSONObject10 == null || (jSONObjectBuildEnvelopeWithExtHeader4 = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA5, jSONObjectOptJSONObject9, jSONObjectOptJSONObject10, "umpx_push_logs", "p", MsgConstant.SDK_VERSION)) == null || jSONObjectBuildEnvelopeWithExtHeader4.has("exception")) {
                        return;
                    }
                    y.a();
                    y.a(jSONObjectOptJSONObject10.getJSONArray("push"));
                    return;
                case 16390:
                    JSONObject jSONObject8 = new JSONObject(obj.toString());
                    Application applicationA6 = x.a();
                    JSONObject jSONObject9 = new JSONObject();
                    jSONObject9.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
                    jSONObject9.put("din", d.c(applicationA6));
                    jSONObject9.put("push_switch", d.p(applicationA6));
                    JSONObject jSONObject10 = new JSONObject();
                    JSONArray jSONArray = new JSONArray();
                    jSONObject8.put(com.alipay.sdk.m.l.b.f9444k, "");
                    jSONObject8.put(RemoteMessageConst.DEVICE_TOKEN, PushAgent.getInstance(applicationA6).getRegistrationId());
                    jSONObject8.put("msg_id", "");
                    jSONObject8.put("ts", System.currentTimeMillis());
                    jSONArray.put(jSONObject8);
                    jSONObject10.put("push", jSONArray);
                    JSONObject jSONObjectBuildEnvelopeWithExtHeader7 = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA6, jSONObject9, jSONObject10, "umpx_push_logs", "p", MsgConstant.SDK_VERSION);
                    if (jSONObjectBuildEnvelopeWithExtHeader7 != null) {
                        jSONObjectBuildEnvelopeWithExtHeader7.has("exception");
                        return;
                    }
                    return;
                case 16391:
                case 16392:
                    Application applicationA7 = x.a();
                    JSONObject jSONObject11 = new JSONObject(obj.toString());
                    JSONObject jSONObjectOptJSONObject11 = jSONObject11.optJSONObject("content");
                    JSONObject jSONObjectOptJSONObject12 = jSONObject11.optJSONObject("header");
                    if (jSONObjectOptJSONObject12 == null || jSONObjectOptJSONObject11 == null || (jSONObjectBuildEnvelopeWithExtHeader5 = UMEnvelopeBuild.buildEnvelopeWithExtHeader(applicationA7, jSONObjectOptJSONObject12, jSONObjectOptJSONObject11, "umpx_push_logs", "p", MsgConstant.SDK_VERSION)) == null) {
                        return;
                    }
                    jSONObjectBuildEnvelopeWithExtHeader5.has("exception");
                    return;
                default:
                    return;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        th.printStackTrace();
    }

    private static void a(Context context, JSONArray jSONArray) {
        if (jSONArray != null) {
            try {
                if (jSONArray.length() == 0) {
                    return;
                }
                ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                    arrayList.add(ContentProviderOperation.newDelete(h.d(context)).withSelection("MsgId=? And ActionType=?", new String[]{jSONObject.optString("msg_id"), String.valueOf(jSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE))}).build());
                }
                context.getContentResolver().applyBatch(h.f(context), arrayList);
            } catch (Throwable th) {
                UPLog.e("LogDataProtocol", "remove cache error:" + th.getMessage());
            }
        }
    }
}
