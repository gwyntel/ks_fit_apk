package com.aliyun.alink.linksdk.tmp.resource;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class ResDescpt {

    /* renamed from: a, reason: collision with root package name */
    protected String f11406a;

    /* renamed from: b, reason: collision with root package name */
    protected List<a> f11407b = new ArrayList();

    public enum ResElementType {
        PROPERTY,
        SERVICE,
        EVENT,
        DISCOVERY,
        ALCS
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        protected String f11408a;

        /* renamed from: b, reason: collision with root package name */
        protected ResElementType f11409b;

        public a(String str, ResElementType resElementType) {
            this.f11408a = str;
            this.f11409b = resElementType;
        }
    }

    public ResDescpt(String str) {
        this.f11406a = str;
    }

    public void a(a aVar) {
        this.f11407b.add(aVar);
    }

    public List<a> a() {
        return this.f11407b;
    }
}
