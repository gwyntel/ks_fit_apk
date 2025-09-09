package com.aliyun.alink.linksdk.tools;

/* loaded from: classes2.dex */
public class AError {
    public static final int AKErrorInvokeNetError = 4101;
    public static final int AKErrorInvokeServerError = 4102;
    public static final int AKErrorLoginTokenIllegalError = 4001;
    public static final int AKErrorServerBusinessError = 4103;
    public static final int AKErrorSuccess = 0;
    public static final int AKErrorUnknownError = 4201;
    public static final String ERR_DOMAIN_NAME_ALINK = "alinkErrorDomain";

    /* renamed from: a, reason: collision with root package name */
    private String f11437a = "alinkErrorDomain";

    /* renamed from: b, reason: collision with root package name */
    private int f11438b;

    /* renamed from: c, reason: collision with root package name */
    private String f11439c;

    /* renamed from: d, reason: collision with root package name */
    private String f11440d;

    /* renamed from: e, reason: collision with root package name */
    private int f11441e;

    /* renamed from: f, reason: collision with root package name */
    private String f11442f;

    /* renamed from: g, reason: collision with root package name */
    private Object f11443g;

    public int getCode() {
        return this.f11438b;
    }

    public String getDomain() {
        return this.f11437a;
    }

    public String getMsg() {
        return this.f11439c;
    }

    public Object getOriginResponseObject() {
        return this.f11443g;
    }

    public int getSubCode() {
        return this.f11441e;
    }

    public String getSubDomain() {
        return this.f11440d;
    }

    public String getSubMsg() {
        return this.f11442f;
    }

    public void setCode(int i2) {
        this.f11438b = i2;
    }

    public void setDomain(String str) {
        this.f11437a = str;
    }

    public void setMsg(String str) {
        this.f11439c = str;
    }

    public void setOriginResponseObject(Object obj) {
        this.f11443g = obj;
    }

    public void setSubCode(int i2) {
        this.f11441e = i2;
    }

    public void setSubDomain(String str) {
        this.f11440d = str;
    }

    public void setSubMsg(String str) {
        this.f11442f = str;
    }
}
