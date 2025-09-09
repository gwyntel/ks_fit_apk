package com.aliyun.alink.business.devicecenter.channel;

import com.aliyun.alink.business.devicecenter.log.ALog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class BaseChainProcessor<T> {

    /* renamed from: a, reason: collision with root package name */
    public final Object f10226a = new Object();
    public ArrayList<T> chainList;

    public BaseChainProcessor() {
        this.chainList = null;
        this.chainList = new ArrayList<>();
    }

    public void addChain(T t2) {
        ALog.d("BaseChainProcessor", "addChain chain=" + t2);
        synchronized (this.f10226a) {
            try {
                if (!this.chainList.contains(t2)) {
                    this.chainList.add(t2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void addChainList(List<T> list) {
        ALog.d("BaseChainProcessor", "addChainList chain=" + list);
        synchronized (this.f10226a) {
            try {
                this.chainList.clear();
                if (list == null) {
                    return;
                }
                this.chainList.addAll(list);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void clearChain() {
        synchronized (this.f10226a) {
            this.chainList.clear();
        }
    }

    public boolean contains(T t2) {
        synchronized (this.f10226a) {
            try {
                ArrayList<T> arrayList = this.chainList;
                if (arrayList != null && arrayList.size() >= 1) {
                    return this.chainList.contains(t2);
                }
                return false;
            } finally {
            }
        }
    }

    public T getChainItem(int i2) {
        T t2;
        if (i2 <= -1 || i2 >= getChainSize()) {
            return null;
        }
        synchronized (this.f10226a) {
            t2 = this.chainList.get(i2);
        }
        return t2;
    }

    public int getChainSize() {
        int size;
        synchronized (this.f10226a) {
            size = this.chainList.size();
        }
        return size;
    }

    public void removeChain(T t2) {
        ALog.d("BaseChainProcessor", "removeChain chain=" + t2);
        synchronized (this.f10226a) {
            this.chainList.remove(t2);
        }
    }

    public void removeChainItem(int i2) {
        ALog.d("BaseChainProcessor", "removeChainItem index=" + i2);
        synchronized (this.f10226a) {
            this.chainList.remove(i2);
        }
    }
}
