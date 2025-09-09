package com.aliyun.alink.linksdk.tools.log;

/* loaded from: classes2.dex */
public class Request<T> {
    public String id;
    public String method;
    public T params;
    public String version;

    public static final class Builder<K> {

        /* renamed from: a, reason: collision with root package name */
        private String f11459a;

        /* renamed from: b, reason: collision with root package name */
        private String f11460b;

        /* renamed from: c, reason: collision with root package name */
        private K f11461c;

        public Request<K> build() {
            return new Request<>(this);
        }

        public Builder method(String str) {
            this.f11460b = str;
            return this;
        }

        public Builder params(K k2) {
            this.f11461c = k2;
            return this;
        }

        public Builder version(String str) {
            this.f11459a = str;
            return this;
        }
    }

    public String toString() {
        return "Request{id='" + this.id + "', version='" + this.version + "', method='" + this.method + "', params=" + this.params + '}';
    }

    private Request(Builder<T> builder) {
        this.id = null;
        this.version = null;
        this.method = null;
        this.params = null;
        this.id = String.valueOf(IDGenerater.generateId());
        this.version = ((Builder) builder).f11459a;
        this.method = ((Builder) builder).f11460b;
        this.params = (T) ((Builder) builder).f11461c;
    }
}
