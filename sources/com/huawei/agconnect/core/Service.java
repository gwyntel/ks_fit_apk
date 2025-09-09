package com.huawei.agconnect.core;

import com.huawei.agconnect.annotation.AutoCreated;
import com.huawei.agconnect.annotation.SharedInstance;
import com.huawei.agconnect.annotation.Singleton;
import java.lang.reflect.Modifier;

/* loaded from: classes3.dex */
public class Service {

    /* renamed from: a, reason: collision with root package name */
    private final Class<?> f15620a;

    /* renamed from: b, reason: collision with root package name */
    private final Class<?> f15621b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f15622c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f15623d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f15624e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f15625f;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        Class<?> f15626a;

        /* renamed from: b, reason: collision with root package name */
        Class<?> f15627b;

        /* renamed from: c, reason: collision with root package name */
        Object f15628c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f15629d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f15630e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f15631f;

        public Service build() {
            Class<?> cls = this.f15626a;
            if (cls == null) {
                throw new IllegalArgumentException("the interface parameter cannot be NULL");
            }
            Class<?> cls2 = this.f15627b;
            if (cls2 == null) {
                Object obj = this.f15628c;
                if (obj == null) {
                    throw new IllegalArgumentException("the clazz or object parameter must set one");
                }
                Service service = new Service(cls, obj);
                service.f15623d = this.f15629d;
                return service;
            }
            if (cls2.isInterface() || !Modifier.isPublic(this.f15627b.getModifiers())) {
                throw new IllegalArgumentException("the clazz parameter cant be interface type or not public");
            }
            Service service2 = new Service((Class) this.f15626a, (Class) this.f15627b);
            service2.f15623d = this.f15629d;
            service2.f15624e = this.f15630e;
            service2.f15625f = this.f15631f;
            return service2;
        }

        public Builder isAutoCreated(boolean z2) {
            this.f15631f = z2;
            return this;
        }

        public Builder isSharedInstance(boolean z2) {
            this.f15630e = z2;
            return this;
        }

        public Builder isSingleton(boolean z2) {
            this.f15629d = z2;
            return this;
        }

        public Builder setClass(Class<?> cls) {
            this.f15627b = cls;
            return this;
        }

        public Builder setInterface(Class<?> cls) {
            this.f15626a = cls;
            return this;
        }

        public Builder setObject(Object obj) {
            this.f15628c = obj;
            return this;
        }
    }

    private Service(Class<?> cls, Class<?> cls2) {
        this.f15620a = cls;
        this.f15621b = cls2;
        this.f15622c = null;
    }

    public static Builder builder(Class<?> cls) {
        return new Builder().setInterface(cls).setClass(cls).isSingleton(cls.isAnnotationPresent(Singleton.class)).isSharedInstance(cls.isAnnotationPresent(SharedInstance.class)).isAutoCreated(cls.isAnnotationPresent(AutoCreated.class));
    }

    public Object getInstance() {
        return this.f15622c;
    }

    public Class<?> getInterface() {
        return this.f15620a;
    }

    public Class<?> getType() {
        return this.f15621b;
    }

    public boolean isAutoCreated() {
        return this.f15625f;
    }

    public boolean isSharedInstance() {
        return this.f15624e;
    }

    public boolean isSingleton() {
        return this.f15623d;
    }

    public static Builder builder(Class<?> cls, Class<?> cls2) {
        return new Builder().setInterface(cls).setClass(cls2).isSingleton(cls2.isAnnotationPresent(Singleton.class)).isSharedInstance(cls2.isAnnotationPresent(SharedInstance.class)).isAutoCreated(cls2.isAnnotationPresent(AutoCreated.class));
    }

    private Service(Class<?> cls, Object obj) {
        this.f15620a = cls;
        this.f15621b = null;
        this.f15622c = obj;
    }

    public static Builder builder(Class<?> cls, Object obj) {
        return new Builder().setInterface(cls).setObject(obj).isSingleton(true).isSharedInstance(cls.isAnnotationPresent(SharedInstance.class)).isAutoCreated(cls.isAnnotationPresent(AutoCreated.class));
    }
}
