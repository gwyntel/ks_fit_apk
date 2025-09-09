package com.squareup.okhttp;

import androidx.browser.trusted.sharing.ShareTarget;
import okio.Buffer;

/* loaded from: classes4.dex */
public final class FormEncodingBuilder {
    private static final MediaType CONTENT_TYPE = MediaType.parse(ShareTarget.ENCODING_TYPE_URL_ENCODED);
    private final Buffer content = new Buffer();

    public FormEncodingBuilder add(String str, String str2) {
        if (this.content.size() > 0) {
            this.content.writeByte(38);
        }
        HttpUrl.g(this.content, str, 0, str.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, true);
        this.content.writeByte(61);
        HttpUrl.g(this.content, str2, 0, str2.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, true);
        return this;
    }

    public FormEncodingBuilder addEncoded(String str, String str2) {
        if (this.content.size() > 0) {
            this.content.writeByte(38);
        }
        HttpUrl.g(this.content, str, 0, str.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, true);
        this.content.writeByte(61);
        HttpUrl.g(this.content, str2, 0, str2.length(), " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, true);
        return this;
    }

    public RequestBody build() {
        return RequestBody.create(CONTENT_TYPE, this.content.snapshot());
    }
}
