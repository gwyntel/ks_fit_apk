package com.aliyun.alink.linksdk.tmp.error;

import com.aliyun.alink.linksdk.tmp.utils.ErrorCode;
import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class NotSupportError extends AError {
    public NotSupportError() {
        setCode(305);
        setMsg(ErrorCode.ERROR_MSG_NOTSUPPORT);
    }
}
