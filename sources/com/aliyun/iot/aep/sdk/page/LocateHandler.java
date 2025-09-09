package com.aliyun.iot.aep.sdk.page;

import android.os.Handler;
import android.os.Message;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.R;

/* loaded from: classes3.dex */
public class LocateHandler extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private OnLocationListener f11974a;

    public interface OnLocationListener {
        void onContinuedLocate(String str);

        void onFailLocate();

        void onSuccessLocate(IoTSmart.Country country);
    }

    void a(OnLocationListener onLocationListener) {
        this.f11974a = onLocationListener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (this.f11974a == null || message == null) {
            return;
        }
        super.handleMessage(message);
        int i2 = message.what;
        if (i2 == 0) {
            this.f11974a.onContinuedLocate(AApplication.getInstance().getResources().getString(R.string.locating));
            return;
        }
        if (i2 == 1) {
            this.f11974a.onContinuedLocate(AApplication.getInstance().getResources().getString(R.string.locating) + ".");
            return;
        }
        if (i2 == 2) {
            this.f11974a.onContinuedLocate(AApplication.getInstance().getResources().getString(R.string.locating) + "..");
            return;
        }
        if (i2 == 3) {
            this.f11974a.onContinuedLocate(AApplication.getInstance().getResources().getString(R.string.locating) + "...");
            return;
        }
        if (i2 == 4) {
            this.f11974a.onSuccessLocate((IoTSmart.Country) message.obj);
            LocationUtil.cancelLocating();
        } else {
            if (i2 != 5) {
                return;
            }
            this.f11974a.onFailLocate();
            LocationUtil.cancelLocating();
        }
    }
}
