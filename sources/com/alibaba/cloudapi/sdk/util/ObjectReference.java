package com.alibaba.cloudapi.sdk.util;

/* loaded from: classes2.dex */
public class ObjectReference<T> {
    private T obj;

    public ObjectReference(T t2) {
        this.obj = t2;
    }

    public T getObj() {
        return this.obj;
    }

    public void setObj(T t2) {
        this.obj = t2;
    }

    public ObjectReference() {
    }
}
