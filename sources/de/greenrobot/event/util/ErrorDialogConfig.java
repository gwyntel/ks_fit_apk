package de.greenrobot.event.util;

import android.content.res.Resources;
import android.util.Log;
import de.greenrobot.event.EventBus;

/* loaded from: classes4.dex */
public class ErrorDialogConfig {

    /* renamed from: a, reason: collision with root package name */
    final Resources f24985a;

    /* renamed from: b, reason: collision with root package name */
    final int f24986b;

    /* renamed from: c, reason: collision with root package name */
    final int f24987c;

    /* renamed from: e, reason: collision with root package name */
    EventBus f24989e;

    /* renamed from: g, reason: collision with root package name */
    String f24991g;

    /* renamed from: h, reason: collision with root package name */
    int f24992h;

    /* renamed from: i, reason: collision with root package name */
    Class f24993i;

    /* renamed from: f, reason: collision with root package name */
    boolean f24990f = true;

    /* renamed from: d, reason: collision with root package name */
    final ExceptionToResourceMapping f24988d = new ExceptionToResourceMapping();

    public ErrorDialogConfig(Resources resources, int i2, int i3) {
        this.f24985a = resources;
        this.f24986b = i2;
        this.f24987c = i3;
    }

    EventBus a() {
        EventBus eventBus = this.f24989e;
        return eventBus != null ? eventBus : EventBus.getDefault();
    }

    public ErrorDialogConfig addMapping(Class<? extends Throwable> cls, int i2) {
        this.f24988d.addMapping(cls, i2);
        return this;
    }

    public void disableExceptionLogging() {
        this.f24990f = false;
    }

    public int getMessageIdForThrowable(Throwable th) {
        Integer numMapThrowable = this.f24988d.mapThrowable(th);
        if (numMapThrowable != null) {
            return numMapThrowable.intValue();
        }
        Log.d(EventBus.TAG, "No specific message ressource ID found for " + th);
        return this.f24987c;
    }

    public void setDefaultDialogIconId(int i2) {
        this.f24992h = i2;
    }

    public void setDefaultEventTypeOnDialogClosed(Class<?> cls) {
        this.f24993i = cls;
    }

    public void setEventBus(EventBus eventBus) {
        this.f24989e = eventBus;
    }

    public void setTagForLoggingExceptions(String str) {
        this.f24991g = str;
    }
}
