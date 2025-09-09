package com.aliyun.alink.linksdk.tmp.error;

import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class UnknownError extends AError {
    public UnknownError() {
        setCode(304);
        setMsg("unknown error");
    }
}
