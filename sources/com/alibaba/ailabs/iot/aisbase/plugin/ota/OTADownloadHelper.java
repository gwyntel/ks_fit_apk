package com.alibaba.ailabs.iot.aisbase.plugin.ota;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.alibaba.ailabs.iot.aisbase.V;
import com.alibaba.ailabs.iot.aisbase.W;
import com.alibaba.ailabs.iot.aisbase.X;
import com.alibaba.ailabs.iot.aisbase.Y;
import com.alibaba.ailabs.iot.aisbase.Z;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.utils.DownloadManagerUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import datasource.implemention.data.DeviceVersionInfo;

/* loaded from: classes2.dex */
public class OTADownloadHelper {

    /* renamed from: c, reason: collision with root package name */
    public IOTAPlugin.IOTAActionListener f8467c;

    /* renamed from: j, reason: collision with root package name */
    public DownloadManagerUtils f8474j;

    /* renamed from: k, reason: collision with root package name */
    public IOTAPlugin.IFirmwareDownloadListener f8475k;

    /* renamed from: a, reason: collision with root package name */
    public final String f8465a = OTADownloadHelper.class.getSimpleName();

    /* renamed from: b, reason: collision with root package name */
    public IOTAPlugin.OTAState f8466b = IOTAPlugin.OTAState.IDLE;

    /* renamed from: d, reason: collision with root package name */
    public Handler f8468d = new Handler(Looper.getMainLooper());

    /* renamed from: e, reason: collision with root package name */
    public SparseArray<IActionListener> f8469e = new SparseArray<>();

    /* renamed from: f, reason: collision with root package name */
    public SparseArray<Runnable> f8470f = new SparseArray<>();

    /* renamed from: g, reason: collision with root package name */
    public String f8471g = null;

    /* renamed from: h, reason: collision with root package name */
    public String f8472h = null;

    /* renamed from: i, reason: collision with root package name */
    public long f8473i = -1;

    /* renamed from: l, reason: collision with root package name */
    public a f8476l = null;

    /* renamed from: m, reason: collision with root package name */
    public DeviceVersionInfo f8477m = null;

    /* renamed from: n, reason: collision with root package name */
    public boolean f8478n = false;

    /* renamed from: o, reason: collision with root package name */
    public String f8479o = null;

    /* renamed from: p, reason: collision with root package name */
    public boolean f8480p = false;

    /* renamed from: q, reason: collision with root package name */
    public Runnable f8481q = new V(this);

    /* renamed from: r, reason: collision with root package name */
    public Runnable f8482r = new W(this);

    public void startDownloadFirmware(Context context, DeviceVersionInfo deviceVersionInfo, String str, IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) throws InterruptedException {
        this.f8475k = iFirmwareDownloadListener;
        if (this.f8473i != -1) {
            a(-400, "There is currently a download task with id" + this.f8473i);
            return;
        }
        if (this.f8474j == null) {
            this.f8474j = DownloadManagerUtils.getInstance(context);
        }
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = this.f8475k;
        if (iFirmwareDownloadListener2 != null) {
            iFirmwareDownloadListener2.onDownloadStart();
        }
        if (this.f8480p) {
            ReportProgressUtil.reportOtaProgress(this.f8479o, deviceVersionInfo.getModel().getVersion(), this.f8471g, this.f8472h, ReportProgressUtil.TAG_START, ReportProgressUtil.CODE_OK, "");
        }
        long jDownloadFile = this.f8474j.downloadFile(deviceVersionInfo.getModel().getOtaUrl(), deviceVersionInfo.getModel().getMd5(), str, new X(this, deviceVersionInfo));
        this.f8473i = jDownloadFile;
        if (jDownloadFile < 0) {
            a(-401, "no write permission or insufficient disk");
            return;
        }
        if (jDownloadFile > 0) {
            a();
        }
        if (this.f8473i == 0) {
            this.f8473i = -1L;
        }
    }

