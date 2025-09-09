package com.alibaba.ailabs.iot.bluetoothlesdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    @NonNull
    private final LinkedList<ControlMessage> f8644a = new LinkedList<>();

    @NonNull
    public b a(@NonNull ControlMessage controlMessage) {
        if (controlMessage.enqueued) {
            throw new IllegalStateException("Request already enqueued");
        }
        this.f8644a.add(controlMessage);
        controlMessage.enqueued = true;
        return this;
    }

    @NonNull
    public b b(@NonNull ControlMessage controlMessage) {
        if (controlMessage.enqueued) {
            throw new IllegalStateException("Request already enqueued");
        }
        this.f8644a.addFirst(controlMessage);
        controlMessage.enqueued = true;
        return this;
    }

    @Nullable
    ControlMessage a() {
        try {
            return this.f8644a.remove();
        } catch (Exception unused) {
            return null;
        }
    }

    boolean b() {
        return !this.f8644a.isEmpty();
    }
}
