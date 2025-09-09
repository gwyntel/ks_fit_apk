package com.huawei.hms.common.internal;

import java.util.ArrayList;
import java.util.ListIterator;

/* loaded from: classes4.dex */
public class BindResolveClients {

    /* renamed from: b, reason: collision with root package name */
    private static final Object f15958b = new Object();

    /* renamed from: a, reason: collision with root package name */
    private ArrayList<ResolveClientBean> f15959a;

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        private static final BindResolveClients f15960a = new BindResolveClients();
    }

    public static BindResolveClients getInstance() {
        return b.f15960a;
    }

    public boolean isClientRegistered(ResolveClientBean resolveClientBean) {
        boolean zContains;
        synchronized (f15958b) {
            zContains = this.f15959a.contains(resolveClientBean);
        }
        return zContains;
    }

    public void notifyClientReconnect() {
        synchronized (f15958b) {
            try {
                ListIterator<ResolveClientBean> listIterator = this.f15959a.listIterator();
                while (listIterator.hasNext()) {
                    listIterator.next().clientReconnect();
                }
                this.f15959a.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void register(ResolveClientBean resolveClientBean) {
        if (resolveClientBean == null) {
            return;
        }
        synchronized (f15958b) {
            try {
                if (!this.f15959a.contains(resolveClientBean)) {
                    this.f15959a.add(resolveClientBean);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unRegister(ResolveClientBean resolveClientBean) {
        if (resolveClientBean == null) {
            return;
        }
        synchronized (f15958b) {
            try {
                if (this.f15959a.contains(resolveClientBean)) {
                    ListIterator<ResolveClientBean> listIterator = this.f15959a.listIterator();
                    while (true) {
                        if (!listIterator.hasNext()) {
                            break;
                        } else if (resolveClientBean.equals(listIterator.next())) {
                            listIterator.remove();
                            break;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unRegisterAll() {
        synchronized (f15958b) {
            this.f15959a.clear();
        }
    }

    private BindResolveClients() {
        this.f15959a = new ArrayList<>();
    }
}
