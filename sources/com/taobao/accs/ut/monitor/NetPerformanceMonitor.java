package com.taobao.accs.ut.monitor;

import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;

@Monitor(module = "accs", monitorPoint = "netperformance")
/* loaded from: classes4.dex */
public class NetPerformanceMonitor extends BaseMonitor {

    /* renamed from: a, reason: collision with root package name */
    private long f20328a;

    @Dimension
    public int accs_sdk_version;

    @Dimension
    public int accs_type;

    /* renamed from: b, reason: collision with root package name */
    private long f20329b;

    /* renamed from: c, reason: collision with root package name */
    private long f20330c;
    public long check_command_time;
    public long check_process_time;
    public long check_routing_ack_time;
    public long check_routing_msg_time;
    public long check_space_time;
    public long control_msg_time;

    /* renamed from: d, reason: collision with root package name */
    private long f20331d;
    public String data_id;
    public String device_id;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long distribute_to_service_time;

    /* renamed from: e, reason: collision with root package name */
    private long f20332e;

    @Dimension
    public int error_code;

    /* renamed from: f, reason: collision with root package name */
    private long f20333f;

    @Dimension
    public String fail_reasons;

    /* renamed from: g, reason: collision with root package name */
    private long f20334g;

    @Dimension
    public String host;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long in_queue_time;

    @Dimension
    public int msgType;
    public long real_to_bz_date;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long receive_accs_to_call_time;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long receive_agoo_to_call_time;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long receive_to_call_back_time;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long receive_to_distribute_time;

    @Dimension
    public String ret;

    @Dimension
    public int retry_times;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long send_to_receive_time;

    @Dimension
    public String service_id = "none";
    public long service_recv;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long service_to_recv_time;
    public long start_service;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long start_to_enter_queue_time;
    public long take_date;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long talk_to_send_time;
    public long thread_schedule_time;
    public long to_tnet_date;

    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long total_time;

    private long a(long j2, long j3) {
        if (j2 <= 0 || j3 <= 0) {
            return 0L;
        }
        return j3 - j2;
    }

    @Override // com.taobao.accs.utl.BaseMonitor, anet.channel.statist.StatObject
    public boolean beforeCommit() {
        if (this.real_to_bz_date == 0) {
            this.real_to_bz_date = this.f20331d;
        }
        this.accs_sdk_version = 221;
        this.total_time = a(this.f20328a, this.real_to_bz_date);
        this.start_to_enter_queue_time = a(this.f20328a, this.f20329b);
        this.in_queue_time = a(this.f20329b, this.take_date);
        this.talk_to_send_time = a(this.take_date, this.to_tnet_date);
        this.send_to_receive_time = a(this.to_tnet_date, this.f20330c);
        this.receive_to_distribute_time = a(this.f20330c, this.f20331d);
        this.distribute_to_service_time = a(this.f20331d, this.start_service);
        this.service_to_recv_time = a(this.start_service, this.service_recv);
        this.receive_to_call_back_time = a(this.service_recv, this.real_to_bz_date);
        this.receive_accs_to_call_time = a(this.f20332e, this.f20333f);
        this.receive_agoo_to_call_time = a(this.f20332e, this.f20334g);
        if ("accs-impaas".equals(this.service_id)) {
            ALog.e("pref", "netperf", "dataid", this.data_id, "total_time", Long.valueOf(this.total_time), "before_queue", Long.valueOf(this.start_to_enter_queue_time), "in_queue", Long.valueOf(this.in_queue_time), "send", Long.valueOf(this.talk_to_send_time), "recv", Long.valueOf(this.send_to_receive_time), "distribute", Long.valueOf(this.receive_to_distribute_time), "startservice", Long.valueOf(this.distribute_to_service_time), "servicerecv", Long.valueOf(this.service_to_recv_time), "tobiz", Long.valueOf(this.receive_to_call_back_time));
        }
        return super.beforeCommit();
    }

    public void onEnterQueueData() {
        this.f20329b = System.currentTimeMillis();
    }

    public void onRecAck() {
        this.f20330c = System.currentTimeMillis();
    }

    public void onReceiveData() {
        this.f20332e = System.currentTimeMillis();
    }

    public void onSend() {
        this.f20328a = System.currentTimeMillis();
    }

    public void onSendData() {
        this.to_tnet_date = System.currentTimeMillis();
    }

    public void onTakeFromQueue() {
        this.take_date = System.currentTimeMillis();
    }

    public void onToAccsTime() {
        this.f20333f = System.currentTimeMillis();
    }

    public void onToAgooTime() {
        this.f20334g = System.currentTimeMillis();
    }

    public void onToBizDate() {
        this.f20331d = System.currentTimeMillis();
    }

    public void setConnType(int i2) {
        this.accs_type = i2;
    }

    public void setDataId(String str) {
        this.data_id = str;
    }

    public void setDeviceId(String str) {
        this.device_id = str;
    }

    public void setFailReason(String str) {
        this.fail_reasons = str;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setMsgType(int i2) {
        this.msgType = i2;
    }

    public void setRet(boolean z2) {
        this.ret = z2 ? "y" : "n";
    }

    public void setServiceId(String str) {
        this.service_id = str;
    }

    public void setFailReason(int i2) {
        this.error_code = i2;
        if (i2 == -4) {
            setFailReason("msg too large");
            return;
        }
        if (i2 == -3) {
            setFailReason("service not available");
            return;
        }
        if (i2 == -2) {
            setFailReason("param error");
            return;
        }
        if (i2 == -1) {
            setFailReason("network fail");
        } else if (i2 != 200) {
            if (i2 != 300) {
                setFailReason(String.valueOf(i2));
            } else {
                setFailReason("app not bind");
            }
        }
    }
}
