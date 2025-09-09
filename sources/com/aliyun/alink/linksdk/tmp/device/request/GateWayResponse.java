package com.aliyun.alink.linksdk.tmp.device.request;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class GateWayResponse<T> implements Serializable {
    public int code;
    public T data;
    public String localizedMsg;
    public String message;

    public void setData(T t2) {
        this.data = t2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code:");
        sb.append(this.code);
        sb.append("message:");
        boolean zIsEmpty = TextUtils.isEmpty(this.message);
        String str = TmpConstant.GROUP_ROLE_UNKNOWN;
        sb.append(zIsEmpty ? TmpConstant.GROUP_ROLE_UNKNOWN : this.message);
        sb.append("data:");
        if (this.data != null) {
            str = this.message;
        }
        sb.append(str);
        return sb.toString();
    }
}
