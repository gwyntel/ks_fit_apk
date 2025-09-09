package com.aliyun.alink.linksdk.tmp.connect;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.tmp.devicemodel.Profile;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class CommonRequestBuilder<Builder, Payload> {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11100a = "CommonRequestBuilder";

    /* renamed from: c, reason: collision with root package name */
    protected Object f11102c;

    /* renamed from: d, reason: collision with root package name */
    protected String f11103d;

    /* renamed from: e, reason: collision with root package name */
    protected String f11104e;

    /* renamed from: f, reason: collision with root package name */
    protected int f11105f;

    /* renamed from: g, reason: collision with root package name */
    protected String f11106g;

    /* renamed from: l, reason: collision with root package name */
    protected Payload f11111l;

    /* renamed from: h, reason: collision with root package name */
    protected boolean f11107h = false;

    /* renamed from: i, reason: collision with root package name */
    protected long f11108i = 5000;

    /* renamed from: j, reason: collision with root package name */
    protected RequestType f11109j = RequestType.NORMAL;

    /* renamed from: b, reason: collision with root package name */
    protected Method f11101b = Method.GET;

    /* renamed from: k, reason: collision with root package name */
    protected Builder f11110k = this;

    public enum Method {
        UNKNOW(0),
        POST(1),
        GET(2),
        DELETE(3),
        PUT(4);

        protected int mValue;

        Method(int i2) {
            this.mValue = i2;
        }

        public int getValue() {
            return this.mValue;
        }

        public CommonRequest.METHOD toCRMethod() {
            CommonRequest.METHOD method = CommonRequest.METHOD.GET;
            int i2 = this.mValue;
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? method : CommonRequest.METHOD.PUT : CommonRequest.METHOD.DELETE : method : CommonRequest.METHOD.POST;
        }
    }

    public enum RequestType {
        NORMAL(1, "normal"),
        MULTIPLE_RESPONSE(2, "multiple_response"),
        RELEATE(3, "releate");

        protected String mDesc;
        protected int mType;

        RequestType(int i2, String str) {
            this.mType = i2;
            this.mDesc = str;
        }

        public String getDesc() {
            return this.mDesc;
        }

        public int getType() {
            return this.mType;
        }
    }

    public static String d(String str) {
        return TmpConstant.PATH_GROUP_PRE + str;
    }

    public Builder a(boolean z2) {
        this.f11107h = z2;
        return this.f11110k;
    }

    public Builder b(String str) {
        this.f11106g = str;
        return this.f11110k;
    }

    public abstract d c();

    public Builder c(String str) {
        this.f11103d = str;
        return this.f11110k;
    }

    public Builder a(Object obj) {
        this.f11102c = obj;
        return this.f11110k;
    }

    public Builder b(Payload payload) {
        this.f11111l = payload;
        return this.f11110k;
    }

    public Builder a(String str) {
        this.f11104e = str;
        return this.f11110k;
    }

    public Method b() {
        return this.f11101b;
    }

    public Builder a(long j2) {
        this.f11108i = j2;
        return this.f11110k;
    }

    public Builder a(RequestType requestType) {
        this.f11109j = requestType;
        return this.f11110k;
    }

    public int a() {
        return this.f11105f;
    }

    public Builder a(int i2) {
        this.f11105f = i2;
        return this.f11110k;
    }

    public void a(Method method) {
        this.f11101b = method;
    }

    public static String a(Profile profile, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (profile == null) {
            LogCat.e(f11100a, "formatPath error param null profile:" + profile + " method" + str);
            return str;
        }
        return a(profile.getProdKey(), profile.getName(), str, "sys");
    }

    public static String a(String str, String str2, String str3, String str4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str3)) {
                LogCat.e(f11100a, "formatPath error param  method" + str3);
                return "/" + str4 + "/" + str + "/" + str2;
            }
            return "/" + str4 + "/" + str + "/" + str2 + "/" + str3.replace(".", "/");
        }
        LogCat.e(f11100a, "formatPath error param null productKey:" + str + " deviceName:" + str2 + " method" + str3);
        return str3;
    }
}
