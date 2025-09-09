package com.xiaomi.infra.galaxy.fds;

import androidx.media3.extractor.text.ttml.TtmlNode;
import com.alibaba.sdk.android.oss.common.RequestParameters;

/* loaded from: classes4.dex */
public enum SubResource {
    ACL(RequestParameters.SUBRESOURCE_ACL),
    QUOTA("quota"),
    UPLOADS(RequestParameters.SUBRESOURCE_UPLOADS),
    PART_NUMBER(RequestParameters.PART_NUMBER),
    UPLOAD_ID(RequestParameters.UPLOAD_ID),
    STORAGE_ACCESS_TOKEN("storageAccessToken"),
    METADATA(TtmlNode.TAG_METADATA);

    private final String name;

    SubResource(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }
}
