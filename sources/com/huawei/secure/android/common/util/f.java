package com.huawei.secure.android.common.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.zip.ZipFile;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class f {
    public static /* synthetic */ ZipFile a(File file, Charset charset) {
        return new ZipFile(file, charset);
    }
}
