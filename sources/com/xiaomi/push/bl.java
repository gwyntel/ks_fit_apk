package com.xiaomi.push;

import java.util.LinkedList;

/* loaded from: classes4.dex */
public class bl {

    /* renamed from: a, reason: collision with root package name */
    private LinkedList<a> f23498a = new LinkedList<>();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final bl f23499a = new bl();

        /* renamed from: a, reason: collision with other field name */
        public int f213a;

        /* renamed from: a, reason: collision with other field name */
        public Object f214a;

        /* renamed from: a, reason: collision with other field name */
        public String f215a;

        a(int i2, Object obj) {
            this.f213a = i2;
            this.f214a = obj;
        }
    }

    public static bl a() {
        return a.f23499a;
    }

    public synchronized void a(Object obj) {
        this.f23498a.add(new a(0, obj));
        m215a();
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m215a() {
        if (this.f23498a.size() > 100) {
            this.f23498a.removeFirst();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized int m216a() {
        return this.f23498a.size();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized LinkedList<a> m217a() {
        LinkedList<a> linkedList;
        linkedList = this.f23498a;
        this.f23498a = new LinkedList<>();
        return linkedList;
    }
}
