package com.aliyun.alink.linksdk.tmp.resource;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.resource.ResDescpt;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11420a = "[Tmp]TResManager";

    /* renamed from: b, reason: collision with root package name */
    protected Map<String, b> f11421b;

    protected static class a {

        /* renamed from: a, reason: collision with root package name */
        protected static e f11422a = new e();

        protected a() {
        }
    }

    public static e a() {
        return a.f11422a;
    }

    private e() {
        this.f11421b = new HashMap();
    }

    public String a(com.aliyun.alink.linksdk.tmp.connect.b bVar, String str, DeviceModel deviceModel, String str2, boolean z2, b bVar2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String topicStr = TextHelper.getTopicStr(deviceModel, str2);
        if (a(bVar, str, str2, topicStr, z2, bVar2)) {
            return topicStr;
        }
        return null;
    }

    public String a(com.aliyun.alink.linksdk.tmp.connect.b bVar, DeviceModel deviceModel, String str, boolean z2, b bVar2) {
        return a(bVar, (String) null, deviceModel, str, z2, bVar2);
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, DeviceModel deviceModel, String str) {
        return a(bVar, str, TextHelper.getTopicStr(deviceModel, str));
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, DeviceModel deviceModel, String str, OutputParams outputParams, IPublishResourceListener iPublishResourceListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return a(bVar, str, deviceModel.getEventMethod(str), TextHelper.getTopicStr(deviceModel, str), outputParams, iPublishResourceListener);
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, String str, String str2, String str3, boolean z2, b bVar2) {
        return bVar.a(str, str2, str3, z2, bVar2);
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, String str, String str2, boolean z2, b bVar2) {
        return a(bVar, (String) null, str, str2, z2, bVar2);
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, String str, String str2) {
        return bVar.a(str, str2);
    }

    protected boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, String str, String str2, String str3, OutputParams outputParams, IPublishResourceListener iPublishResourceListener) {
        return bVar.a(str, str2, str3, outputParams, iPublishResourceListener);
    }

    public boolean a(com.aliyun.alink.linksdk.tmp.connect.b bVar, String str, String str2, byte[] bArr, IPublishResourceListener iPublishResourceListener) {
        return bVar.a(str, str2, bArr, iPublishResourceListener);
    }

    public static ResDescpt.ResElementType a(DeviceModel deviceModel, String str) {
        if (TextUtils.isEmpty(str)) {
            return ResDescpt.ResElementType.SERVICE;
        }
        if (str.equalsIgnoreCase("dev")) {
            return ResDescpt.ResElementType.DISCOVERY;
        }
        if (!str.equalsIgnoreCase(TmpConstant.PROPERTY_IDENTIFIER_GET) && !str.equalsIgnoreCase(TmpConstant.PROPERTY_IDENTIFIER_SET)) {
            if (deviceModel != null && !TextUtils.isEmpty(deviceModel.getEventMethod(str))) {
                return ResDescpt.ResElementType.EVENT;
            }
            if (deviceModel != null && !TextUtils.isEmpty(deviceModel.getServiceMethod(str))) {
                return ResDescpt.ResElementType.SERVICE;
            }
            return ResDescpt.ResElementType.ALCS;
        }
        return ResDescpt.ResElementType.PROPERTY;
    }
}
