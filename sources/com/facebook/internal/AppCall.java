package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

/* loaded from: classes3.dex */
public class AppCall {
    private static AppCall currentPendingCall;
    private UUID callId;
    private int requestCode;
    private Intent requestIntent;

    public AppCall(int i2) {
        this(i2, UUID.randomUUID());
    }

    public static synchronized AppCall finishPendingCall(UUID uuid, int i2) {
        AppCall currentPendingCall2 = getCurrentPendingCall();
        if (currentPendingCall2 != null && currentPendingCall2.getCallId().equals(uuid) && currentPendingCall2.getRequestCode() == i2) {
            setCurrentPendingCall(null);
            return currentPendingCall2;
        }
        return null;
    }

    public static AppCall getCurrentPendingCall() {
        return currentPendingCall;
    }

    private static synchronized boolean setCurrentPendingCall(AppCall appCall) {
        AppCall currentPendingCall2;
        currentPendingCall2 = getCurrentPendingCall();
        currentPendingCall = appCall;
        return currentPendingCall2 != null;
    }

    public UUID getCallId() {
        return this.callId;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public Intent getRequestIntent() {
        return this.requestIntent;
    }

    public boolean setPending() {
        return setCurrentPendingCall(this);
    }

    public void setRequestCode(int i2) {
        this.requestCode = i2;
    }

    public void setRequestIntent(Intent intent) {
        this.requestIntent = intent;
    }

    public AppCall(int i2, UUID uuid) {
        this.callId = uuid;
        this.requestCode = i2;
    }
}
