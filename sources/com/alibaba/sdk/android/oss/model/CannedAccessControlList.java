package com.alibaba.sdk.android.oss.model;

import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes2.dex */
public enum CannedAccessControlList {
    Private(PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE),
    PublicRead("public-read"),
    PublicReadWrite("public-read-write");

    private String ACLString;

    CannedAccessControlList(String str) {
        this.ACLString = str;
    }

    public static CannedAccessControlList parseACL(String str) {
        for (CannedAccessControlList cannedAccessControlList : values()) {
            if (cannedAccessControlList.toString().equals(str)) {
                return cannedAccessControlList;
            }
        }
        return null;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.ACLString;
    }
}
