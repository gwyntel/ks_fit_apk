package com.aliyun.alink.business.devicecenter.channel.coap;

import com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAP;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAP;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;
import com.aliyun.alink.linksdk.alcs.coap.resources.AlcsCoAPResource;

/* loaded from: classes2.dex */
public class AlcsCoAPService implements IAlcsCoAP {

    /* renamed from: b, reason: collision with root package name */
    public AlcsCoAP f10271b;

    /* renamed from: a, reason: collision with root package name */
    public long f10270a = -1;

    /* renamed from: c, reason: collision with root package name */
    public AlcsCoAPServiceStatus f10272c = AlcsCoAPServiceStatus.IDLE;

    public AlcsCoAPService() {
        this.f10271b = null;
        this.f10271b = new AlcsCoAP();
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAPSend
    public boolean cancelMessage(long j2) {
        try {
            return this.f10271b.cancelMessage(this.f10270a, j2);
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "cancelMessage exception." + e2);
            return false;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAP
    public void deInitCoAPService() {
        try {
            this.f10271b.freeCoAPContext(this.f10270a);
            this.f10272c = AlcsCoAPServiceStatus.IDLE;
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "deInitCoAPService freeCoAPContext exception." + e2);
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAP
    public long getContextId() {
        return this.f10270a;
    }

    public AlcsCoAPServiceStatus getServiceStatus() {
        return this.f10272c;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAP
    public void initCoAPService(AlcsCoAPContext alcsCoAPContext, AlcsCoAPResource alcsCoAPResource) {
        try {
            this.f10270a = this.f10271b.createCoAPContext(alcsCoAPContext, alcsCoAPResource);
            this.f10272c = AlcsCoAPServiceStatus.INITED;
            StringBuilder sb = new StringBuilder();
            sb.append("initCoAPService contexId=");
            sb.append(this.f10270a);
            ALog.d("AlcsCoAPService", sb.toString());
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "initCoAPService createCoAPContext exception." + e2);
        }
    }

    public void registerResource(AlcsCoAPResource alcsCoAPResource) {
        try {
            if (this.f10272c == AlcsCoAPServiceStatus.IDLE) {
                ALog.d("AlcsCoAPService", "registerResource contexId=");
                return;
            }
            if (alcsCoAPResource == null) {
                ALog.d("AlcsCoAPService", "registerResource contexId=");
                return;
            }
            this.f10271b.registerAllResource(this.f10270a, alcsCoAPResource);
            StringBuilder sb = new StringBuilder();
            sb.append("initCoAPService contexId=");
            sb.append(this.f10270a);
            ALog.d("AlcsCoAPService", sb.toString());
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "initCoAPService createCoAPContext exception." + e2);
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAPSend
    public long sendRequest(AlcsCoAPRequest alcsCoAPRequest, IAlcsCoAPReqHandler iAlcsCoAPReqHandler) {
        try {
            return this.f10271b.sendRequest(this.f10270a, alcsCoAPRequest, iAlcsCoAPReqHandler);
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "sendRequest sendAlcsRequest exception." + e2);
            return -1L;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAPSend
    public boolean sendResponse(AlcsCoAPResponse alcsCoAPResponse) {
        try {
            return this.f10271b.sendResponse(this.f10270a, alcsCoAPResponse);
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "sendResponse sendAlcsResponse exception." + e2);
            return false;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAP
    public void startCoAPService() {
        try {
            this.f10271b.alcsStart(this.f10270a);
            this.f10272c = AlcsCoAPServiceStatus.STARTED;
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "startCoAPService alcsStart exception." + e2);
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.coap.listener.IAlcsCoAP
    public void stopCoAPService() {
        try {
            this.f10271b.alcsStop(this.f10270a);
            this.f10272c = AlcsCoAPServiceStatus.STOPPED;
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "stopCoAPService alcsStop exception." + e2);
        }
    }

    public void unRegisterResource(AlcsCoAPResource alcsCoAPResource) {
        try {
            if (this.f10272c == AlcsCoAPServiceStatus.IDLE) {
                ALog.d("AlcsCoAPService", "registerResource context not inited.");
                return;
            }
            if (alcsCoAPResource == null) {
                ALog.d("AlcsCoAPService", "registerResource context not inited.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("initCoAPService contexId=");
            sb.append(this.f10270a);
            ALog.d("AlcsCoAPService", sb.toString());
        } catch (Exception e2) {
            ALog.w("AlcsCoAPService", "initCoAPService createCoAPContext exception." + e2);
        }
    }
}
