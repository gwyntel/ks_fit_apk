package com.umeng.message.proguard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.inapp.IUmengInAppMsgCloseCallback;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.inapp.UmengCardMessage;
import com.umeng.message.proguard.af;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class ag implements ad, af.a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f22727a = "com.umeng.message.proguard.ag";

    /* renamed from: b, reason: collision with root package name */
    public Context f22728b;

    /* renamed from: c, reason: collision with root package name */
    public String f22729c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f22730d;

    /* renamed from: e, reason: collision with root package name */
    private UInAppMessage f22731e;

    /* renamed from: f, reason: collision with root package name */
    private IUmengInAppMsgCloseCallback f22732f;

    public ag(Activity activity, String str, IUmengInAppMsgCloseCallback iUmengInAppMsgCloseCallback) {
        this.f22728b = activity;
        this.f22729c = str;
        this.f22732f = iUmengInAppMsgCloseCallback;
    }

    @Override // com.umeng.message.proguard.ad
    public final void a(UInAppMessage uInAppMessage) {
    }

    @Override // com.umeng.message.proguard.ad
    public final void b(UInAppMessage uInAppMessage) {
        UInAppMessage uInAppMessage2;
        String strA = InAppMessageManager.getInstance(this.f22728b).a(this.f22729c);
        if (TextUtils.isEmpty(strA)) {
            uInAppMessage2 = null;
        } else {
            try {
                uInAppMessage2 = new UInAppMessage(new JSONObject(strA));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (uInAppMessage != null) {
            if (uInAppMessage2 != null && !uInAppMessage.msg_id.equals(uInAppMessage2.msg_id)) {
                InAppMessageManager.getInstance(this.f22728b).a(new File(f.a(this.f22728b, uInAppMessage2.msg_id)));
            }
            this.f22731e = uInAppMessage;
        } else if (uInAppMessage2 == null) {
            return;
        } else {
            this.f22731e = uInAppMessage2;
        }
        if (this.f22731e.show_type == 1 && !b(this.f22729c)) {
            InAppMessageManager.getInstance(this.f22728b).a(this.f22731e.msg_id, 0);
        }
        InAppMessageManager.getInstance(this.f22728b);
        if (InAppMessageManager.b(this.f22731e) && InAppMessageManager.getInstance(this.f22728b).c(this.f22731e)) {
            int i2 = this.f22731e.msg_type;
            if (i2 == 5 || i2 == 6) {
                InAppMessageManager.getInstance(this.f22728b).a(this.f22731e, this.f22729c);
                a();
            } else {
                af afVar = new af(this.f22728b, this.f22731e);
                afVar.f22724a = this;
                afVar.execute(this.f22731e.image_url);
            }
        }
    }

    public final boolean a(String str) {
        JSONArray jSONArray;
        if (!d.a(this.f22728b).equals(InAppMessageManager.getInstance(this.f22728b).a("KEY_LAST_VERSION_CODE", ""))) {
            InAppMessageManager.getInstance(this.f22728b).b("KEY_CARD_LABEL_LIST", "");
        }
        InAppMessageManager.getInstance(this.f22728b).b("KEY_LAST_VERSION_CODE", d.a(this.f22728b));
        String strA = InAppMessageManager.getInstance(this.f22728b).a("KEY_CARD_LABEL_LIST", "");
        if (TextUtils.isEmpty(strA)) {
            jSONArray = null;
        } else {
            try {
                jSONArray = new JSONArray(strA);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (jSONArray == null) {
            JSONArray jSONArray2 = new JSONArray();
            jSONArray2.put(str);
            InAppMessageManager.getInstance(this.f22728b).b("KEY_CARD_LABEL_LIST", jSONArray2.toString());
            return true;
        }
        if (a(jSONArray, str)) {
            return true;
        }
        if (jSONArray.length() >= 10) {
            return false;
        }
        jSONArray.put(str);
        InAppMessageManager.getInstance(this.f22728b).b("KEY_CARD_LABEL_LIST", jSONArray.toString());
        return true;
    }

    private boolean b(String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(InAppMessageManager.getInstance(this.f22728b).c(str));
        Calendar calendar2 = Calendar.getInstance();
        return calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1);
    }

    private static boolean a(JSONArray jSONArray, String str) {
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                if (jSONArray.getString(i2).equals(str)) {
                    return true;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    private void a() {
        try {
            UmengCardMessage umengCardMessage = new UmengCardMessage();
            umengCardMessage.f22647a = this.f22732f;
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ScionAnalytics.PARAM_LABEL, this.f22729c);
            bundle.putString("msg", this.f22731e.getRaw().toString());
            umengCardMessage.setArguments(bundle);
            umengCardMessage.show(((Activity) this.f22728b).getFragmentManager(), this.f22729c);
            InAppMessageManager.getInstance(this.f22728b).a(this.f22731e.msg_id, 1);
            InAppMessageManager.getInstance(this.f22728b).b(this.f22729c);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.umeng.message.proguard.af.a
    public final void a(Bitmap[] bitmapArr) {
        Bitmap bitmap;
        if (!this.f22730d && (bitmap = bitmapArr[0]) != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                UmengCardMessage umengCardMessage = new UmengCardMessage();
                umengCardMessage.f22647a = this.f22732f;
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ScionAnalytics.PARAM_LABEL, this.f22729c);
                bundle.putString("msg", this.f22731e.getRaw().toString());
                bundle.putByteArray("bitmapByte", byteArray);
                umengCardMessage.setArguments(bundle);
                umengCardMessage.show(((Activity) this.f22728b).getFragmentManager(), this.f22729c);
                InAppMessageManager.getInstance(this.f22728b).a(this.f22731e.msg_id, 1);
                InAppMessageManager.getInstance(this.f22728b).b(this.f22729c);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        InAppMessageManager.getInstance(this.f22728b).a(this.f22731e, this.f22729c);
    }
}
