package com.aliyun.alink.linksdk.tmp.connect.a.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAOptions;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.ica.group.ICAGroupOption;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.data.device.Option;
import com.aliyun.alink.linksdk.tmp.device.payload.service.ServiceRequestPayload;
import com.aliyun.alink.linksdk.tmp.device.request.localgroup.QueryLocalGroupDeviceRequest;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends a<b, ServiceRequestPayload> {

    /* renamed from: m, reason: collision with root package name */
    private String f11117m;

    /* renamed from: n, reason: collision with root package name */
    private String f11118n;

    /* renamed from: o, reason: collision with root package name */
    private String f11119o;

    /* renamed from: p, reason: collision with root package name */
    private String f11120p;

    /* renamed from: q, reason: collision with root package name */
    private Option f11121q;

    /* renamed from: r, reason: collision with root package name */
    private List<DeviceBasicData> f11122r;

    /* renamed from: s, reason: collision with root package name */
    private QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData f11123s;

    public b(String str, String str2) {
        this.f11117m = str;
        this.f11118n = str2;
    }

    public static b a(String str, String str2) {
        return new b(str, str2);
    }

    public b b(String str, String str2) {
        this.f11119o = str;
        this.f11120p = str2;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.a.a.a, com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public d c() {
        PalGroupReqMessage palGroupReqMessage = new PalGroupReqMessage();
        palGroupReqMessage.topic = CommonRequestBuilder.d(this.f11117m);
        palGroupReqMessage.payload = (TextUtils.isEmpty(this.f11106g) ? GsonUtils.toJson(this.f11111l) : this.f11106g).getBytes();
        PalGroupInfo palGroupInfo = new PalGroupInfo();
        palGroupReqMessage.groupInfo = palGroupInfo;
        palGroupInfo.groupId = this.f11118n;
        QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData = this.f11123s;
        if (queryLocalGroupDeviceResData != null) {
            palGroupInfo.deviceArray = new PalDeviceInfo[queryLocalGroupDeviceResData.totalNum];
            int i2 = 0;
            while (true) {
                QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData2 = this.f11123s;
                if (i2 >= queryLocalGroupDeviceResData2.totalNum) {
                    break;
                }
                QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResDataInner queryLocalGroupDeviceResDataInner = queryLocalGroupDeviceResData2.items.get(i2);
                DeviceBasicData deviceBasicData = DeviceManager.getInstance().getDeviceBasicData(queryLocalGroupDeviceResDataInner.getDeviceId());
                if (deviceBasicData == null) {
                    palGroupReqMessage.groupInfo.deviceArray[i2] = new PalDeviceInfo(queryLocalGroupDeviceResDataInner.productKey, queryLocalGroupDeviceResDataInner.deviceName);
                } else {
                    palGroupReqMessage.groupInfo.deviceArray[i2] = new PalDeviceInfo(queryLocalGroupDeviceResDataInner.productKey, queryLocalGroupDeviceResDataInner.deviceName, deviceBasicData.getAddr());
                }
                i2++;
            }
        }
        ICAOptions iCAOptions = new ICAOptions();
        iCAOptions.code = AlcsCoAPConstant.Code.GET.value;
        Option option = this.f11121q;
        iCAOptions.type = (option == null ? TmpEnum.QoSLevel.QoS_CON : option.getQoSLevel()).getValue();
        ICAAuthParams iCAAuthParams = new ICAAuthParams();
        iCAAuthParams.accessKey = this.f11119o;
        iCAAuthParams.accessToken = this.f11120p;
        ICAGroupOption iCAGroupOption = new ICAGroupOption();
        iCAGroupOption.icaOptions = iCAOptions;
        iCAGroupOption.icaAuthParams = iCAAuthParams;
        palGroupReqMessage.palOptions = iCAGroupOption;
        return new d(palGroupReqMessage);
    }

    public b a(Option option) {
        this.f11121q = option;
        return this;
    }

    public b a(QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData queryLocalGroupDeviceResData) {
        this.f11123s = queryLocalGroupDeviceResData;
        return this;
    }
}
