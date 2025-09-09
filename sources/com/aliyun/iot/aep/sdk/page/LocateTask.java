package com.aliyun.iot.aep.sdk.page;

import android.content.Context;
import android.location.Address;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.page.LocateHandler;
import com.aliyun.iot.aep.sdk.threadpool.ThreadPool;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class LocateTask {
    public static final String TAG = "LocateTask";

    /* renamed from: a, reason: collision with root package name */
    private boolean f11975a = true;

    /* renamed from: b, reason: collision with root package name */
    private Timer f11976b = new Timer();

    /* renamed from: c, reason: collision with root package name */
    private LocateHandler f11977c = new LocateHandler();

    /* renamed from: d, reason: collision with root package name */
    private int f11978d = 0;

    /* renamed from: e, reason: collision with root package name */
    private CopyOnWriteArrayList<IoTSmart.Country> f11979e;

    /* renamed from: f, reason: collision with root package name */
    private Context f11980f;

    public LocateTask(@NonNull Context context, List<IoTSmart.Country> list, LocateHandler.OnLocationListener onLocationListener) {
        CopyOnWriteArrayList<IoTSmart.Country> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        this.f11979e = copyOnWriteArrayList;
        this.f11980f = context;
        if (list != null) {
            copyOnWriteArrayList.addAll(list);
        }
        this.f11977c.a(onLocationListener);
    }

    static /* synthetic */ int f(LocateTask locateTask) {
        int i2 = locateTask.f11978d;
        locateTask.f11978d = i2 + 1;
        return i2;
    }

    public synchronized void startLocation() {
        try {
            ThreadPool.DefaultThreadPool.getInstance().submit(new Runnable() { // from class: com.aliyun.iot.aep.sdk.page.LocateTask.1
                @Override // java.lang.Runnable
                public void run() throws InterruptedException, IOException {
                    while (LocateTask.this.f11975a) {
                        Address addressA = LocationUtil.a(LocateTask.this.f11980f.getApplicationContext());
                        if (addressA != null) {
                            String countryCode = addressA.getCountryCode();
                            if (TextUtils.isEmpty(countryCode) && addressA.getLocale() != null) {
                                countryCode = addressA.getLocale().getCountry();
                            }
                            IoTSmart.Country countryA = LocateTask.this.a(countryCode, addressA.getCountryName());
                            Log.d(LocateTask.TAG, "<====" + addressA.getCountryCode() + ", " + addressA.getCountryName() + " =====>");
                            if (countryA != null) {
                                Message message = new Message();
                                message.obj = countryA;
                                message.what = 4;
                                LocateTask.this.f11977c.sendMessage(message);
                                LocateTask.this.f11975a = false;
                                LocateTask.this.f11976b.cancel();
                                LocateTask.this.f11976b = null;
                            } else {
                                try {
                                    Thread.sleep(500L);
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                }
                                if (LocateTask.this.f11975a) {
                                    Message message2 = new Message();
                                    message2.what = LocateTask.this.f11978d % 4;
                                    LocateTask.f(LocateTask.this);
                                    LocateTask.this.f11977c.sendMessage(message2);
                                }
                            }
                        } else {
                            try {
                                Thread.sleep(500L);
                            } catch (InterruptedException e3) {
                                e3.printStackTrace();
                            }
                            if (LocateTask.this.f11975a) {
                                Message message3 = new Message();
                                message3.what = LocateTask.this.f11978d % 4;
                                LocateTask.f(LocateTask.this);
                                LocateTask.this.f11977c.sendMessage(message3);
                            }
                        }
                    }
                }
            });
            if (this.f11976b == null) {
                this.f11976b = new Timer();
            }
            this.f11976b.schedule(new TimerTask() { // from class: com.aliyun.iot.aep.sdk.page.LocateTask.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    LocateTask.this.f11975a = false;
                    Message message = new Message();
                    message.what = 5;
                    LocateTask.this.f11977c.sendMessage(message);
                }
            }, 30000L);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void stopLocation() {
        try {
            this.f11975a = false;
            Timer timer = this.f11976b;
            if (timer != null) {
                timer.cancel();
            }
            LocateHandler locateHandler = this.f11977c;
            if (locateHandler != null) {
                locateHandler.removeCallbacksAndMessages(null);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IoTSmart.Country a(String str, String str2) {
        if (this.f11979e.isEmpty()) {
            return null;
        }
        Iterator<IoTSmart.Country> it = this.f11979e.iterator();
        while (it.hasNext()) {
            IoTSmart.Country next = it.next();
            if (Objects.equals(str, next.code) || Objects.equals(str, next.domainAbbreviation) || Objects.equals(str2, next.areaName)) {
                return next;
            }
        }
        return null;
    }
}
