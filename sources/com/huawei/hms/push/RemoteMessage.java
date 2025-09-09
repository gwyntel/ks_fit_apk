package com.huawei.hms.push;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.push.utils.DateUtil;
import com.huawei.hms.push.utils.JsonUtil;
import com.huawei.hms.support.api.push.PushException;
import com.huawei.hms.support.log.HMSLog;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class RemoteMessage implements Parcelable {
    public static final Parcelable.Creator<RemoteMessage> CREATOR;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_UNKNOWN = 0;

    /* renamed from: c, reason: collision with root package name */
    private static final String[] f16642c;

    /* renamed from: d, reason: collision with root package name */
    private static final int[] f16643d;

    /* renamed from: e, reason: collision with root package name */
    private static final long[] f16644e;

    /* renamed from: f, reason: collision with root package name */
    private static final HashMap<String, Object> f16645f;

    /* renamed from: g, reason: collision with root package name */
    private static final HashMap<String, Object> f16646g;

    /* renamed from: h, reason: collision with root package name */
    private static final HashMap<String, Object> f16647h;

    /* renamed from: i, reason: collision with root package name */
    private static final HashMap<String, Object> f16648i;

    /* renamed from: j, reason: collision with root package name */
    private static final HashMap<String, Object> f16649j;

    /* renamed from: a, reason: collision with root package name */
    private Bundle f16650a;

    /* renamed from: b, reason: collision with root package name */
    private Notification f16651b;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        private final Bundle f16652a;

        /* renamed from: b, reason: collision with root package name */
        private final Map<String, String> f16653b;

        public Builder(String str) {
            Bundle bundle = new Bundle();
            this.f16652a = bundle;
            this.f16653b = new HashMap();
            bundle.putString("to", str);
        }

        public Builder addData(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("add data failed, key is null.");
            }
            this.f16653b.put(str, str2);
            return this;
        }

        public RemoteMessage build() throws JSONException {
            Bundle bundle = new Bundle();
            JSONObject jSONObject = new JSONObject();
            try {
                for (Map.Entry<String, String> entry : this.f16653b.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
                try {
                    String string = jSONObject.toString();
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(RemoteMessageConst.COLLAPSE_KEY, this.f16652a.getString(RemoteMessageConst.COLLAPSE_KEY));
                    jSONObject2.put(RemoteMessageConst.TTL, this.f16652a.getInt(RemoteMessageConst.TTL));
                    jSONObject2.put(RemoteMessageConst.SEND_MODE, this.f16652a.getInt(RemoteMessageConst.SEND_MODE));
                    jSONObject2.put(RemoteMessageConst.RECEIPT_MODE, this.f16652a.getInt(RemoteMessageConst.RECEIPT_MODE));
                    JSONObject jSONObject3 = new JSONObject();
                    if (jSONObject.length() != 0) {
                        jSONObject3.put("data", string);
                    }
                    jSONObject3.put(RemoteMessageConst.MSGID, this.f16652a.getString(RemoteMessageConst.MSGID));
                    jSONObject2.put(RemoteMessageConst.MessageBody.MSG_CONTENT, jSONObject3);
                    bundle.putByteArray(RemoteMessageConst.MSGBODY, jSONObject2.toString().getBytes(k.f16690a));
                    bundle.putString("to", this.f16652a.getString("to"));
                    bundle.putString("message_type", this.f16652a.getString("message_type"));
                    return new RemoteMessage(bundle);
                } catch (JSONException unused) {
                    HMSLog.w("RemoteMessage", "JSONException: parse message body failed.");
                    throw new PushException(PushException.EXCEPTION_SEND_FAILED);
                }
            } catch (JSONException unused2) {
                HMSLog.w("RemoteMessage", "JSONException: parse data to json failed.");
                throw new PushException(PushException.EXCEPTION_SEND_FAILED);
            }
        }

        public Builder clearData() {
            this.f16653b.clear();
            return this;
        }

        public Builder setCollapseKey(String str) {
            this.f16652a.putString(RemoteMessageConst.COLLAPSE_KEY, str);
            return this;
        }

        public Builder setData(Map<String, String> map) {
            this.f16653b.clear();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.f16653b.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public Builder setMessageId(String str) {
            this.f16652a.putString(RemoteMessageConst.MSGID, str);
            return this;
        }

        public Builder setMessageType(String str) {
            this.f16652a.putString("message_type", str);
            return this;
        }

        public Builder setReceiptMode(int i2) {
            if (i2 != 1 && i2 != 0) {
                throw new IllegalArgumentException("receipt mode can only be 0 or 1.");
            }
            this.f16652a.putInt(RemoteMessageConst.RECEIPT_MODE, i2);
            return this;
        }

        public Builder setSendMode(int i2) {
            if (i2 != 0 && i2 != 1) {
                throw new IllegalArgumentException("send mode can only be 0 or 1.");
            }
            this.f16652a.putInt(RemoteMessageConst.SEND_MODE, i2);
            return this;
        }

        public Builder setTtl(int i2) {
            if (i2 < 1 || i2 > 1296000) {
                throw new IllegalArgumentException("ttl must be greater than or equal to 1 and less than or equal to 1296000");
            }
            this.f16652a.putInt(RemoteMessageConst.TTL, i2);
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    public static class Notification implements Serializable {
        private final long[] A;
        private final String B;

        /* renamed from: a, reason: collision with root package name */
        private final String f16654a;

        /* renamed from: b, reason: collision with root package name */
        private final String f16655b;

        /* renamed from: c, reason: collision with root package name */
        private final String[] f16656c;

        /* renamed from: d, reason: collision with root package name */
        private final String f16657d;

        /* renamed from: e, reason: collision with root package name */
        private final String f16658e;

        /* renamed from: f, reason: collision with root package name */
        private final String[] f16659f;

        /* renamed from: g, reason: collision with root package name */
        private final String f16660g;

        /* renamed from: h, reason: collision with root package name */
        private final String f16661h;

        /* renamed from: i, reason: collision with root package name */
        private final String f16662i;

        /* renamed from: j, reason: collision with root package name */
        private final String f16663j;

        /* renamed from: k, reason: collision with root package name */
        private final String f16664k;

        /* renamed from: l, reason: collision with root package name */
        private final String f16665l;

        /* renamed from: m, reason: collision with root package name */
        private final String f16666m;

        /* renamed from: n, reason: collision with root package name */
        private final Uri f16667n;

        /* renamed from: o, reason: collision with root package name */
        private final int f16668o;

        /* renamed from: p, reason: collision with root package name */
        private final String f16669p;

        /* renamed from: q, reason: collision with root package name */
        private final int f16670q;

        /* renamed from: r, reason: collision with root package name */
        private final int f16671r;

        /* renamed from: s, reason: collision with root package name */
        private final int f16672s;

        /* renamed from: t, reason: collision with root package name */
        private final int[] f16673t;

        /* renamed from: u, reason: collision with root package name */
        private final String f16674u;

        /* renamed from: v, reason: collision with root package name */
        private final int f16675v;

        /* renamed from: w, reason: collision with root package name */
        private final String f16676w;

        /* renamed from: x, reason: collision with root package name */
        private final int f16677x;

        /* renamed from: y, reason: collision with root package name */
        private final String f16678y;

        /* renamed from: z, reason: collision with root package name */
        private final String f16679z;

        /* synthetic */ Notification(Bundle bundle, a aVar) {
            this(bundle);
        }

        private Integer a(String str) {
            if (str != null) {
                try {
                    return Integer.valueOf(str);
                } catch (NumberFormatException unused) {
                    HMSLog.w("RemoteMessage", "NumberFormatException: get " + str + " failed.");
                }
            }
            return null;
        }

        public Integer getBadgeNumber() {
            return a(this.f16676w);
        }

        public String getBody() {
            return this.f16657d;
        }

        public String[] getBodyLocalizationArgs() {
            String[] strArr = this.f16659f;
            return strArr == null ? new String[0] : (String[]) strArr.clone();
        }

        public String getBodyLocalizationKey() {
            return this.f16658e;
        }

        public String getChannelId() {
            return this.f16666m;
        }

        public String getClickAction() {
            return this.f16664k;
        }

        public String getColor() {
            return this.f16663j;
        }

        public String getIcon() {
            return this.f16660g;
        }

        public Uri getImageUrl() {
            String str = this.f16669p;
            if (str == null) {
                return null;
            }
            return Uri.parse(str);
        }

        public Integer getImportance() {
            return a(this.f16678y);
        }

        public String getIntentUri() {
            return this.f16665l;
        }

        public int[] getLightSettings() {
            int[] iArr = this.f16673t;
            return iArr == null ? new int[0] : (int[]) iArr.clone();
        }

        public Uri getLink() {
            return this.f16667n;
        }

        public int getNotifyId() {
            return this.f16668o;
        }

        public String getSound() {
            return this.f16661h;
        }

        public String getTag() {
            return this.f16662i;
        }

        public String getTicker() {
            return this.f16679z;
        }

        public String getTitle() {
            return this.f16654a;
        }

        public String[] getTitleLocalizationArgs() {
            String[] strArr = this.f16656c;
            return strArr == null ? new String[0] : (String[]) strArr.clone();
        }

        public String getTitleLocalizationKey() {
            return this.f16655b;
        }

        public long[] getVibrateConfig() {
            long[] jArr = this.A;
            return jArr == null ? new long[0] : (long[]) jArr.clone();
        }

        public Integer getVisibility() {
            return a(this.B);
        }

        public Long getWhen() {
            if (!TextUtils.isEmpty(this.f16674u)) {
                try {
                    return Long.valueOf(DateUtil.parseUtcToMillisecond(this.f16674u));
                } catch (StringIndexOutOfBoundsException unused) {
                    HMSLog.w("RemoteMessage", "StringIndexOutOfBoundsException: parse when failed.");
                } catch (ParseException unused2) {
                    HMSLog.w("RemoteMessage", "ParseException: parse when failed.");
                }
            }
            return null;
        }

        public boolean isAutoCancel() {
            return this.f16677x == 1;
        }

        public boolean isDefaultLight() {
            return this.f16670q == 1;
        }

        public boolean isDefaultSound() {
            return this.f16671r == 1;
        }

        public boolean isDefaultVibrate() {
            return this.f16672s == 1;
        }

        public boolean isLocalOnly() {
            return this.f16675v == 1;
        }

        private Notification(Bundle bundle) {
            this.f16654a = bundle.getString(RemoteMessageConst.Notification.NOTIFY_TITLE);
            this.f16657d = bundle.getString("content");
            this.f16655b = bundle.getString(RemoteMessageConst.Notification.TITLE_LOC_KEY);
            this.f16658e = bundle.getString(RemoteMessageConst.Notification.BODY_LOC_KEY);
            this.f16656c = bundle.getStringArray(RemoteMessageConst.Notification.TITLE_LOC_ARGS);
            this.f16659f = bundle.getStringArray(RemoteMessageConst.Notification.BODY_LOC_ARGS);
            this.f16660g = bundle.getString(RemoteMessageConst.Notification.ICON);
            this.f16663j = bundle.getString("color");
            this.f16661h = bundle.getString(RemoteMessageConst.Notification.SOUND);
            this.f16662i = bundle.getString("tag");
            this.f16666m = bundle.getString(RemoteMessageConst.Notification.CHANNEL_ID);
            this.f16664k = bundle.getString(RemoteMessageConst.Notification.CLICK_ACTION);
            this.f16665l = bundle.getString(RemoteMessageConst.Notification.INTENT_URI);
            this.f16668o = bundle.getInt(RemoteMessageConst.Notification.NOTIFY_ID);
            String string = bundle.getString("url");
            this.f16667n = !TextUtils.isEmpty(string) ? Uri.parse(string) : null;
            this.f16669p = bundle.getString(RemoteMessageConst.Notification.NOTIFY_ICON);
            this.f16670q = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_LIGHT_SETTINGS);
            this.f16671r = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_SOUND);
            this.f16672s = bundle.getInt(RemoteMessageConst.Notification.DEFAULT_VIBRATE_TIMINGS);
            this.f16673t = bundle.getIntArray(RemoteMessageConst.Notification.LIGHT_SETTINGS);
            this.f16674u = bundle.getString(RemoteMessageConst.Notification.WHEN);
            this.f16675v = bundle.getInt(RemoteMessageConst.Notification.LOCAL_ONLY);
            this.f16676w = bundle.getString(RemoteMessageConst.Notification.BADGE_SET_NUM, null);
            this.f16677x = bundle.getInt(RemoteMessageConst.Notification.AUTO_CANCEL);
            this.f16678y = bundle.getString(RemoteMessageConst.Notification.PRIORITY, null);
            this.f16679z = bundle.getString(RemoteMessageConst.Notification.TICKER);
            this.A = bundle.getLongArray(RemoteMessageConst.Notification.VIBRATE_TIMINGS);
            this.B = bundle.getString("visibility", null);
        }
    }

    class a implements Parcelable.Creator<RemoteMessage> {
        a() {
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public RemoteMessage createFromParcel(Parcel parcel) {
            return new RemoteMessage(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public RemoteMessage[] newArray(int i2) {
            return new RemoteMessage[i2];
        }
    }

    static {
        String[] strArr = new String[0];
        f16642c = strArr;
        int[] iArr = new int[0];
        f16643d = iArr;
        long[] jArr = new long[0];
        f16644e = jArr;
        HashMap<String, Object> map = new HashMap<>(8);
        f16645f = map;
        map.put("from", "");
        map.put(RemoteMessageConst.COLLAPSE_KEY, "");
        map.put(RemoteMessageConst.SEND_TIME, "");
        map.put(RemoteMessageConst.TTL, 86400);
        map.put(RemoteMessageConst.URGENCY, 2);
        map.put(RemoteMessageConst.ORI_URGENCY, 2);
        map.put(RemoteMessageConst.SEND_MODE, 0);
        map.put(RemoteMessageConst.RECEIPT_MODE, 0);
        HashMap<String, Object> map2 = new HashMap<>(8);
        f16646g = map2;
        map2.put(RemoteMessageConst.Notification.TITLE_LOC_KEY, "");
        map2.put(RemoteMessageConst.Notification.BODY_LOC_KEY, "");
        map2.put(RemoteMessageConst.Notification.NOTIFY_ICON, "");
        map2.put(RemoteMessageConst.Notification.TITLE_LOC_ARGS, strArr);
        map2.put(RemoteMessageConst.Notification.BODY_LOC_ARGS, strArr);
        map2.put(RemoteMessageConst.Notification.TICKER, "");
        map2.put(RemoteMessageConst.Notification.NOTIFY_TITLE, "");
        map2.put("content", "");
        HashMap<String, Object> map3 = new HashMap<>(8);
        f16647h = map3;
        map3.put(RemoteMessageConst.Notification.ICON, "");
        map3.put("color", "");
        map3.put(RemoteMessageConst.Notification.SOUND, "");
        map3.put(RemoteMessageConst.Notification.DEFAULT_LIGHT_SETTINGS, 1);
        map3.put(RemoteMessageConst.Notification.LIGHT_SETTINGS, iArr);
        map3.put(RemoteMessageConst.Notification.DEFAULT_SOUND, 1);
        map3.put(RemoteMessageConst.Notification.DEFAULT_VIBRATE_TIMINGS, 1);
        map3.put(RemoteMessageConst.Notification.VIBRATE_TIMINGS, jArr);
        HashMap<String, Object> map4 = new HashMap<>(8);
        f16648i = map4;
        map4.put("tag", "");
        map4.put(RemoteMessageConst.Notification.WHEN, "");
        map4.put(RemoteMessageConst.Notification.LOCAL_ONLY, 1);
        map4.put(RemoteMessageConst.Notification.BADGE_SET_NUM, "");
        map4.put(RemoteMessageConst.Notification.PRIORITY, "");
        map4.put(RemoteMessageConst.Notification.AUTO_CANCEL, 1);
        map4.put("visibility", "");
        map4.put(RemoteMessageConst.Notification.CHANNEL_ID, "");
        HashMap<String, Object> map5 = new HashMap<>(3);
        f16649j = map5;
        map5.put(RemoteMessageConst.Notification.CLICK_ACTION, "");
        map5.put(RemoteMessageConst.Notification.INTENT_URI, "");
        map5.put("url", "");
        CREATOR = new a();
    }

    public RemoteMessage(Bundle bundle) {
        this.f16650a = a(bundle);
    }

    private Bundle a(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        JSONObject jSONObjectB = b(bundle);
        JSONObject jSONObjectA = a(jSONObjectB);
        String string = JsonUtil.getString(jSONObjectA, "data", null);
        bundle2.putString(RemoteMessageConst.ANALYTIC_INFO, JsonUtil.getString(jSONObjectA, RemoteMessageConst.ANALYTIC_INFO, null));
        bundle2.putString(RemoteMessageConst.DEVICE_TOKEN, bundle.getString(RemoteMessageConst.DEVICE_TOKEN));
        JSONObject jSONObjectD = d(jSONObjectA);
        JSONObject jSONObjectB2 = b(jSONObjectD);
        JSONObject jSONObjectC = c(jSONObjectD);
        if (bundle.getInt(RemoteMessageConst.INPUT_TYPE) == 1 && c.a(jSONObjectA, jSONObjectD, string)) {
            bundle2.putString("data", com.huawei.hms.push.a.a(bundle.getByteArray(RemoteMessageConst.MSGBODY)));
            return bundle2;
        }
        String string2 = bundle.getString("to");
        String string3 = bundle.getString("message_type");
        String string4 = JsonUtil.getString(jSONObjectA, RemoteMessageConst.MSGID, null);
        bundle2.putString("to", string2);
        bundle2.putString("data", string);
        bundle2.putString(RemoteMessageConst.MSGID, string4);
        bundle2.putString("message_type", string3);
        JsonUtil.transferJsonObjectToBundle(jSONObjectB, bundle2, f16645f);
        bundle2.putBundle("notification", a(jSONObjectB, jSONObjectA, jSONObjectD, jSONObjectB2, jSONObjectC));
        return bundle2;
    }

    private static JSONObject b(Bundle bundle) {
        try {
            return new JSONObject(com.huawei.hms.push.a.a(bundle.getByteArray(RemoteMessageConst.MSGBODY)));
        } catch (JSONException unused) {
            HMSLog.w("RemoteMessage", "JSONException:parse message body failed.");
            return null;
        }
    }

    private static JSONObject c(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PARAM);
        }
        return null;
    }

    private static JSONObject d(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
        }
        return null;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public String getAnalyticInfo() {
        return this.f16650a.getString(RemoteMessageConst.ANALYTIC_INFO);
    }

    public Map<String, String> getAnalyticInfoMap() {
        HashMap map = new HashMap();
        String string = this.f16650a.getString(RemoteMessageConst.ANALYTIC_INFO);
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String strValueOf = String.valueOf(itKeys.next());
                    map.put(strValueOf, String.valueOf(jSONObject.get(strValueOf)));
                }
            } catch (JSONException unused) {
                HMSLog.w("RemoteMessage", "JSONException: get analyticInfo from map failed.");
            }
        }
        return map;
    }

    public String getCollapseKey() {
        return this.f16650a.getString(RemoteMessageConst.COLLAPSE_KEY);
    }

    public String getData() {
        return this.f16650a.getString("data");
    }

    public Map<String, String> getDataOfMap() {
        HashMap map = new HashMap();
        String string = this.f16650a.getString("data");
        if (string != null && !string.trim().isEmpty()) {
            try {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String strValueOf = String.valueOf(itKeys.next());
                    map.put(strValueOf, String.valueOf(jSONObject.get(strValueOf)));
                }
            } catch (JSONException unused) {
                HMSLog.w("RemoteMessage", "JSONException: get data from map failed");
            }
        }
        return map;
    }

    public String getFrom() {
        return this.f16650a.getString("from");
    }

    public String getMessageId() {
        return this.f16650a.getString(RemoteMessageConst.MSGID);
    }

    public String getMessageType() {
        return this.f16650a.getString("message_type");
    }

    public Notification getNotification() {
        Bundle bundle = this.f16650a.getBundle("notification");
        a aVar = null;
        if (this.f16651b == null && bundle != null) {
            this.f16651b = new Notification(bundle, aVar);
        }
        if (this.f16651b == null) {
            this.f16651b = new Notification(new Bundle(), aVar);
        }
        return this.f16651b;
    }

    public int getOriginalUrgency() {
        int i2 = this.f16650a.getInt(RemoteMessageConst.ORI_URGENCY);
        if (i2 == 1 || i2 == 2) {
            return i2;
        }
        return 0;
    }

    public int getReceiptMode() {
        return this.f16650a.getInt(RemoteMessageConst.RECEIPT_MODE);
    }

    public int getSendMode() {
        return this.f16650a.getInt(RemoteMessageConst.SEND_MODE);
    }

    public long getSentTime() {
        try {
            String string = this.f16650a.getString(RemoteMessageConst.SEND_TIME);
            if (TextUtils.isEmpty(string)) {
                return 0L;
            }
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            HMSLog.w("RemoteMessage", "NumberFormatException: get sendTime error.");
            return 0L;
        }
    }

    public String getTo() {
        return this.f16650a.getString("to");
    }

    public String getToken() {
        return this.f16650a.getString(RemoteMessageConst.DEVICE_TOKEN);
    }

    public int getTtl() {
        return this.f16650a.getInt(RemoteMessageConst.TTL);
    }

    public int getUrgency() {
        int i2 = this.f16650a.getInt(RemoteMessageConst.URGENCY);
        if (i2 == 1 || i2 == 2) {
            return i2;
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeBundle(this.f16650a);
        parcel.writeSerializable(this.f16651b);
    }

    public RemoteMessage(Parcel parcel) {
        this.f16650a = parcel.readBundle();
        this.f16651b = (Notification) parcel.readSerializable();
    }

    private static JSONObject b(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.NOTIFY_DETAIL);
        }
        return null;
    }

    private Bundle a(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, JSONObject jSONObject4, JSONObject jSONObject5) {
        Bundle bundle = new Bundle();
        JsonUtil.transferJsonObjectToBundle(jSONObject3, bundle, f16646g);
        JsonUtil.transferJsonObjectToBundle(jSONObject4, bundle, f16647h);
        JsonUtil.transferJsonObjectToBundle(jSONObject, bundle, f16648i);
        JsonUtil.transferJsonObjectToBundle(jSONObject5, bundle, f16649j);
        bundle.putInt(RemoteMessageConst.Notification.NOTIFY_ID, JsonUtil.getInt(jSONObject2, RemoteMessageConst.Notification.NOTIFY_ID, 0));
        return bundle;
    }

    private static JSONObject a(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
        }
        return null;
    }
}