    public void startDownloadIlopFirmware(Context context, DeviceVersionInfo.DeviceInfoModel deviceInfoModel, String str, IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) throws InterruptedException {
        LogUtils.e(this.f8465a, "Info Model: " + deviceInfoModel.toString());
        this.f8475k = iFirmwareDownloadListener;
        if (this.f8473i != -1) {
            a(-400, "There is currently a download task with id" + this.f8473i);
            return;
        }
        if (this.f8474j == null) {
            this.f8474j = DownloadManagerUtils.getInstance(context);
        }
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener2 = this.f8475k;
        if (iFirmwareDownloadListener2 != null) {
            iFirmwareDownloadListener2.onDownloadStart();
        }
        if (this.f8480p) {
            ReportProgressUtil.reportOtaProgress(this.f8479o, deviceInfoModel.getVersion(), this.f8471g, this.f8472h, ReportProgressUtil.TAG_START, ReportProgressUtil.CODE_OK, "");
        }
        long jDownloadFile = this.f8474j.downloadFile(deviceInfoModel.getOtaUrl(), deviceInfoModel.getMd5(), str, new Y(this, deviceInfoModel));
        this.f8473i = jDownloadFile;
        if (jDownloadFile < 0) {
            a(-401, "no write permission or insufficient disk");
            return;
        }
        if (jDownloadFile > 0) {
            a();
        }
        if (this.f8473i == 0) {
            this.f8473i = -1L;
        }
    }

    public void stopDownloadFirmware() {
        DownloadManagerUtils downloadManagerUtils;
        long j2 = this.f8473i;
        if (j2 != -1 || (downloadManagerUtils = this.f8474j) == null) {
            return;
        }
        downloadManagerUtils.cancelDownload(j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class a extends Thread {

        /* renamed from: a, reason: collision with root package name */
        public boolean f8483a;

        public a() {
            this.f8483a = true;
        }

        public void a() {
            this.f8483a = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            if (OTADownloadHelper.this.f8474j != null) {
                while (this.f8483a) {
                    int iValidDownload = OTADownloadHelper.this.f8474j.validDownload(OTADownloadHelper.this.f8473i);
                    if (iValidDownload != 0) {
                        OTADownloadHelper.this.a(iValidDownload, "Download failed");
                        OTADownloadHelper.this.f8473i = -1L;
                        return;
                    }
                    DownloadManagerUtils.DownloadTaskDetails downloadDetails = OTADownloadHelper.this.f8474j.getDownloadDetails(OTADownloadHelper.this.f8473i);
                    if (downloadDetails == null) {
                        this.f8483a = false;
                        return;
                    }
                    if (OTADownloadHelper.this.f8475k != null) {
                        OTADownloadHelper.this.f8468d.post(new Z(this, downloadDetails));
                    }
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }

        public /* synthetic */ a(OTADownloadHelper oTADownloadHelper, V v2) {
            this();
        }
    }

    public final void b(int i2, String str) {
        LogUtils.e(this.f8465a, "ota error (" + i2 + ":" + str + ")");
        this.f8478n = false;
        this.f8468d.removeCallbacks(this.f8481q);
        this.f8468d.removeCallbacks(this.f8482r);
        this.f8466b = IOTAPlugin.OTAState.ERROR;
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8467c;
        if (iOTAActionListener != null) {
            iOTAActionListener.onFailed(i2, str);
        }
    }

    public final void a() throws InterruptedException {
        b();
        a aVar = new a(this, null);
        this.f8476l = aVar;
        aVar.start();
    }

    public final void a(int i2, String str) {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = this.f8475k;
        if (iFirmwareDownloadListener != null) {
            iFirmwareDownloadListener.onFailed(i2, str);
        }
        if (this.f8480p) {
            DeviceVersionInfo deviceVersionInfo = this.f8477m;
            String version = deviceVersionInfo == null ? "" : deviceVersionInfo.getModel().getVersion();
            ReportProgressUtil.reportOtaProgress(this.f8479o, version, this.f8471g, this.f8472h, "FINISH", ReportProgressUtil.CODE_ERR, str + OpenAccountUIConstants.UNDER_LINE + i2);
        }
    }

    public final void b() throws InterruptedException {
        a aVar = this.f8476l;
        if (aVar != null) {
            aVar.a();
            try {
                this.f8476l.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }
}
